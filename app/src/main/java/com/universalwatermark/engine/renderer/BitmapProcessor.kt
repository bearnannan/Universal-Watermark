package com.universalwatermark.engine.renderer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BitmapProcessor @Inject constructor() {

    fun calculateInSampleSize(options: BitmapFactory.Options, maxDimension: Int): Int {
        val (width, height) = options.outWidth to options.outHeight
        var inSampleSize = 1
        val actualMax = if (maxDimension > 12000) 12000 else maxDimension
        while (width / inSampleSize > actualMax || height / inSampleSize > actualMax) {
            inSampleSize *= 2
        }
        return inSampleSize
    }
    
    // Additional memory-efficient bitmap functions could go here
}
