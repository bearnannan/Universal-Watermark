package com.universalwatermark.engine.renderer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.text.StaticLayout
import android.text.TextPaint
import com.universalwatermark.domain.model.PositionType
import com.universalwatermark.domain.model.WatermarkPosition
import com.universalwatermark.domain.model.WatermarkStyle
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextRenderer @Inject constructor() {

    fun drawText(
        canvas: Canvas,
        text: String,
        style: WatermarkStyle,
        position: WatermarkPosition,
        canvasWidth: Int,
        canvasHeight: Int
    ) {
        if (text.isBlank()) return

        val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor(style.fontColorHex)
            textSize = style.fontSizeSp * 3f // Simple scaling for high-res images
            typeface = Typeface.DEFAULT_BOLD // Or load from style.fontFamily
            if (style.hasShadow) {
                setShadowLayer(
                    style.shadowRadius,
                    style.shadowDx,
                    style.shadowDy,
                    Color.parseColor(style.shadowColorHex)
                )
            }
        }

        // Layout for multi-line support
        @Suppress("DEPRECATION")
        val staticLayout = StaticLayout(
            text, textPaint, canvasWidth,
            android.text.Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false
        )

        val textWidth = staticLayout.width.toFloat()
        val textHeight = staticLayout.height.toFloat()

        var startX = 0f
        var startY = 0f

        val marginX = position.marginX * 3f
        val marginY = position.marginY * 3f

        when (position.type) {
            PositionType.TOP_LEFT -> {
                startX = marginX
                startY = marginY
            }
            PositionType.TOP_RIGHT -> {
                startX = canvasWidth - textWidth - marginX
                startY = marginY
            }
            PositionType.BOTTOM_LEFT -> {
                startX = marginX
                startY = canvasHeight - textHeight - marginY
            }
            PositionType.BOTTOM_RIGHT -> {
                startX = canvasWidth - textWidth - marginX
                startY = canvasHeight - textHeight - marginY
            }
            PositionType.CENTER -> {
                startX = (canvasWidth - textWidth) / 2f
                startY = (canvasHeight - textHeight) / 2f
            }
            PositionType.CUSTOM -> {
                startX = position.customX
                startY = position.customY
            }
        }

        canvas.save()
        canvas.translate(startX, startY)

        // Draw background if needed
        if (style.backgroundOpacity > 0f) {
            val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.parseColor(style.backgroundColorHex)
                alpha = (style.backgroundOpacity * 255).toInt()
            }
            val padding = 10f * 3f
            val bgRect = Rect(
                (-padding).toInt(),
                (-padding).toInt(),
                (textWidth + padding).toInt(),
                (textHeight + padding).toInt()
            )
            canvas.drawRect(bgRect, bgPaint)
        }

        staticLayout.draw(canvas)
        canvas.restore()
    }
}
