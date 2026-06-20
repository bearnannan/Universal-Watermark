package com.universalwatermark.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.universalwatermark.data.local.datastore.SettingsDataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var settingsDataStore: SettingsDataStore

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Using GlobalScope since Receiver's lifecycle is short, 
            // but we need to read from DataStore and start the service
            GlobalScope.launch {
                val isEnabled = settingsDataStore.isServiceEnabled.first()
                if (isEnabled) {
                    ServiceController.startService(context)
                }
            }
        }
    }
}
