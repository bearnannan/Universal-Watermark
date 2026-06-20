package com.universalwatermark.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = context.settingsDataStore

    companion object {
        val IS_SERVICE_ENABLED = booleanPreferencesKey("is_service_enabled")
        val OUTPUT_QUALITY = intPreferencesKey("output_quality")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val LANGUAGE = stringPreferencesKey("language")

        // Watermark Toggles
        val SHOW_DATE = booleanPreferencesKey("show_date")
        val SHOW_TIME = booleanPreferencesKey("show_time")
        val SHOW_GPS = booleanPreferencesKey("show_gps")
        val SHOW_ADDRESS = booleanPreferencesKey("show_address")
        val SHOW_USER = booleanPreferencesKey("show_user")
        val SHOW_ORG = booleanPreferencesKey("show_org")
        val SHOW_DEVICE = booleanPreferencesKey("show_device")
        val SHOW_ASSET = booleanPreferencesKey("show_asset")
        val SHOW_CUSTOM = booleanPreferencesKey("show_custom")
        val SHOW_LOGO = booleanPreferencesKey("show_logo")
    }

    val isServiceEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_SERVICE_ENABLED] ?: false
    }

    suspend fun setServiceEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_SERVICE_ENABLED] = enabled
        }
    }

    val outputQuality: Flow<Int> = dataStore.data.map { preferences ->
        preferences[OUTPUT_QUALITY] ?: 95
    }

    suspend fun setOutputQuality(quality: Int) {
        dataStore.edit { preferences ->
            preferences[OUTPUT_QUALITY] = quality
        }
    }

    // Toggle Getters
    val showDate: Flow<Boolean> = dataStore.data.map { it[SHOW_DATE] ?: true }
    val showTime: Flow<Boolean> = dataStore.data.map { it[SHOW_TIME] ?: true }
    val showGps: Flow<Boolean> = dataStore.data.map { it[SHOW_GPS] ?: true }
    val showAddress: Flow<Boolean> = dataStore.data.map { it[SHOW_ADDRESS] ?: true }
    val showUser: Flow<Boolean> = dataStore.data.map { it[SHOW_USER] ?: false }
    val showOrg: Flow<Boolean> = dataStore.data.map { it[SHOW_ORG] ?: false }
    val showDevice: Flow<Boolean> = dataStore.data.map { it[SHOW_DEVICE] ?: false }
    val showAsset: Flow<Boolean> = dataStore.data.map { it[SHOW_ASSET] ?: false }
    val showCustom: Flow<Boolean> = dataStore.data.map { it[SHOW_CUSTOM] ?: false }
    val showLogo: Flow<Boolean> = dataStore.data.map { it[SHOW_LOGO] ?: false }

    // Toggle Setters
    suspend fun setToggle(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { prefs ->
            prefs[key] = value
        }
    }
}
