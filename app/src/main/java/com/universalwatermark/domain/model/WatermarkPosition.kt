package com.universalwatermark.domain.model

enum class PositionType {
    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, CENTER, CUSTOM
}

data class WatermarkPosition(
    val type: PositionType = PositionType.BOTTOM_RIGHT,
    val marginX: Float = 16f,
    val marginY: Float = 16f,
    val customX: Float = 0f,
    val customY: Float = 0f
)
