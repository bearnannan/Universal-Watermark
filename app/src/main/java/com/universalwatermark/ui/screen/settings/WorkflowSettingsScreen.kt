package com.universalwatermark.ui.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import com.universalwatermark.ui.components.ProButton
import com.universalwatermark.ui.components.ProCard
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.universalwatermark.ui.ReorderTextDialog
import com.universalwatermark.ui.TextInputDialog
import com.universalwatermark.ui.TagManagementDialog
import com.universalwatermark.ui.NoteManagementDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkflowSettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.cameraSettingsFlow.collectAsState()
    val context = LocalContext.current
    
    val overlayPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (Settings.canDrawOverlays(context)) {
            viewModel.updateFloatingWidgetEnabled(true)
            val serviceIntent = Intent(context, com.universalwatermark.service.FloatingWidgetService::class.java)
            context.startForegroundService(serviceIntent)
        } else {
            viewModel.updateFloatingWidgetEnabled(false)
        }
    }
    val noteHistory by viewModel.noteHistory.collectAsState()
    val savedNotes by viewModel.savedNotes.collectAsState()
    val tagsHistory by viewModel.tagsHistory.collectAsState()

    var showProjectDialog by remember { mutableStateOf(false) }
    var showInspectorDialog by remember { mutableStateOf(false) }
    var showNoteDialog by remember { mutableStateOf(false) }
    var showTagsDialog by remember { mutableStateOf(false) }
    var showReorderDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ข้อมูลงาน (Workflow)") },
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
                ProCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("ชื่อโครงการ (Project)", style = MaterialTheme.typography.titleSmall)
                            Switch(checked = settings.isProjectEnabled, onCheckedChange = { viewModel.updateProjectEnabled(it) })
                        }
                        Text(if (settings.projectName.isNotEmpty()) settings.projectName else "ยังไม่ได้ระบุ")
                        ProButton(onClick = { showProjectDialog = true }, modifier = Modifier.padding(top = 8.dp), enabled = settings.isProjectEnabled) {
                            Text("แก้ไข")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                ProCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("ผู้ตรวจงาน (Inspector)", style = MaterialTheme.typography.titleSmall)
                            Switch(checked = settings.isInspectorEnabled, onCheckedChange = { viewModel.updateInspectorEnabled(it) })
                        }
                        Text(if (settings.inspectorName.isNotEmpty()) settings.inspectorName else "ยังไม่ได้ระบุ")
                        ProButton(onClick = { showInspectorDialog = true }, modifier = Modifier.padding(top = 8.dp), enabled = settings.isInspectorEnabled) {
                            Text("แก้ไข")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                ProCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("ข้อความ/หมายเหตุ (Note)", style = MaterialTheme.typography.titleSmall)
                            Switch(checked = settings.isNoteEnabled, onCheckedChange = { viewModel.updateNoteEnabled(it) })
                        }
                        Text(if (settings.customNote.isNotEmpty()) settings.customNote else "ยังไม่ได้ระบุ")
                        ProButton(onClick = { showNoteDialog = true }, modifier = Modifier.padding(top = 8.dp), enabled = settings.isNoteEnabled) {
                            Text("จัดการ Notes")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                ProCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("แท็ก (Tags)", style = MaterialTheme.typography.titleSmall)
                            Switch(checked = settings.isTagsEnabled, onCheckedChange = { viewModel.updateTagsEnabled(it) })
                        }
                        Text(if (settings.tags.isNotEmpty()) settings.tags else "ไม่มีแท็ก")
                        ProButton(onClick = { showTagsDialog = true }, modifier = Modifier.padding(top = 8.dp), enabled = settings.isTagsEnabled) {
                            Text("จัดการ Tags")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                ProCard(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column {
                                Text("วิดเจ็ตตั้งค่าด่วน (AssistiveTouch)", style = MaterialTheme.typography.titleSmall)
                                Text("แสดงปุ่มลอยเพื่อแก้ไข Note ด่วนขณะใช้งานกล้อง", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Switch(
                                checked = settings.isFloatingWidgetEnabled,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        if (!Settings.canDrawOverlays(context)) {
                                            Toast.makeText(context, "กรุณาเปิดสิทธิ์ 'แสดงทับแอปอื่น' เพื่อใช้งานวิดเจ็ตลอย", Toast.LENGTH_LONG).show()
                                            try {
                                                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${context.packageName}"))
                                                overlayPermissionLauncher.launch(intent)
                                            } catch (e: Exception) {
                                                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                                                overlayPermissionLauncher.launch(intent)
                                            }
                                        } else {
                                            viewModel.updateFloatingWidgetEnabled(true)
                                            val serviceIntent = Intent(context, com.universalwatermark.service.FloatingWidgetService::class.java)
                                            context.startForegroundService(serviceIntent)
                                        }
                                    } else {
                                        viewModel.updateFloatingWidgetEnabled(false)
                                        val serviceIntent = Intent(context, com.universalwatermark.service.FloatingWidgetService::class.java)
                                        context.stopService(serviceIntent)
                                    }
                                }
                            )
                        }
                    }
                }
            }
            item {
                ProButton(onClick = { showReorderDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Text("จัดลำดับข้อความในลายน้ำ (Reorder Text)")
                }
            }
        }

        // Dialogs
        if (showProjectDialog) {
            TextInputDialog(
                title = "ชื่อโครงการ",
                initialValue = settings.projectName,
                onConfirm = { viewModel.updateProjectName(it); showProjectDialog = false },
                onDismiss = { showProjectDialog = false }
            )
        }
        if (showInspectorDialog) {
            TextInputDialog(
                title = "ผู้ตรวจงาน",
                initialValue = settings.inspectorName,
                onConfirm = { viewModel.updateInspectorName(it); showInspectorDialog = false },
                onDismiss = { showInspectorDialog = false }
            )
        }
        if (showNoteDialog) {
            NoteManagementDialog(
                currentNote = settings.customNote,
                savedNotes = savedNotes,
                noteHistory = noteHistory.toList(),
                onDismiss = { showNoteDialog = false },
                onConfirm = { viewModel.updateCustomNote(it); showNoteDialog = false },
                onAddNote = { viewModel.addSavedNote(it) },
                onRemoveNote = { viewModel.removeSavedNote(it) },
                onClearNotes = { viewModel.clearSavedNotes() },
                onImportNotes = { viewModel.importSavedNotes(it) }
            )
        }
        if (showTagsDialog) {
            TagManagementDialog(
                initialTags = settings.tags,
                availableTags = settings.availableTags,
                onDismiss = { showTagsDialog = false },
                onConfirm = { viewModel.updateTags(it); showTagsDialog = false },
                onAddTag = { viewModel.addAvailableTag(it) },
                onRemoveTag = { viewModel.removeAvailableTag(it) },
                onClearTags = { viewModel.clearAvailableTags() },
                onImportTags = { viewModel.importTags(it) }
            )
        }
        if (showReorderDialog) {
            ReorderTextDialog(
                currentOrder = settings.customTextOrder,
                onSave = { viewModel.updateTextOrder(it); showReorderDialog = false },
                onDismiss = { showReorderDialog = false }
            )
        }
    }
}
