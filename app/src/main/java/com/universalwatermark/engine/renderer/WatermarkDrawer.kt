package com.universalwatermark.engine.renderer

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import java.text.SimpleDateFormat
import java.util.Locale

object WatermarkDrawer {

    fun draw(canvas: Canvas, width: Int, height: Int, config: OverlayConfig, scale: Float = width / 1080f) {
        
        // 1. Draw Compass Graphic (Top-Left)
        if (config.showCompass || config.compassEnabled) {
             val compassSize = 250f * scale
             val compassMargin = 50f * scale
             val cx = compassMargin + compassSize/2
             val cy = compassMargin + compassSize/2
             val heading = if (config.compassEnabled) config.compassHeading else config.azimuth
             drawCompass(canvas, cx, cy, compassSize/2, heading)
        }

        // 2. Draw Text Layouts
        drawClassicLayout(canvas, width, height, config, scale)
        
        // 3. Draw Logo (Top Right)
        if (config.showLogo && config.logoBitmap != null) {
            drawLogo(canvas, width, height, config.logoBitmap, scale)
        }
    }

    private fun drawLogo(canvas: Canvas, width: Int, height: Int, bitmap: Bitmap, scale: Float) {
        val targetWidth = width * 0.15f
        val ratio = bitmap.height.toFloat() / bitmap.width.toFloat()
        val targetHeight = targetWidth * ratio
        
        val margin = 40f * scale
        val x = width - targetWidth - margin
        val y = margin + 50f * scale 
        
        val dst = RectF(x, y, x + targetWidth, y + targetHeight)
        canvas.drawBitmap(bitmap, null, dst, null)
    }

    private fun drawClassicLayout(canvas: Canvas, width: Int, height: Int, config: OverlayConfig, scale: Float) {
        val paint = Paint().apply {
            color = config.textColor
            textSize = config.textSize * scale
            alpha = config.alpha
            isAntiAlias = true
            style = Paint.Style.FILL
            if (config.textShadowEnabled) {
                setShadowLayer(5f * scale, 2f * scale, 2f * scale, Color.BLACK)
            }
            
            val baseTypeface = when (config.googleFontName) {
                "Oswald" -> Typeface.create("sans-serif-condensed", Typeface.BOLD)
                "Roboto Mono" -> Typeface.MONOSPACE
                "Playfair Display" -> Typeface.SERIF
                "Inter" -> Typeface.SANS_SERIF
                "Cursive" -> Typeface.create("cursive", Typeface.NORMAL)
                else -> Typeface.SANS_SERIF
            }
            
            val style = if (config.textStyle == 1) Typeface.BOLD else Typeface.NORMAL
            typeface = Typeface.create(baseTypeface, style)
        }

        val allLines = mutableListOf<String>()

        val defaultOrder = listOf(
            com.universalwatermark.data.WatermarkItemType.DATE_TIME,
            com.universalwatermark.data.WatermarkItemType.ADDRESS,
            com.universalwatermark.data.WatermarkItemType.GPS,
            com.universalwatermark.data.WatermarkItemType.COMPASS,
            com.universalwatermark.data.WatermarkItemType.ALTITUDE_SPEED,
            com.universalwatermark.data.WatermarkItemType.PROJECT_NAME,
            com.universalwatermark.data.WatermarkItemType.INSPECTOR_NAME,
            com.universalwatermark.data.WatermarkItemType.NOTE,
            com.universalwatermark.data.WatermarkItemType.TAGS,
            com.universalwatermark.data.WatermarkItemType.CUSTOM_TEXT
        )
        val orderToUse = if (config.textOrder.isNotEmpty()) config.textOrder else defaultOrder

        for (itemType in orderToUse) {
            when (itemType) {
                com.universalwatermark.data.WatermarkItemType.DATE_TIME -> {
                    if (config.showDate || config.showTime) {
                        val formatStr = if (config.showDate && config.showTime) "dd/MM/yyyy HH:mm:ss" 
                                        else if (config.showDate) "dd/MM/yyyy" 
                                        else "HH:mm:ss"
                        val dateText = com.universalwatermark.util.OverlayUtils.getFormattedDate(config.date, formatStr, config.useThaiLocale)
                        allLines.add(dateText)
                    }
                }
                com.universalwatermark.data.WatermarkItemType.ADDRESS -> {
                    if (config.showAddress && config.address.isNotEmpty()) {
                        config.address.split("\n").forEach { allLines.add(it) }
                    }
                }
                com.universalwatermark.data.WatermarkItemType.GPS -> {
                    if (config.showLatLon) {
                        if (config.latLon.isNotEmpty()) {
                            allLines.add(config.latLon)
                        } else {
                            allLines.add("GPS: EMPTY_STRING")
                        }
                    }
                }
                com.universalwatermark.data.WatermarkItemType.COMPASS -> {
                    if (config.showCompass || config.compassEnabled) {
                         val heading = if (config.compassEnabled) config.compassHeading else config.azimuth
                         val directions = arrayOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")
                         val index = Math.round(((heading % 360) / 45)).toInt() % 8
                         allLines.add("Heading: ${heading.toInt()}° ${directions[index]}")
                    }
                }
                com.universalwatermark.data.WatermarkItemType.ALTITUDE_SPEED -> {
                    if (config.altitudeEnabled || config.speedEnabled) {
                         val parts = mutableListOf<String>()
                         if (config.altitudeEnabled) parts.add("Alt: %.1f m".format(config.altitude))
                         if (config.speedEnabled) parts.add("Spd: %.1f km/h".format(config.speed * 3.6f))
                         if (parts.isNotEmpty()) allLines.add(parts.joinToString(" "))
                    } else if (config.showAltitudeSpeed && config.altitudeSpeed.isNotEmpty()) {
                        allLines.add(config.altitudeSpeed)
                    }
                }
                com.universalwatermark.data.WatermarkItemType.PROJECT_NAME -> {
                    if (config.showOrg && config.orgName.isNotEmpty()) allLines.add(config.orgName)
                }
                com.universalwatermark.data.WatermarkItemType.INSPECTOR_NAME -> {
                    if (config.showUser && config.userName.isNotEmpty()) allLines.add(config.userName)
                }
                com.universalwatermark.data.WatermarkItemType.NOTE -> {
                    if (config.showDevice && config.deviceAlias.isNotEmpty()) allLines.add(config.deviceAlias)
                }
                com.universalwatermark.data.WatermarkItemType.TAGS -> {
                    if (config.showAsset && config.assetNumber.isNotEmpty()) allLines.add(config.assetNumber)
                }
                com.universalwatermark.data.WatermarkItemType.CUSTOM_TEXT -> {
                    if (config.showCustomText && config.customText.isNotEmpty()) {
                        config.customText.split("\n").forEach { allLines.add(it) }
                    }
                }
                else -> {}
            }
        }

        if (allLines.isEmpty()) return

        var maxWidth = 0f
        var totalHeight = 0f
        val lineHeights = mutableListOf<Float>()
        val bounds = Rect()

        allLines.forEach { line ->
            paint.getTextBounds(line, 0, line.length, bounds)
            val measureWidth = paint.measureText(line)
            val lineH = bounds.height() + (config.textSize * 0.5f) 
            if (measureWidth > maxWidth) maxWidth = measureWidth
            lineHeights.add(lineH)
            totalHeight += lineH
        }

        val padding = 40f * scale
        val totalBlockWidth = maxWidth
        val totalBlockHeight = totalHeight

        var blockX = padding
        var blockY = padding

        when (config.position) {
            OverlayPosition.TOP_LEFT -> { blockX = padding; blockY = padding }
            OverlayPosition.TOP_CENTER -> { blockX = (width - totalBlockWidth) / 2; blockY = padding }
            OverlayPosition.TOP_RIGHT -> { blockX = width - totalBlockWidth - padding; blockY = padding }
            OverlayPosition.CENTER_LEFT -> { blockX = padding; blockY = (height - totalBlockHeight) / 2 }
            OverlayPosition.CENTER -> { blockX = (width - totalBlockWidth) / 2; blockY = (height - totalBlockHeight) / 2 }
            OverlayPosition.CENTER_RIGHT -> { blockX = width - totalBlockWidth - padding; blockY = (height - totalBlockHeight) / 2 }
            OverlayPosition.BOTTOM_LEFT -> { blockX = padding; blockY = height - totalBlockHeight - padding }
            OverlayPosition.BOTTOM_CENTER -> { blockX = (width - totalBlockWidth) / 2; blockY = height - totalBlockHeight - padding }
            OverlayPosition.BOTTOM_RIGHT -> { blockX = width - totalBlockWidth - padding; blockY = height - totalBlockHeight - padding }
        }
        
        val textOnLeft = when (config.position) {
            OverlayPosition.TOP_LEFT, OverlayPosition.CENTER_LEFT, OverlayPosition.BOTTOM_LEFT -> true 
            else -> false 
        }
        
        val textX = blockX

        if (config.textBackgroundEnabled && allLines.isNotEmpty()) {
             val bgPadding = 20f * scale
             val bgRect = Rect(
                 (textX - bgPadding).toInt(),
                 (blockY - bgPadding).toInt(),
                 (textX + maxWidth + bgPadding).toInt(),
                 (blockY + totalHeight + bgPadding).toInt()
             )
             
             val bgPaint = Paint().apply {
                 color = Color.BLACK
                 alpha = 100
                 style = Paint.Style.FILL
             }
             canvas.drawRect(bgRect, bgPaint)
        }
        
        if (config.textStrokeEnabled) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = config.textStrokeWidth * scale
            paint.color = config.textStrokeColor
            
            var strokeY = blockY + (lineHeights.firstOrNull() ?: config.textSize)
            
            allLines.forEachIndexed { index, line ->
                 if (!textOnLeft) {
                    paint.textAlign = Paint.Align.RIGHT
                    canvas.drawText(line, textX + maxWidth, strokeY, paint)
                } else {
                    paint.textAlign = Paint.Align.LEFT
                    canvas.drawText(line, textX, strokeY, paint)
                }
                if (index < lineHeights.size -1) {
                    strokeY += lineHeights[index + 1]
                }
            }
        }
        
        paint.style = Paint.Style.FILL
        paint.color = config.textColor
        paint.textAlign = Paint.Align.LEFT 
        
        var currentY = blockY + (lineHeights.firstOrNull() ?: config.textSize)
        
        allLines.forEachIndexed { index, line ->
            if (!textOnLeft) {
                paint.textAlign = Paint.Align.RIGHT
                canvas.drawText(line, textX + maxWidth, currentY, paint)
            } else {
                paint.textAlign = Paint.Align.LEFT
                canvas.drawText(line, textX, currentY, paint)
            }
            if (index < lineHeights.size -1) {
                currentY += lineHeights[index + 1]
            }
        }
    }

    fun drawCompass(canvas: Canvas, cx: Float, cy: Float, radius: Float, heading: Float) {
        val dialColor = 0xCC000000.toInt()
        val tickColor = Color.WHITE
        val tickColorMinor = Color.GRAY
        val cardinalColor = Color.WHITE
        val northColor = 0xFFFF5722.toInt()
        val centerTextColor = 0xFFFF5722.toInt()
        val centerSubTextColor = Color.WHITE

        val bgPaint = Paint().apply {
            color = dialColor
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        canvas.drawCircle(cx, cy, radius, bgPaint)

        val indicatorPaint = Paint().apply {
            color = northColor
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        val indicatorSize = radius * 0.15f
        val indicatorPath = Path().apply {
            val tipY = cy - radius + (radius * 0.1f)
            moveTo(cx, tipY)
            lineTo(cx - indicatorSize/2, tipY + indicatorSize)
            lineTo(cx + indicatorSize/2, tipY + indicatorSize)
            close()
        }
        canvas.drawPath(indicatorPath, indicatorPaint)

        canvas.save()
        canvas.rotate(-heading, cx, cy)

        val textPaint = Paint().apply {
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
            typeface = Typeface.DEFAULT_BOLD
        }

        val tickPaint = Paint().apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
        }

        for (i in 0 until 360 step 2) {
            if (i % 5 != 0) continue

            val angleRad = Math.toRadians((i - 90).toDouble())
            val cosAngle = Math.cos(angleRad).toFloat()
            val sinAngle = Math.sin(angleRad).toFloat()

            val isCardinal = i % 90 == 0
            val isMajor = i % 30 == 0

            val tickLen = if (isCardinal) radius * 0.15f else if (isMajor) radius * 0.1f else radius * 0.05f
            val startRadius = radius - tickLen - (radius * 0.05f)
            val stopRadius = radius - (radius * 0.05f)

            val strokeScale = radius / 100f
            tickPaint.strokeWidth = if (isCardinal) 5f * strokeScale else if (isMajor) 3f * strokeScale else 1f * strokeScale
            tickPaint.color = if (isCardinal || isMajor) tickColor else tickColorMinor

            canvas.drawLine(
                cx + startRadius * cosAngle, cy + startRadius * sinAngle,
                cx + stopRadius * cosAngle, cy + stopRadius * sinAngle,
                tickPaint
            )

            if (isCardinal) {
                val label = when(i) {
                    0 -> "N"
                    90 -> "E"
                    180 -> "S"
                    270 -> "W"
                    else -> ""
                }
                textPaint.color = if (i == 0) northColor else cardinalColor
                textPaint.textSize = radius * 0.25f
                val fontMetrics = textPaint.fontMetrics
                val offset = (fontMetrics.descent + fontMetrics.ascent) / 2
                canvas.drawText(label, cx + (startRadius - radius * 0.15f) * cosAngle, cy + (startRadius - radius * 0.15f) * sinAngle - offset, textPaint)
            } else if (isMajor) {
                textPaint.color = tickColor
                textPaint.textSize = radius * 0.15f
                val fontMetrics = textPaint.fontMetrics
                val offset = (fontMetrics.descent + fontMetrics.ascent) / 2
                canvas.drawText(i.toString(), cx + (startRadius - radius * 0.15f) * cosAngle, cy + (startRadius - radius * 0.15f) * sinAngle - offset, textPaint)
            }
        }
        canvas.restore()

        val degrees = heading.toInt()
        val directions = arrayOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")
        val index = Math.round(((degrees % 360) / 45.0)).toInt() % 8
        val direction = directions[index]

        textPaint.color = centerTextColor
        textPaint.textSize = radius * 0.4f
        val fontMetrics = textPaint.fontMetrics
        val centerOffset = (fontMetrics.descent + fontMetrics.ascent) / 2
        canvas.drawText("$degrees°", cx, cy + (radius * -0.15f) - centerOffset, textPaint)

        textPaint.color = centerSubTextColor
        textPaint.textSize = radius * 0.35f
        canvas.drawText(direction, cx, cy + (radius * 0.25f) - centerOffset, textPaint)
    }
}
