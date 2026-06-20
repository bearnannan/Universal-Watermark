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
            
            // 2. Calculate sample size for large images (e.g. > 4000px)
            options.inSampleSize = bitmapProcessor.calculateInSampleSize(options, 4000)
            options.inJustDecodeBounds = false
            options.inMutable = true // We need a mutable bitmap to draw on
            
            // 3. Decode actual bitmap
            val bitmap = contentResolver.openInputStream(sourceUri)?.use {
                BitmapFactory.decodeStream(it, null, options)
            } ?: return@withContext Result.failure(Exception("Failed to decode image"))

            val canvas = Canvas(bitmap)

            // 4. Draw Watermark using ported Drawer
            WatermarkDrawer.draw(
                canvas = canvas,
                width = bitmap.width,
                height = bitmap.height,
                config = config
            )
            
            // 6. Save to output stream
            outputStream.use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
            }
            
            // 7. Cleanup memory
            bitmap.recycle()
            
            Result.success(Unit)
            
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
