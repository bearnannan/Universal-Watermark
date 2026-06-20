package com.universalwatermark.ui.screen.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.universalwatermark.ui.SettingsBottomSheet

@Deprecated("Use new categorized SettingsScreen instead")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsSheetWrapper(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.cameraSettingsFlow.collectAsState()
    val noteHistory by viewModel.noteHistory.collectAsState()
    val savedNotes by viewModel.savedNotes.collectAsState()
    val tagsHistory by viewModel.tagsHistory.collectAsState()

    val logoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.setLogo(uri)
    }

    SettingsBottomSheet(
        sheetState = sheetState,
        onDismiss = onDismiss,
        videoQuality = settings.videoQuality,
        onVideoQualityChange = { viewModel.updateVideoQuality(it) },
        aspectRatio = settings.aspectRatio,
        onAspectRatioChange = { viewModel.updateAspectRatio(it) },
        dateWatermarkEnabled = settings.dateWatermarkEnabled,
        onDateWatermarkChange = { viewModel.updateDateWatermark(it) },
        timeWatermarkEnabled = settings.timeWatermarkEnabled,
        onTimeWatermarkChange = { viewModel.updateTimeWatermark(it) },
        shutterSoundEnabled = settings.shutterSoundEnabled,
        onShutterSoundChange = { viewModel.updateShutterSound(it) },
        gridLinesEnabled = settings.gridLinesEnabled,
        onGridLinesChange = { viewModel.updateGridLines(it) },
        virtualLevelerEnabled = settings.virtualLevelerEnabled,
        onVirtualLevelerChange = { viewModel.updateVirtualLevelerEnabled(it) },
        volumeShutterEnabled = settings.volumeShutterEnabled,
        onVolumeShutterChange = { viewModel.updateVolumeShutterEnabled(it) },
        flipFrontPhoto = settings.flipFrontPhoto,
        onFlipFrontPhotoChange = { viewModel.updateFlipFrontPhoto(it) },
        imageFormat = settings.imageFormat,
        onImageFormatChange = { viewModel.updateImageFormat(it) },
        compressionQuality = settings.compressionQuality,
        onCompressionQualityChange = { viewModel.updateCompressionQuality(it) },
        saveExif = settings.saveExif,
        onSaveExifChange = { viewModel.updateSaveExif(it) },
        customSavePath = settings.customSavePath,
        onCustomSavePathChange = { viewModel.updateCustomSavePath(it) },
        batterySaverMode = settings.batterySaverMode,
        onBatterySaverModeChange = { viewModel.updateBatterySaverMode(it) },
        customNote = settings.customNote,
        onCustomNoteChange = { viewModel.updateCustomNote(it) },
        dateFormat = settings.dateFormat,
        onDateFormatChange = { viewModel.updateDateFormat(it) },
        useThaiLocale = settings.isThaiLanguage,
        onUseThaiLocaleChange = { viewModel.updateThaiLanguage(it) },
        isDarkTheme = settings.isDarkTheme,
        onDarkThemeChange = { viewModel.updateDarkTheme(it) },
        textShadowEnabled = settings.textShadowEnabled,
        onTextShadowChange = { viewModel.updateTextShadow(it) },
        textBackgroundEnabled = settings.textBackgroundEnabled,
        onTextBackgroundChange = { viewModel.updateTextBackground(it) },
        textColor = settings.textColor,
        onTextColorChange = { viewModel.updateTextColor(it) },
        textSize = settings.textSize,
        onTextSizeChange = { viewModel.updateTextSize(it) },
        textStyle = settings.textStyle,
        onTextStyleChange = { viewModel.updateTextStyle(it) },
        textAlpha = settings.textAlpha,
        onTextAlphaChange = { viewModel.updateTextAlpha(it) },
        fontFamily = settings.fontFamily,
        onFontFamilyChange = { viewModel.updateFontFamily(it) },
        overlayPosition = settings.overlayPosition,
        onOverlayPositionChange = { viewModel.updateOverlayPosition(it) },
        compassEnabled = settings.compassEnabled,
        onCompassChange = { viewModel.updateCompassEnabled(it) },
        altitudeEnabled = settings.altitudeEnabled,
        onAltitudeChange = { viewModel.updateAltitudeEnabled(it) },
        speedEnabled = settings.speedEnabled,
        onSpeedChange = { viewModel.updateSpeedEnabled(it) },
        
        addressEnabled = settings.isAddressEnabled,
        onAddressEnabledChange = { viewModel.updateAddressEnabled(it) },
        coordinatesEnabled = settings.isCoordinatesEnabled,
        onCoordinatesEnabledChange = { viewModel.updateCoordinatesEnabled(it) },
        gpsFormat = settings.gpsFormat,
        onGpsFormatChange = { viewModel.updateGpsFormat(it) },
        addressResolution = settings.addressResolution,
        onAddressResolutionChange = { viewModel.updateAddressResolution(it) },

        cloudPath = settings.cloudPath,
        onCloudPathChange = { viewModel.updateCloudPath(it) },
        targetWidth = settings.targetWidth,
        targetHeight = settings.targetHeight,
        supportedResolutions = emptyList(), // Placeholder for now
        onTargetResolutionChange = { w, h -> viewModel.updateTargetResolution(w, h) },
        hasLogo = settings.customLogoPath != null,
        onLogoSelect = { logoPickerLauncher.launch("image/*") },
        onLogoRemove = { viewModel.setLogo(null) },
        projectName = settings.projectName,
        onProjectNameChange = { viewModel.updateProjectName(it) },
        inspectorName = settings.inspectorName,
        onInspectorNameChange = { viewModel.updateInspectorName(it) },
        tags = settings.tags,
        onTagsChange = { viewModel.updateTags(it) },
        noteHistory = noteHistory.toList(),
        savedNotes = savedNotes,
        onAddSavedNote = { viewModel.addSavedNote(it) },
        onRemoveSavedNote = { viewModel.removeSavedNote(it) },
        onClearSavedNotes = { viewModel.clearSavedNotes() },
        onImportSavedNotes = { viewModel.importSavedNotes(it) },
        tagsHistory = tagsHistory.toList(),
        customFields = settings.customFields,
        onCustomFieldsChange = { viewModel.updateCustomFields(it) },
        textStrokeEnabled = settings.textStrokeEnabled,
        onTextStrokeEnabledChange = { viewModel.updateTextStrokeEnabled(it) },
        textStrokeWidth = settings.textStrokeWidth,
        onTextStrokeWidthChange = { viewModel.updateTextStrokeWidth(it) },
        textStrokeColor = settings.textStrokeColor,
        onTextStrokeColorChange = { viewModel.updateTextStrokeColor(it) },
        googleFontName = settings.googleFontName,
        onGoogleFontNameChange = { viewModel.updateGoogleFontName(it) },
        templateId = settings.templateId,
        onTemplateIdChange = { viewModel.updateTemplateId(it) },
        compassPosition = settings.compassPosition,
        onCompassPositionChange = { viewModel.updateCompassPosition(it) },
        customTextOrder = settings.customTextOrder,
        onCustomTextOrderChange = { viewModel.updateTextOrder(it) },
        availableTags = settings.availableTags,
        onAddTag = { viewModel.addAvailableTag(it) },
        onRemoveTag = { viewModel.removeAvailableTag(it) },
        onClearTags = { viewModel.clearAvailableTags() },
        onImportTags = { viewModel.importTags(it) },
        saveOriginalPhoto = settings.saveOriginalPhoto,
        onSaveOriginalPhotoChange = { viewModel.updateSaveOriginalPhoto(it) },
        fileNameFormat = settings.fileNameFormat,
        onFileNameFormatChange = { viewModel.updateFileNameFormat(it) },
        uploadOnlyWifi = settings.uploadOnlyWifi,
        onUploadOnlyWifiChange = { viewModel.updateUploadOnlyWifi(it) },
        uploadLowBattery = settings.uploadLowBattery,
        onUploadLowBatteryChange = { viewModel.updateUploadLowBattery(it) }
    )
}
