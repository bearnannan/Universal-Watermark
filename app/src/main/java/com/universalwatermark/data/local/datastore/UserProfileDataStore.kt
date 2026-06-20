package com.universalwatermark.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.userProfileDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_profile")

@Singleton
class UserProfileDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = context.userProfileDataStore

    companion object {
        val USER_NAME = stringPreferencesKey("user_name")
        val ORGANIZATION = stringPreferencesKey("organization")
        val ASSET_NUMBER = stringPreferencesKey("asset_number")
        val CUSTOM_TEXT = stringPreferencesKey("custom_text")
        val DEVICE_ALIAS = stringPreferencesKey("device_alias")
        val LOGO_URI = stringPreferencesKey("logo_uri")
    }

    val userName: Flow<String> = dataStore.data.map { it[USER_NAME] ?: "" }
    val organization: Flow<String> = dataStore.data.map { it[ORGANIZATION] ?: "" }
    val assetNumber: Flow<String> = dataStore.data.map { it[ASSET_NUMBER] ?: "" }
    val customText: Flow<String> = dataStore.data.map { it[CUSTOM_TEXT] ?: "" }
    val deviceAlias: Flow<String> = dataStore.data.map { it[DEVICE_ALIAS] ?: "" }
    val logoUri: Flow<String> = dataStore.data.map { it[LOGO_URI] ?: "" }

    suspend fun updateProfile(
        name: String,
        org: String,
        asset: String,
        custom: String,
        alias: String
    ) {
        dataStore.edit { prefs ->
            prefs[USER_NAME] = name
            prefs[ORGANIZATION] = org
            prefs[ASSET_NUMBER] = asset
            prefs[CUSTOM_TEXT] = custom
            prefs[DEVICE_ALIAS] = alias
        }
    }

    suspend fun setLogoUri(uri: String) {
        dataStore.edit { prefs ->
            prefs[LOGO_URI] = uri
        }
    }
}
