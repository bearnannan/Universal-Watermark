package com.universalwatermark.engine.metadata

import android.content.Context
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExifDataExtractor @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun extractExif(uri: Uri): Map<String, String> {
        val exifData = mutableMapOf<String, String>()
        try {
            val inputStream: InputStream? = try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    val originalUri = android.provider.MediaStore.setRequireOriginal(uri)
                    context.contentResolver.openInputStream(originalUri)
                } else {
                    context.contentResolver.openInputStream(uri)
                }
            } catch (e: Exception) {
                // Fallback to redacted URI if ACCESS_MEDIA_LOCATION is not granted or URI is not supported
                context.contentResolver.openInputStream(uri)
            }
            
            inputStream?.use {
                val exif = ExifInterface(it)
                
                exif.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL)?.let { date ->
                    exifData["DATETIME_ORIGINAL"] = date
                } ?: exif.getAttribute(ExifInterface.TAG_DATETIME)?.let { date ->
                    exifData["DATETIME_ORIGINAL"] = date
                }
                
                // Get Location
                val latLong = exif.latLong
                if (latLong != null && latLong.size == 2) {
                    exifData["GPS_LATITUDE"] = latLong[0].toString()
                    exifData["GPS_LONGITUDE"] = latLong[1].toString()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return exifData
    }
}
