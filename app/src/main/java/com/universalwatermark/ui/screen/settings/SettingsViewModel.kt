package com.universalwatermark.ui.screen.settings

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.universalwatermark.data.CameraSettings
import com.universalwatermark.data.CustomField
import com.universalwatermark.data.FileNameFormat
import com.universalwatermark.data.ImageFormat
import com.universalwatermark.data.LocationFormat
import com.universalwatermark.data.SettingsRepository
import com.universalwatermark.data.WatermarkItemType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val settingsRepository = SettingsRepository(application)

    val cameraSettingsFlow: StateFlow<CameraSettings> = settingsRepository.cameraSettingsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CameraSettings())

    val noteHistory: StateFlow<Set<String>> = settingsRepository.customNoteHistoryFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val savedNotes: StateFlow<Set<String>> = settingsRepository.savedNotesFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val tagsHistory: StateFlow<Set<String>> = settingsRepository.tagsHistoryFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())



    // Update Functions
    fun updateVideoQuality(quality: String) { viewModelScope.launch { settingsRepository.updateVideoQuality(quality) } }
    fun updateAspectRatio(ratio: String) { viewModelScope.launch { settingsRepository.updateAspectRatio(ratio) } }
    fun updateDateWatermark(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateDateWatermark(enabled) } }
    fun updateTimeWatermark(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateTimeWatermark(enabled) } }
    fun updateShutterSound(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateShutterSound(enabled) } }
    fun updateGridLines(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateGridLines(enabled) } }
    fun updateVirtualLevelerEnabled(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateVirtualLevelerEnabled(enabled) } }
    fun updateVolumeShutterEnabled(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateVolumeShutterEnabled(enabled) } }
    
    fun updateFlipFrontPhoto(flip: Boolean) { viewModelScope.launch { settingsRepository.updateFlipFrontPhoto(flip) } }
    fun updateImageFormat(format: ImageFormat) { viewModelScope.launch { settingsRepository.updateImageFormat(format) } }
    fun updateCompressionQuality(quality: Int) { viewModelScope.launch { settingsRepository.updateCompressionQuality(quality) } }
    fun updateSaveExif(save: Boolean) { viewModelScope.launch { settingsRepository.updateSaveExif(save) } }
    fun updateDeleteOriginalPhoto(delete: Boolean) { viewModelScope.launch { settingsRepository.updateDeleteOriginalPhoto(delete) } }
    fun updateCustomSavePath(path: String?) { viewModelScope.launch { settingsRepository.updateCustomSavePath(path) } }
    fun updateBatterySaverMode(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateBatterySaverMode(enabled) } }
    
    fun updateCustomNote(note: String) { viewModelScope.launch { settingsRepository.updateCustomNote(note) } }
    fun updateDateFormat(format: String) { viewModelScope.launch { settingsRepository.updateDateFormat(format) } }
    fun updateThaiLanguage(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateThaiLanguage(enabled) } }
    fun updateDarkTheme(isDark: Boolean) { viewModelScope.launch { settingsRepository.updateDarkTheme(isDark) } }
    
    fun updateTextShadow(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateTextShadow(enabled) } }
    fun updateTextBackground(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateTextBackground(enabled) } }
    fun updateTextColor(color: Int) { viewModelScope.launch { settingsRepository.updateTextColor(color) } }
    fun updateTextSize(size: Float) { viewModelScope.launch { settingsRepository.updateTextSize(size) } }
    fun updateTextStyle(style: Int) { viewModelScope.launch { settingsRepository.updateTextStyle(style) } }
    fun updateTextAlpha(alpha: Int) { viewModelScope.launch { settingsRepository.updateTextAlpha(alpha) } }
    fun updateFontFamily(fontFamily: String) { viewModelScope.launch { settingsRepository.updateFontFamily(fontFamily) } }
    fun updateOverlayPosition(positionName: String) { viewModelScope.launch { settingsRepository.updateOverlayPosition(positionName) } }
    
    fun updateCompassEnabled(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateCompassEnabled(enabled) } }
    fun updateAltitudeEnabled(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateAltitudeEnabled(enabled) } }
    fun updateSpeedEnabled(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateSpeedEnabled(enabled) } }

    fun updateCloudPath(path: String) { viewModelScope.launch { settingsRepository.updateCloudPath(path) } }
    
    fun updateTargetResolution(width: Int, height: Int) { viewModelScope.launch { settingsRepository.updateTargetResolution(width, height) } }
    
    fun setLogo(uri: Uri?) { viewModelScope.launch { settingsRepository.updateCustomLogoPath(uri?.toString()) } }
    
    fun updateProjectName(name: String) { viewModelScope.launch { settingsRepository.updateProjectName(name) } }
    fun updateInspectorName(name: String) { viewModelScope.launch { settingsRepository.updateInspectorName(name) } }
    fun updateTags(tags: String) { viewModelScope.launch { settingsRepository.updateTags(tags) } }
    
    fun updateProjectEnabled(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateProjectEnabled(enabled) } }
    fun updateInspectorEnabled(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateInspectorEnabled(enabled) } }
    fun updateNoteEnabled(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateNoteEnabled(enabled) } }
    fun updateTagsEnabled(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateTagsEnabled(enabled) } }
    fun updateFloatingWidgetEnabled(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateFloatingWidgetEnabled(enabled) } }
    
    fun addSavedNote(note: String) { viewModelScope.launch { settingsRepository.addSavedNote(note) } }
    fun removeSavedNote(note: String) { viewModelScope.launch { settingsRepository.removeSavedNote(note) } }
    fun clearSavedNotes() { viewModelScope.launch { settingsRepository.clearSavedNotes() } }
    fun importSavedNotes(notes: Set<String>) { viewModelScope.launch { settingsRepository.updateSavedNotes(notes) } }
    
    fun updateCustomFields(fields: List<CustomField>) { viewModelScope.launch { settingsRepository.updateCustomFields(fields) } }
    
    fun updateTextStrokeEnabled(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateTextStrokeEnabled(enabled) } }
    fun updateTextStrokeWidth(width: Float) { viewModelScope.launch { settingsRepository.updateTextStrokeWidth(width) } }
    fun updateTextStrokeColor(color: Int) { viewModelScope.launch { settingsRepository.updateTextStrokeColor(color) } }
    fun updateGoogleFontName(fontName: String) { viewModelScope.launch { settingsRepository.updateGoogleFontName(fontName) } }
    fun updateTemplateId(id: Int) { viewModelScope.launch { settingsRepository.updateTemplateId(id) } }
    fun updateCompassPosition(position: String) { viewModelScope.launch { settingsRepository.updateCompassPosition(position) } }
    fun updateTextOrder(order: List<WatermarkItemType>) { viewModelScope.launch { settingsRepository.updateTextOrder(order) } }
    
    fun addAvailableTag(tag: String) { viewModelScope.launch { settingsRepository.addAvailableTag(tag) } }
    fun removeAvailableTag(tag: String) { viewModelScope.launch { settingsRepository.removeAvailableTag(tag) } }
    fun clearAvailableTags() { viewModelScope.launch { settingsRepository.clearAvailableTags() } }
    fun importTags(tags: Set<String>) { viewModelScope.launch { settingsRepository.updateAvailableTags(tags) } }
    
    fun updateSaveOriginalPhoto(save: Boolean) { viewModelScope.launch { settingsRepository.updateSaveOriginalPhoto(save) } }
    fun updateFileNameFormat(format: FileNameFormat) { viewModelScope.launch { settingsRepository.updateFileNameFormat(format) } }
    fun updateUploadOnlyWifi(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateUploadOnlyWifi(enabled) } }
    fun updateUploadLowBattery(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateUploadLowBattery(enabled) } }

    fun updateAddressEnabled(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateAddressEnabled(enabled) } }
    fun updateCoordinatesEnabled(enabled: Boolean) { viewModelScope.launch { settingsRepository.updateCoordinatesEnabled(enabled) } }
    fun updateGpsFormat(format: Int) { viewModelScope.launch { settingsRepository.updateGpsFormat(format) } }
    fun updateAddressResolution(resolution: LocationFormat) { viewModelScope.launch { settingsRepository.updateAddressResolution(resolution) } }
}
