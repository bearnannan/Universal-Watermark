package com.universalwatermark.ui.screen.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.universalwatermark.data.local.datastore.SettingsDataStore
import com.universalwatermark.service.ServiceController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.universalwatermark.data.CameraSettings
import com.universalwatermark.data.SettingsRepository

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val app: Application,
    private val settingsDataStore: SettingsDataStore
) : AndroidViewModel(app) {

    val isServiceEnabled: StateFlow<Boolean> = settingsDataStore.isServiceEnabled
        .stateIn(viewModelScope, SharingStarted.Lazily, false)
        
    private val settingsRepository = SettingsRepository(app)

    val cameraSettings: StateFlow<CameraSettings> = settingsRepository.cameraSettingsFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, CameraSettings())

    fun toggleService(enabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.setServiceEnabled(enabled)
            settingsRepository.updateFloatingWidgetEnabled(enabled)
            if (enabled) {
                ServiceController.startService(app)
                if (android.provider.Settings.canDrawOverlays(app)) {
                    val serviceIntent = android.content.Intent(app, com.universalwatermark.service.FloatingWidgetService::class.java)
                    app.startForegroundService(serviceIntent)
                }
            } else {
                ServiceController.stopService(app)
                val serviceIntent = android.content.Intent(app, com.universalwatermark.service.FloatingWidgetService::class.java)
                app.stopService(serviceIntent)
            }
        }
    }
    
    fun updateOverlayPosition(position: String) {
        viewModelScope.launch { settingsRepository.updateOverlayPosition(position) }
    }
}
