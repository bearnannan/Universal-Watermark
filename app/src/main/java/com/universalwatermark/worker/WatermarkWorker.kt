package com.universalwatermark.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Size
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.universalwatermark.data.local.db.dao.WatermarkHistoryDao
import com.universalwatermark.data.local.db.dao.WatermarkTemplateDao
import com.universalwatermark.data.local.db.entity.WatermarkHistoryEntity
import com.universalwatermark.domain.model.WatermarkPosition
import com.universalwatermark.domain.model.WatermarkStyle
import com.universalwatermark.engine.metadata.MetadataCollector
import com.universalwatermark.engine.renderer.WatermarkRenderer
import com.universalwatermark.engine.template.TemplateEngine
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.File

@HiltWorker
class WatermarkWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val metadataCollector: MetadataCollector,
    private val watermarkRenderer: WatermarkRenderer,
    private val historyDao: WatermarkHistoryDao,
    private val settingsDataStore: com.universalwatermark.data.local.datastore.SettingsDataStore,
    private val userProfileDataStore: com.universalwatermark.data.local.datastore.UserProfileDataStore,
    private val cryptoManager: com.universalwatermark.engine.crypto.CryptoManager
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val imageUriString = inputData.getString("IMAGE_URI") ?: return@withContext Result.failure()
        val imageUri = Uri.parse(imageUriString)

        try {
            // 0. Check if already processed
            val existing = historyDao.getHistoryByUri(imageUriString)
            if (existing != null && existing.status == "SUCCESS") {
                return@withContext Result.success() // Already processed
            }

            // 1. Collect Metadata
            val metadata = metadataCollector.collectMetadata(imageUri)

            // 2. Fetch User Settings & Toggles
            val settingsRepository = com.universalwatermark.data.SettingsRepository(context)
            val settings = settingsRepository.cameraSettingsFlow.first()

            val logoBitmap = if (settings.customLogoPath != null) {
                try {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                        android.graphics.ImageDecoder.decodeBitmap(android.graphics.ImageDecoder.createSource(context.contentResolver, android.net.Uri.parse(settings.customLogoPath)))
                    } else {
                        @Suppress("DEPRECATION")
                        android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, android.net.Uri.parse(settings.customLogoPath))
                    }
                } catch(e:Exception){ null }
            } else null

            // 3. Build OverlayConfig
            val dateFormat = java.text.SimpleDateFormat(if (settings.dateWatermarkEnabled && settings.timeWatermarkEnabled) "${settings.dateFormat} HH:mm:ss" else if (settings.dateWatermarkEnabled) settings.dateFormat else "HH:mm:ss", if (settings.isThaiLanguage) java.util.Locale("th", "TH") else java.util.Locale.getDefault())
            val dateStr = "${metadata["DATE"]} ${metadata["TIME"]}"
            
            // We use the current date or parse from metadata
            val dateObj = try { 
                val defaultParser = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
                defaultParser.parse(dateStr) ?: java.util.Date() 
            } catch(e:Exception){ java.util.Date() }

            val config = com.universalwatermark.engine.renderer.OverlayConfig(
                templateId = settings.templateId, 
                date = dateObj,
                address = metadata["ADDRESS"] ?: "",
                latLon = metadata["GPS"] ?: "",
                userName = settings.inspectorName, // Mapping inspector to User
                orgName = settings.projectName,    // Mapping project to Org
                deviceAlias = settings.customNote,
                assetNumber = settings.tags,
                customText = "",
                
                showDate = settings.dateWatermarkEnabled,
                showTime = settings.timeWatermarkEnabled,
                showAddress = settings.isAddressEnabled,
                showLatLon = settings.isCoordinatesEnabled,
                showUser = settings.isInspectorEnabled && settings.inspectorName.isNotEmpty(),
                showOrg = settings.isProjectEnabled && settings.projectName.isNotEmpty(),
                showDevice = settings.isNoteEnabled && settings.customNote.isNotEmpty(),
                showAsset = settings.isTagsEnabled && settings.tags.isNotEmpty(),
                showCustomText = false,
                showLogo = settings.customLogoPath != null,
                logoBitmap = logoBitmap,
                
                // Style configurations
                position = com.universalwatermark.engine.renderer.OverlayPosition.valueOf(settings.overlayPosition),
                textColor = settings.textColor,
                textSize = settings.textSize,
                alpha = settings.textAlpha,
                datePattern = settings.dateFormat,
                useThaiLocale = settings.isThaiLanguage,
                textShadowEnabled = settings.textShadowEnabled,
                textBackgroundEnabled = settings.textBackgroundEnabled,
                textStyle = settings.textStyle,
                fontFamily = settings.fontFamily,
                textOrder = settings.customTextOrder,
                googleFontName = settings.googleFontName,
                textStrokeEnabled = settings.textStrokeEnabled,
                textStrokeWidth = settings.textStrokeWidth,
                textStrokeColor = settings.textStrokeColor,
                
                // Rich data
                compassEnabled = settings.compassEnabled,
                showCompass = settings.compassEnabled,
                compassHeading = metadata["AZIMUTH"]?.toFloatOrNull() ?: 0f,
                azimuth = metadata["AZIMUTH"]?.toFloatOrNull() ?: 0f,
                altitudeEnabled = settings.altitudeEnabled,
                speedEnabled = settings.speedEnabled,
                altitude = metadata["ALTITUDE"]?.toDoubleOrNull() ?: 0.0,
                speed = metadata["SPEED"]?.toFloatOrNull() ?: 0f
            )

            // 4. Output File Setup using MediaStore (to show in Gallery)
            val noteValue = settings.customNote.trim()
            val safeNoteName = if (settings.isNoteEnabled && noteValue.isNotBlank()) {
                noteValue.replace("[\\\\/:*?\"<>|]".toRegex(), "_")
            } else {
                "WM"
            }
            
            val dateFormatForFile = java.text.SimpleDateFormat("yyyyMMdd_HHmmss", java.util.Locale.US)
            val dateString = dateFormatForFile.format(java.util.Date())
            val fileName = "${safeNoteName}_${dateString}.jpg"
            val subFolder = if (safeNoteName != "WM") "/$safeNoteName" else ""
 
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/UniversalWatermark" + subFolder)
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }
            }

            val resolver = context.contentResolver
            val outputUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                ?: return@withContext Result.failure()

            // 5. Render Watermark
            val outputStream = resolver.openOutputStream(outputUri) ?: return@withContext Result.failure()
            val renderResult = watermarkRenderer.render(imageUri, outputStream, config)
            
            if (renderResult.isSuccess) {
                // 5.5 Generate Hash and Signature (BEFORE removing IS_PENDING)
                var finalMetadataJson = metadata.toString()
                try {
                    val renderedInputStream = resolver.openInputStream(outputUri)
                    val bytesToHash = renderedInputStream?.readBytes() ?: ByteArray(0)
                    renderedInputStream?.close()

                    if (bytesToHash.isNotEmpty()) {
                        val hashBase64 = cryptoManager.generateHash(bytesToHash)
                        val signature = cryptoManager.signData(hashBase64)
                        val publicKey = cryptoManager.getPublicKeyBase64()

                        val signatureJson = """{"hash":"$hashBase64","signature":"$signature","publicKey":"$publicKey"}"""

                        val userName = userProfileDataStore.userName.first()
                        val artistName = if (userName.isNotBlank()) userName else "Universal Watermark User"
                        
                        val lat = metadata["LATITUDE"]?.toDoubleOrNull()
                        val lon = metadata["LONGITUDE"]?.toDoubleOrNull()
                        val alt = metadata["ALTITUDE"]?.toDoubleOrNull()

                        com.universalwatermark.engine.metadata.ExifWriter.writeSignatureAndCloneExif(
                            context = context, 
                            originalUri = imageUri,
                            outputUri = outputUri, 
                            signatureJson = signatureJson,
                            artistName = artistName,
                            lat = lat,
                            lon = lon,
                            alt = alt
                        )
                        
                        finalMetadataJson = "$metadata, SIGNATURE_EMBEDDED: $signature"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                // Release pending state after EVERYTHING is written
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                    resolver.update(outputUri, contentValues, null, null)
                } else {
                    try {
                        val intent = android.content.Intent(android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                        intent.data = outputUri
                        context.sendBroadcast(intent)
                    } catch (e: Exception) {}
                }
                
                // 6. Save History
                historyDao.insertHistory(
                    WatermarkHistoryEntity(
                        originalUri = imageUriString,
                        watermarkedUri = outputUri.toString(),
                        templateId = 0L,
                        processedAt = System.currentTimeMillis(),
                        metadataJson = finalMetadataJson,
                        status = "SUCCESS"
                    )
                )

                // 7. Post Notification
                try {
                    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel(
                            "watermark_success",
                            "Watermark Success",
                            NotificationManager.IMPORTANCE_DEFAULT
                        )
                        notificationManager.createNotificationChannel(channel)
                    }

                    var thumbnail: Bitmap? = null
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            thumbnail = resolver.loadThumbnail(outputUri, Size(512, 512), null)
                        } else {
                            @Suppress("DEPRECATION")
                            thumbnail = MediaStore.Images.Media.getBitmap(resolver, outputUri)
                        }
                    } catch (e: Exception) { e.printStackTrace() }

                    val intent = Intent(context, Class.forName("com.universalwatermark.MainActivity")).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    val pendingIntent: PendingIntent = PendingIntent.getActivity(
                        context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    )

                    val builder = NotificationCompat.Builder(context, "watermark_success")
                        .setSmallIcon(android.R.drawable.ic_menu_gallery)
                        .setContentTitle("บันทึกภาพลายน้ำสำเร็จ")
                        .setContentText("แตะเพื่อดูรูปภาพ")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)

                    if (thumbnail != null) {
                        builder.setLargeIcon(thumbnail)
                        builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(thumbnail).bigLargeIcon(null as Bitmap?))
                    }

                    notificationManager.notify(outputUri.hashCode(), builder.build())
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                Result.success()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    resolver.delete(outputUri, null, null)
                }
                historyDao.insertHistory(
                    WatermarkHistoryEntity(
                        originalUri = imageUriString,
                        watermarkedUri = "",
                        templateId = 0L,
                        processedAt = System.currentTimeMillis(),
                        metadataJson = metadata.toString(),
                        status = "FAILED"
                    )
                )
                Result.failure()
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}
