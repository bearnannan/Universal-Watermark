package com.universalwatermark.engine.metadata

import android.content.Context
import android.net.Uri
import androidx.exifinterface.media.ExifInterface

class ExifWriter {
    
    companion object {
        
        // Tags to copy from original image
        private val TAGS_TO_COPY = listOf(
            ExifInterface.TAG_DATETIME_ORIGINAL,
            ExifInterface.TAG_DATETIME_DIGITIZED,
            ExifInterface.TAG_GPS_LATITUDE,
            ExifInterface.TAG_GPS_LATITUDE_REF,
            ExifInterface.TAG_GPS_LONGITUDE,
            ExifInterface.TAG_GPS_LONGITUDE_REF,
            ExifInterface.TAG_GPS_ALTITUDE,
            ExifInterface.TAG_GPS_ALTITUDE_REF,
            ExifInterface.TAG_GPS_TIMESTAMP,
            ExifInterface.TAG_GPS_DATESTAMP,
            ExifInterface.TAG_MAKE,
            ExifInterface.TAG_MODEL,
            ExifInterface.TAG_FOCAL_LENGTH,
            ExifInterface.TAG_F_NUMBER,
            ExifInterface.TAG_EXPOSURE_TIME,
            ExifInterface.TAG_PHOTOGRAPHIC_SENSITIVITY, // ISO
            ExifInterface.TAG_WHITE_BALANCE,
            ExifInterface.TAG_LENS_MAKE,
            ExifInterface.TAG_LENS_MODEL,
            ExifInterface.TAG_LENS_SPECIFICATION
        )

        fun writeSignatureAndCloneExif(
            context: Context, 
            originalUri: Uri, 
            outputUri: Uri, 
            signatureJson: String,
            artistName: String = "Universal Watermark User",
            lat: Double? = null,
            lon: Double? = null,
            alt: Double? = null
        ): Boolean {
            return try {
                // 1. Read original EXIF
                var originalExif: ExifInterface? = null
                try {
                    context.contentResolver.openInputStream(originalUri)?.use { inputStream ->
                        originalExif = ExifInterface(inputStream)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                // 2. Open output file to rewrite EXIF
                val pfd = context.contentResolver.openFileDescriptor(outputUri, "rw")
                if (pfd != null) {
                    val outputExif = ExifInterface(pfd.fileDescriptor)
                    
                    // 3. Clone tags
                    originalExif?.let { orig ->
                        for (tag in TAGS_TO_COPY) {
                            val value = orig.getAttribute(tag)
                            if (value != null) {
                                outputExif.setAttribute(tag, value)
                            }
                        }
                    }

                    // 4. Append Custom Tags
                    outputExif.setAttribute(ExifInterface.TAG_SOFTWARE, "Universal Watermark v1.0")
                    outputExif.setAttribute(ExifInterface.TAG_ARTIST, artistName)
                    outputExif.setAttribute(ExifInterface.TAG_USER_COMMENT, signatureJson)
                    
                    // Explicitly inject GPS if provided
                    if (lat != null && lon != null) {
                        outputExif.setLatLong(lat, lon)
                    }
                    if (alt != null) {
                        outputExif.setAltitude(alt)
                    }
                    
                    // 5. Save
                    outputExif.saveAttributes()
                    pfd.close()
                    true
                } else {
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}
