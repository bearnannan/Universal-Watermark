package com.universalwatermark.engine.renderer

import android.graphics.Bitmap
import android.graphics.Color
import java.util.Date

enum class OverlayPosition {
    TOP_LEFT, TOP_CENTER, TOP_RIGHT,
    CENTER_LEFT, CENTER, CENTER_RIGHT,
    BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT
}

data class OverlayConfig(
    val position: OverlayPosition = OverlayPosition.BOTTOM_RIGHT,
    val date: Date = Date(),
    
    // Data Fields
    val address: String = "",
    val latLon: String = "",
    val altitudeSpeed: String = "",
    val resolution: String = "", 
    val customText: String = "",
    val azimuth: Float = 0f,
    
    // Appearance
    val textColor: Int = Color.WHITE,
    val textSize: Float = 36f, 
    val alpha: Int = 255,

    // Date/Time Formats
    val datePattern: String = "dd/MM/yyyy",
    val showTime: Boolean = true,
    val useThaiLocale: Boolean = false,
    
    // New Styling
    val textShadowEnabled: Boolean = false,
    val textBackgroundEnabled: Boolean = false,
    val textStyle: Int = 0, // 0=Default, 1=Bold
    val fontFamily: String = "sans", 
    
    // Rich Data 
    val compassEnabled: Boolean = false,
    val altitudeEnabled: Boolean = false,
    val speedEnabled: Boolean = false,
    
    // Live Data
    val compassHeading: Float = 0f, 
    val altitude: Double = 0.0, 
    val speed: Float = 0f, 
    
    // Workflow
    val userName: String = "",
    val orgName: String = "",
    val deviceAlias: String = "",
    val assetNumber: String = "",

    // Typography
    val textStrokeEnabled: Boolean = false,
    val textStrokeWidth: Float = 3f,
    val textStrokeColor: Int = Color.BLACK,
    val googleFontName: String = "Roboto",

    // Toggles
    val showDate: Boolean = true,
    val showAddress: Boolean = true,
    val showLatLon: Boolean = true,
    val showAltitudeSpeed: Boolean = false,
    val showResolution: Boolean = false,
    val showCustomText: Boolean = false,
    val showCompass: Boolean = false,
    
    val showUser: Boolean = false,
    val showOrg: Boolean = false,
    val showDevice: Boolean = false,
    val showAsset: Boolean = false,
    val showLogo: Boolean = false,
    
    // Style Template (0=Classic, 1=Modern, 2=Minimal)
    val templateId: Int = 0,
    
    // Custom Text Order
    val textOrder: List<com.universalwatermark.data.WatermarkItemType> = emptyList(),
    
    // Logo
    val logoBitmap: Bitmap? = null
)
