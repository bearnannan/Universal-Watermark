package com.universalwatermark.ui.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.universalwatermark.data.LocationFormat
import com.universalwatermark.ui.SelectionDialog
import com.universalwatermark.ui.SettingsToggleItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentSettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.cameraSettingsFlow.collectAsState()
    
    var showGpsFormatDialog by remember { mutableStateOf(false) }
    
    val gpsFormatOptions = listOf("DD (Decimal)", "DMS (Degrees/Minutes/Seconds)")
    val currentGpsFormatString = if (settings.gpsFormat == 0) "DD (Decimal)" else "DMS (Degrees/Minutes/Seconds)"

    if (showGpsFormatDialog) {
        SelectionDialog(
            title = "รูปแบบพิกัด GPS",
            options = gpsFormatOptions,
            selectedOption = currentGpsFormatString,
            onSelect = { option ->
                val newFormat = if (option.startsWith("DD")) 0 else 1
                viewModel.updateGpsFormat(newFormat)
                showGpsFormatDialog = false
            },
            onDismiss = { showGpsFormatDialog = false }
        )
    }
    
    var showAddressFormatDialog by remember { mutableStateOf(false) }

    val addressFormatOptions = listOf(
        "ที่อยู่แบบเต็ม (Full Address)",
        "ที่อยู่แบบย่อ (Short Address)",
        "เฉพาะเมือง/จังหวัด (City Only)",
        "เฉพาะพิกัด (Lat/Lon Only)",
        "ไม่แสดง (None)"
    )
    val currentAddressFormatString = when (settings.addressResolution) {
        LocationFormat.FULL_ADDRESS -> "ที่อยู่แบบเต็ม (Full Address)"
        LocationFormat.SHORT_ADDRESS -> "ที่อยู่แบบย่อ (Short Address)"
        LocationFormat.CITY_ONLY -> "เฉพาะเมือง/จังหวัด (City Only)"
        LocationFormat.LAT_LON_ONLY -> "เฉพาะพิกัด (Lat/Lon Only)"
        LocationFormat.NONE -> "ไม่แสดง (None)"
    }

    if (showAddressFormatDialog) {
        SelectionDialog(
            title = "รูปแบบที่อยู่ (Address Format)",
            options = addressFormatOptions,
            selectedOption = currentAddressFormatString,
            onSelect = { option ->
                val newFormat = when (option) {
                    "ที่อยู่แบบเต็ม (Full Address)" -> LocationFormat.FULL_ADDRESS
                    "ที่อยู่แบบย่อ (Short Address)" -> LocationFormat.SHORT_ADDRESS
                    "เฉพาะเมือง/จังหวัด (City Only)" -> LocationFormat.CITY_ONLY
                    "เฉพาะพิกัด (Lat/Lon Only)" -> LocationFormat.LAT_LON_ONLY
                    else -> LocationFormat.NONE
                }
                viewModel.updateAddressResolution(newFormat)
                showAddressFormatDialog = false
            },
            onDismiss = { showAddressFormatDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ข้อมูลที่แสดง (Display Content)") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            item {
                Text("เวลาและสถานที่", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(8.dp))
                
                SettingsToggleItem(
                    title = "แสดงวันที่ (Date)",
                    isEnabled = settings.dateWatermarkEnabled,
                    onToggle = { viewModel.updateDateWatermark(it) }
                )
                SettingsToggleItem(
                    title = "แสดงเวลา (Time)",
                    isEnabled = settings.timeWatermarkEnabled,
                    onToggle = { viewModel.updateTimeWatermark(it) }
                )
                SettingsToggleItem(
                    title = "แสดงที่อยู่ (Address)",
                    isEnabled = settings.isAddressEnabled,
                    onToggle = { viewModel.updateAddressEnabled(it) }
                )
                
                // Address Format Selector
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showAddressFormatDialog = true }
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("รูปแบบที่อยู่ (Address Format)", color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp)
                        Text(currentAddressFormatString, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                    }
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                
                SettingsToggleItem(
                    title = "แสดงพิกัด GPS",
                    isEnabled = settings.isCoordinatesEnabled,
                    onToggle = { viewModel.updateCoordinatesEnabled(it) }
                )
                
                // GPS Format Selector
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showGpsFormatDialog = true }
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("รูปแบบพิกัด GPS (Format)", color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp)
                        Text(currentGpsFormatString, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                    }
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                
                Divider(modifier = Modifier.padding(vertical = 16.dp))
            }

            item {
                Text("ข้อมูลเชิงลึก (Rich Data)", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(8.dp))

                SettingsToggleItem(
                    title = "เข็มทิศ (Compass)",
                    subtitle = "แสดงทิศทางการหันกล้อง",
                    isEnabled = settings.compassEnabled,
                    onToggle = { viewModel.updateCompassEnabled(it) }
                )
                SettingsToggleItem(
                    title = "ระดับความสูง (Altitude)",
                    isEnabled = settings.altitudeEnabled,
                    onToggle = { viewModel.updateAltitudeEnabled(it) }
                )
                SettingsToggleItem(
                    title = "ความเร็ว (Speed)",
                    isEnabled = settings.speedEnabled,
                    onToggle = { viewModel.updateSpeedEnabled(it) }
                )
            }
        }
    }
}
