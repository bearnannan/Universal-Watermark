package com.universalwatermark.engine.renderer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import com.universalwatermark.domain.model.WatermarkPosition
import com.universalwatermark.domain.model.WatermarkStyle
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatermarkRenderer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val textRenderer: TextRenderer,
    private val bitmapProcessor: BitmapProcessor
) {

    suspend fun render(
        sourceUri: Uri,
        outputStream: OutputStream,
        config: OverlayConfig
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val contentResolver = context.contentResolver
            
            // 1. Decode bounds to get image dimensions
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            contentResolver.openInputStream(sourceUri)?.use {
                BitmapFactory.decodeStream(it, null, options)
            }
            
            // 2. Calculate sample size for large images (e.g. > 12000px)
            options.inSampleSize = bitmapProcessor.calculateInSampleSize(options, 12000)
            options.inJustDecodeBounds = false
            options.inMutable = true // We need a mutable bitmap to draw on
            options.inPreferredConfig = Bitmap.Config.ARGB_8888 // Force maximum color depth
            options.inScaled = false // Prevent density-based scaling
            
            // Force sRGB color space on Android O and above to prevent washed-out colors
            // when saving the Bitmap as a JPEG without an ICC profile.
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                options.inPreferredColorSpace = android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB)
            }
            
            var retryCount = 0
            val maxRetries = 3
            var bitmap: Bitmap? = null
            var success = false
            var currentSampleSize = options.inSampleSize

            while (retryCount <= maxRetries && !success) {
                try {
                    // 3. Decode actual bitmap
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                        val source = android.graphics.ImageDecoder.createSource(contentResolver, sourceUri)
                        bitmap = android.graphics.ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                            decoder.allocator = android.graphics.ImageDecoder.ALLOCATOR_SOFTWARE
                            decoder.isMutableRequired = true
                            if (currentSampleSize > 1) {
                                decoder.setTargetSampleSize(currentSampleSize)
                            }
                        }
                    } else {
                        options.inSampleSize = currentSampleSize
                        var decodedBitmap: Bitmap? = contentResolver.openInputStream(sourceUri)?.use {
                            BitmapFactory.decodeStream(it, null, options)
                        }
                        if (decodedBitmap == null) return@withContext Result.failure(Exception("Failed to decode image"))
                        
                        // Handle rotation manually for API < 28
                        val rotationMatrix = android.graphics.Matrix()
                        contentResolver.openInputStream(sourceUri)?.use { stream ->
                            val exif = androidx.exifinterface.media.ExifInterface(stream)
                            val orientation = exif.getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, androidx.exifinterface.media.ExifInterface.ORIENTATION_NORMAL)
                            when (orientation) {
                                androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_90 -> rotationMatrix.postRotate(90f)
                                androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_180 -> rotationMatrix.postRotate(180f)
                                androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_270 -> rotationMatrix.postRotate(270f)
                            }
                        }
                        
                        if (!rotationMatrix.isIdentity) {
                            val rotatedBitmap = Bitmap.createBitmap(decodedBitmap, 0, 0, decodedBitmap.width, decodedBitmap.height, rotationMatrix, true)
                            if (rotatedBitmap != decodedBitmap) {
                                decodedBitmap.recycle()
                                decodedBitmap = rotatedBitmap.copy(Bitmap.Config.ARGB_8888, true)
                                rotatedBitmap.recycle()
                            }
                        }
                        bitmap = decodedBitmap
                    }

                    val finalBitmap = bitmap ?: throw Exception("Failed to decode image")
                    val canvas = Canvas(finalBitmap)

                    // 4. Draw Watermark using ported Drawer
                    WatermarkDrawer.draw(
                        canvas = canvas,
                        width = finalBitmap.width,
                        height = finalBitmap.height,
                        config = config
                    )
                    
                    // 6. Save to output stream
                    outputStream.use { out ->
                        finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                    }
                    success = true

                } catch (e: OutOfMemoryError) {
                    bitmap?.recycle()
                    bitmap = null
                    System.gc()
                    retryCount++
                    currentSampleSize *= 2
                    if (retryCount > maxRetries) {
                        return@withContext Result.failure(Exception("Out of memory after retries: ${e.message}"))
                    }
                }
            }
            
            // 7. Cleanup memory
            bitmap?.recycle()
            
            if (success) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to render watermark"))
            }
            
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
