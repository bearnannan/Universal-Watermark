package com.universalwatermark.engine.metadata

import android.os.Build
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceInfoCollector @Inject constructor() {
    
    fun getDeviceModel(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer, ignoreCase = true)) {
            model.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        } else {
            "${manufacturer.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }} $model"
        }
    }
}
