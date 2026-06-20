package com.universalwatermark.ui.components

import android.graphics.Color
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.universalwatermark.data.CameraSettings
import com.universalwatermark.engine.renderer.OverlayConfig
import com.universalwatermark.engine.renderer.OverlayPosition
import com.universalwatermark.engine.renderer.WatermarkDrawer
import java.util.Date

@Composable
fun LivePreviewCard(settings: CameraSettings) {
    val context = LocalContext.current

    // Convert CameraSettings to OverlayConfig
    val config = remember(settings) {
        val logoBitmap = if (settings.customLogoPath != null) {
            try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                    android.graphics.ImageDecoder.decodeBitmap(android.graphics.ImageDecoder.createSource(context.contentResolver, android.net.Uri.parse(settings.customLogoPath)))
                } else {
                    @Suppress("DEPRECATION")
                    android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, android.net.Uri.parse(settings.customLogoPath))
                }
            } catch(e:Exception){ null }
        } else null

        OverlayConfig(
            templateId = settings.templateId,
            date = Date(),
            address = if (settings.isAddressEnabled) "123 Mockingbird Lane, Bangkok, Thailand" else "",
            latLon = if (settings.isCoordinatesEnabled) "13.7563° N, 100.5018° E" else "",
            userName = settings.inspectorName,
            orgName = settings.projectName,
            deviceAlias = settings.customNote,
            assetNumber = settings.tags,
            customText = "",
            
            showDate = settings.dateWatermarkEnabled,
            showTime = settings.timeWatermarkEnabled,
            showAddress = settings.isAddressEnabled,
            showLatLon = settings.isCoordinatesEnabled,
            showUser = settings.isInspectorEnabled && settings.inspectorName.isNotEmpty(),
            showOrg = settings.isProjectEnabled && settings.projectName.isNotEmpty(),
            showDevice = settings.isNoteEnabled && settings.customNote.isNotEmpty(),
            showAsset = settings.isTagsEnabled && settings.tags.isNotEmpty(),
            showCustomText = false,
            showLogo = settings.customLogoPath != null,
            logoBitmap = logoBitmap,
            
            position = try { OverlayPosition.valueOf(settings.overlayPosition) } catch (e: Exception) { OverlayPosition.BOTTOM_RIGHT },
            textColor = settings.textColor,
            textSize = settings.textSize,
            alpha = settings.textAlpha,
            datePattern = settings.dateFormat,
            useThaiLocale = settings.isThaiLanguage,
            textShadowEnabled = settings.textShadowEnabled,
            textBackgroundEnabled = settings.textBackgroundEnabled,
            textStyle = settings.textStyle,
            fontFamily = settings.fontFamily,
            textOrder = settings.customTextOrder,
            googleFontName = settings.googleFontName,
            textStrokeEnabled = settings.textStrokeEnabled,
            textStrokeWidth = settings.textStrokeWidth,
            textStrokeColor = settings.textStrokeColor,
            
            compassEnabled = settings.compassEnabled,
            showCompass = settings.compassEnabled,
            compassHeading = 45f,
            azimuth = 45f,
            altitudeEnabled = settings.altitudeEnabled,
            speedEnabled = settings.speedEnabled,
            altitude = 15.5,
            speed = 0f
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(4f / 3f)
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(androidx.compose.ui.graphics.Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        // Mock Image Background (Just a placeholder color or gradient)
        Box(modifier = Modifier.fillMaxSize().background(androidx.compose.ui.graphics.Color(0xFF555555)))
        
        // Watermark Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawIntoCanvas { canvas ->
                // Draw using the actual engine drawer so it matches exactly
                WatermarkDrawer.draw(
                    canvas = canvas.nativeCanvas,
                    width = size.width.toInt(),
                    height = size.height.toInt(),
                    config = config,
                    scale = size.width / 1080f
                )
            }
        }
    }
}
