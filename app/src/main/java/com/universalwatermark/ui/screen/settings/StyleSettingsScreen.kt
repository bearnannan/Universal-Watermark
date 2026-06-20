package com.universalwatermark.ui.screen.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.universalwatermark.ui.SelectionDialog
import com.universalwatermark.ui.ColorPickerItem
import com.universalwatermark.ui.SliderItem
import com.universalwatermark.ui.PositionSelectorItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyleSettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.cameraSettingsFlow.collectAsState()

    var showTemplateDialog by remember { mutableStateOf(false) }
    var showFontDialog by remember { mutableStateOf(false) }

    val logoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.setLogo(uri)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ดีไซน์ (Style & Theme)") },
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
                val templateName = when(settings.templateId) {
                    1 -> "ทันสมัย (Modern)"
                    2 -> "มินิมอล (Minimal)"
                    else -> "ดั้งเดิม (Classic)"
                }
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("เทมเพลตลายน้ำ", style = MaterialTheme.typography.titleSmall)
                        Text(templateName)
                        Button(onClick = { showTemplateDialog = true }, modifier = Modifier.padding(top = 8.dp)) {
                            Text("เปลี่ยนเทมเพลต")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("โลโก้ส่วนตัว", style = MaterialTheme.typography.titleSmall)
                        Text(if (settings.customLogoPath != null) "ตั้งค่าแล้ว" else "ยังไม่มีโลโก้")
                        Row(modifier = Modifier.padding(top = 8.dp)) {
                            Button(onClick = { logoPickerLauncher.launch("image/*") }) {
                                Text("เลือกโลโก้")
                            }
                            if (settings.customLogoPath != null) {
                                Spacer(modifier = Modifier.width(8.dp))
                                OutlinedButton(onClick = { viewModel.setLogo(null) }) {
                                    Text("ลบ")
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("การปรับแต่งข้อความ", style = MaterialTheme.typography.titleSmall)
                        
                        // Font Family
                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("ฟอนต์ (Font)")
                            Button(onClick = { showFontDialog = true }) { Text(settings.googleFontName) }
                        }
                        
                        // Text Style (Bold)
                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("ตัวหนา (Bold)")
                            Switch(checked = settings.textStyle == 1, onCheckedChange = { viewModel.updateTextStyle(if (it) 1 else 0) })
                        }
                        
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        
                        // Size Slider
                        SliderItem(title = "ขนาดข้อความ: ${settings.textSize.toInt()}", value = settings.textSize, valueRange = 20f..80f, onValueChange = { viewModel.updateTextSize(it) })
                        
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        
                        // Color Picker
                        ColorPickerItem(selectedColor = settings.textColor, onColorSelected = { viewModel.updateTextColor(it) })
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("ตำแหน่งลายน้ำ", style = MaterialTheme.typography.titleSmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        PositionSelectorItem(selectedPosition = settings.overlayPosition, onPositionSelected = { viewModel.updateOverlayPosition(it) })
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        // Dialogs
        if (showTemplateDialog) {
            SelectionDialog(
                title = "เลือกรูปแบบลายน้ำ",
                options = listOf("ดั้งเดิม (Classic)", "ทันสมัย (Modern Pro)", "มินิมอล (Minimal)"),
                selectedOption = when(settings.templateId) {
                    1 -> "ทันสมัย (Modern Pro)"
                    2 -> "มินิมอล (Minimal)"
                    else -> "ดั้งเดิม (Classic)"
                },
                onSelect = { option ->
                    val newId = when(option) {
                        "ทันสมัย (Modern Pro)" -> 1
                        "มินิมอล (Minimal)" -> 2
                        else -> 0
                    }
                    viewModel.updateTemplateId(newId)
                    showTemplateDialog = false
                },
                onDismiss = { showTemplateDialog = false }
            )
        }

        if (showFontDialog) {
            SelectionDialog(
                title = "เลือกฟอนต์",
                options = listOf("Roboto", "Oswald", "Roboto Mono", "Playfair Display", "Inter", "Cursive"),
                selectedOption = settings.googleFontName,
                onSelect = { 
                    viewModel.updateGoogleFontName(it)
                    showFontDialog = false 
                },
                onDismiss = { showFontDialog = false }
            )
        }
    }
}
