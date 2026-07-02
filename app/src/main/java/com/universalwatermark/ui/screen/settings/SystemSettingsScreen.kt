package com.universalwatermark.ui.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.universalwatermark.data.ImageFormat
import com.universalwatermark.ui.SettingsToggleItem
import com.universalwatermark.ui.SelectionDialog
import com.universalwatermark.ui.TextInputDialog
import android.os.Build
import android.os.Environment
import android.content.Intent
import android.provider.Settings
import android.net.Uri
import androidx.compose.ui.platform.LocalContext

import com.universalwatermark.ui.components.ProCard
import com.universalwatermark.ui.components.ProButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemSettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.cameraSettingsFlow.collectAsState()
    val context = LocalContext.current
    
    var showQualityDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ระบบ (System & Output)") },
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
                Text("คุณภาพรูปภาพ", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(8.dp))

                ProCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("การบีบอัด (Compression Quality)", style = MaterialTheme.typography.titleSmall)
                        Text("${settings.compressionQuality}%")
                        ProButton(onClick = { showQualityDialog = true }, modifier = Modifier.padding(top = 8.dp)) {
                            Text("เปลี่ยนระดับคุณภาพ")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text("การจัดการไฟล์ (File Management)", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(8.dp))
                SettingsToggleItem(
                    title = "ลบรูปต้นฉบับหลังจากใส่ลายน้ำ",
                    subtitle = "Delete Original Photo",
                    isEnabled = settings.deleteOriginalPhoto,
                    onToggle = { isChecked -> 
                        if (isChecked && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
                            try {
                                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                                intent.data = Uri.parse("package:${context.packageName}")
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                                context.startActivity(intent)
                            }
                        } else {
                            viewModel.updateDeleteOriginalPhoto(isChecked) 
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

        }
        
        // Dialogs
        if (showQualityDialog) {
            SelectionDialog(
                title = "เลือกระดับคุณภาพ",
                options = listOf("100% (ดีที่สุด)", "90% (ดีมาก)", "80% (ปานกลาง)", "60% (ประหยัดพื้นที่)"),
                selectedOption = when(settings.compressionQuality) {
                    100 -> "100% (ดีที่สุด)"
                    80 -> "80% (ปานกลาง)"
                    60 -> "60% (ประหยัดพื้นที่)"
                    else -> "90% (ดีมาก)"
                },
                onSelect = { option ->
                    val value = when(option) {
                        "100% (ดีที่สุด)" -> 100
                        "80% (ปานกลาง)" -> 80
                        "60% (ประหยัดพื้นที่)" -> 60
                        else -> 90
                    }
                    viewModel.updateCompressionQuality(value)
                    showQualityDialog = false
                },
                onDismiss = { showQualityDialog = false }
            )
        }
    }
}
