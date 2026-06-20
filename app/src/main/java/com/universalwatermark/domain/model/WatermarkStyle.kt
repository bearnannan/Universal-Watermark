package com.universalwatermark.domain.model

data class WatermarkStyle(
    val fontFamily: String = "Default",
    val fontSizeSp: Float = 24f,
    val fontColorHex: String = "#FFFFFF",
    val backgroundColorHex: String = "#000000",
    val backgroundOpacity: Float = 0.5f,
    val hasShadow: Boolean = true,
    val shadowRadius: Float = 4f,
    val shadowDx: Float = 2f,
    val shadowDy: Float = 2f,
    val shadowColorHex: String = "#000000"
)
