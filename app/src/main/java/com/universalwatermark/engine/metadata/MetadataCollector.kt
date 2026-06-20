package com.universalwatermark.engine.metadata

import android.content.Context
import android.net.Uri

import com.universalwatermark.data.local.datastore.UserProfileDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MetadataCollector @Inject constructor(
    private val exifDataExtractor: ExifDataExtractor,
    private val deviceInfoCollector: DeviceInfoCollector,
    private val userProfileDataStore: UserProfileDataStore,
    @ApplicationContext private val context: Context
) {
    suspend fun collectMetadata(uri: Uri): Map<String, String> {
        val settingsRepository = com.universalwatermark.data.SettingsRepository(context)
        val settings = settingsRepository.cameraSettingsFlow.first()
        val metadata = mutableMapOf<String, String>()
        
        // EXIF Data
        val exifData = exifDataExtractor.extractExif(uri)
        
        // [DATE] and [TIME]
        val dateTimeOriginal = exifData["DATETIME_ORIGINAL"]
        if (dateTimeOriginal != null) {
            // EXIF format usually "yyyy:MM:dd HH:mm:ss"
            try {
                val parser = SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.US)
                val date = parser.parse(dateTimeOriginal) ?: Date()
                
                val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val timeFormatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                
                metadata["DATE"] = dateFormatter.format(date)
                metadata["TIME"] = timeFormatter.format(date)
            } catch (e: Exception) {
                // Fallback
                val current = Date()
                metadata["DATE"] = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(current)
                metadata["TIME"] = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(current)
            }
        } else {
            val current = Date()
            metadata["DATE"] = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(current)
            metadata["TIME"] = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(current)
        }
        
        // [GPS] and [ADDRESS]
        var latStr = exifData["GPS_LATITUDE"]
        var lonStr = exifData["GPS_LONGITUDE"]
        
        // Fallback to FusedLocationProviderClient if EXIF location is missing
        if (latStr == null || lonStr == null) {
            val hasFineLocation = androidx.core.content.ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED
            val hasCoarseLocation = androidx.core.content.ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED
            
            if (hasFineLocation || hasCoarseLocation) {
                try {
                    val fusedLocationClient = com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(context)
                    val locationTask = fusedLocationClient.getCurrentLocation(
                        com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY, 
                        null
                    )
                    var location = com.google.android.gms.tasks.Tasks.await(locationTask)
                    
                    if (location == null) {
                        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager
                        val isGpsEnabled = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
                        val isNetworkEnabled = locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)
                        
                        if (isNetworkEnabled) {
                            location = locationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER)
                        }
                        if (location == null && isGpsEnabled) {
                            location = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER)
                        }
                    }

                    if (location != null) {
                        latStr = location.latitude.toString()
                        lonStr = location.longitude.toString()
                        metadata["ALTITUDE"] = location.altitude.toString()
                        metadata["SPEED"] = location.speed.toString()
                    } else {
                        latStr = "13.7563"
                        lonStr = "Err: Location is NULL (No cached GPS)"
                    }
                } catch (e: Exception) {
                    latStr = "13.7563"
                    lonStr = "Err: ${e.javaClass.simpleName}"
                    e.printStackTrace()
                }
            } else {
                latStr = "13.7563"
                lonStr = "Err: Missing Location Permission"
            }
        } else {
            // EXIF GPS was used, try to extract Altitude and Speed from EXIF if possible
            val altStr = exifData["GPS_ALTITUDE"]
            if (altStr != null) {
                try {
                    val parts = altStr.split("/")
                    if (parts.size == 2) {
                        metadata["ALTITUDE"] = (parts[0].toDouble() / parts[1].toDouble()).toString()
                    }
                } catch(e:Exception) {}
            }
        }
        
        if (latStr != null && lonStr != null) {
            if (latStr.startsWith("Err:") || lonStr.startsWith("Err:")) {
                metadata["GPS"] = if (latStr.startsWith("Err:")) latStr else lonStr
                metadata["ADDRESS"] = ""
            } else {
                try {
                    val lat = latStr.toDouble()
                    val lon = lonStr.toDouble()
                    
                    metadata["GPS"] = formatGps(lat, lon, settings.gpsFormat)
                    
                    val geocoder = android.location.Geocoder(context, if (settings.isThaiLanguage) java.util.Locale("th", "TH") else java.util.Locale.getDefault())
                    val addresses = try {
                        @Suppress("DEPRECATION")
                        geocoder.getFromLocation(lat, lon, 1)
                    } catch (e: Exception) { null }

                    if (!addresses.isNullOrEmpty()) {
                        metadata["ADDRESS"] = formatAddress(addresses[0], settings.addressResolution)
                    } else {
                        metadata["ADDRESS"] = ""
                    }
                } catch (e: Exception) {
                    metadata["GPS"] = "PARSE_ERROR"
                    metadata["ADDRESS"] = ""
                }
            }
        } else {
            metadata["GPS"] = "NO_GPS_DATA"
            metadata["ADDRESS"] = "NO_ADDRESS_DATA"
        }
        
        // [COMPASS]
        metadata["AZIMUTH"] = ""
        
        // User Profile
        metadata["USER"] = userProfileDataStore.userName.first()
        metadata["ORG"] = userProfileDataStore.organization.first()
        metadata["ASSET"] = userProfileDataStore.assetNumber.first()
        metadata["CUSTOM"] = userProfileDataStore.customText.first()
        
        // Device Info
        val alias = userProfileDataStore.deviceAlias.first()
        metadata["DEVICE"] = alias.ifEmpty { deviceInfoCollector.getDeviceModel() }
        
        return metadata
    }

    private fun formatGps(lat: Double, lon: Double, format: Int): String {
        return when (format) {
            1 -> { // DMS
                val latDms = android.location.Location.convert(Math.abs(lat), android.location.Location.FORMAT_SECONDS)
                val lonDms = android.location.Location.convert(Math.abs(lon), android.location.Location.FORMAT_SECONDS)
                val latDir = if (lat >= 0) "N" else "S"
                val lonDir = if (lon >= 0) "E" else "W"
                "$latDms $latDir, $lonDms $lonDir"
            }
            else -> String.format(Locale.US, "%.6f, %.6f", lat, lon)
        }
    }

    private fun formatAddress(address: android.location.Address, resolution: com.universalwatermark.data.LocationFormat): String {
        return when (resolution) {
            com.universalwatermark.data.LocationFormat.FULL_ADDRESS -> {
                val sb = StringBuilder()
                for (i in 0..address.maxAddressLineIndex) {
                    sb.append(address.getAddressLine(i)).append(" ")
                }
                sb.toString().trim()
            }
            com.universalwatermark.data.LocationFormat.SHORT_ADDRESS -> {
                listOfNotNull(address.thoroughfare, address.subLocality, address.locality, address.adminArea)
                    .joinToString(", ")
            }
            com.universalwatermark.data.LocationFormat.CITY_ONLY -> {
                address.locality ?: address.subAdminArea ?: address.adminArea ?: ""
            }
            com.universalwatermark.data.LocationFormat.LAT_LON_ONLY -> ""
            com.universalwatermark.data.LocationFormat.NONE -> ""
        }
    }
}
