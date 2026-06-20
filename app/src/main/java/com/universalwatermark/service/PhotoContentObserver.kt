package com.universalwatermark.service

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoContentObserver(
    private val context: Context,
    private val onNewPhotoDetected: (Uri) -> Unit
) : ContentObserver(Handler(Looper.getMainLooper())) {

    private var lastCheckedId: Long = -1

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        
        CoroutineScope(Dispatchers.IO).launch {
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED
            )
            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

            context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                    val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    val id = cursor.getLong(idColumn)
                    val filePath = cursor.getString(dataColumn) ?: ""

                    // Prevent processing our own watermarked images and duplicate IDs
                    if (id != lastCheckedId && !filePath.contains("UniversalWatermark")) {
                        lastCheckedId = id
                        val contentUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())
                        
                        onNewPhotoDetected(contentUri)
                    }
                }
            }
        }
    }
}
