package com.universalwatermark.ui.screen.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.universalwatermark.ui.permission.PermissionHandler
import com.universalwatermark.ui.components.LivePreviewCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToHistory: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val isServiceEnabled by viewModel.isServiceEnabled.collectAsState()
    val cameraSettings by viewModel.cameraSettings.collectAsState()
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (allGranted) {
            viewModel.toggleService(true)
        } else {
            // TODO: Show a message that permissions are required
            viewModel.toggleService(false)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Universal Watermark") },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (isServiceEnabled) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Watermark Service", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = if (isServiceEnabled) "Running" else "Stopped",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isServiceEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = isServiceEnabled,
                        onCheckedChange = { isChecked -> 
                            if (isChecked) {
                                if (PermissionHandler.hasAllPermissions(context)) {
                                    viewModel.toggleService(true)
                                } else {
                                    val requiredPermissions = PermissionHandler.getRequiredPermissions().toTypedArray()
                                    permissionLauncher.launch(requiredPermissions)
                                }
                            } else {
                                viewModel.toggleService(false)
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Profile Selector
            var showProfileMenu by remember { mutableStateOf(false) }
            var showSaveDialog by remember { mutableStateOf(false) }
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("โปรไฟล์งาน (Workflow Profiles)", style = MaterialTheme.typography.titleMedium)
                TextButton(onClick = { showSaveDialog = true }) {
                    Text("บันทึก")
                }
            }
            
            Box(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                OutlinedButton(onClick = { showProfileMenu = true }, modifier = Modifier.fillMaxWidth()) {
                    val activeProfile = cameraSettings.workflowProfiles.find { it.id == cameraSettings.activeProfileId }
                    Text(activeProfile?.name ?: "เลือกโปรไฟล์ (Select Profile)")
                }
                DropdownMenu(
                    expanded = showProfileMenu,
                    onDismissRequest = { showProfileMenu = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    cameraSettings.workflowProfiles.forEach { profile ->
                        DropdownMenuItem(
                            text = { Text(profile.name) },
                            onClick = {
                                viewModel.applyProfile(profile.id)
                                showProfileMenu = false
                            },
                            trailingIcon = {
                                IconButton(onClick = { viewModel.deleteProfile(profile.id) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                                }
                            }
                        )
                    }
                }
            }
            
            if (showSaveDialog) {
                var profileName by remember { mutableStateOf("") }
                AlertDialog(
                    onDismissRequest = { showSaveDialog = false },
                    title = { Text("บันทึกโปรไฟล์งาน") },
                    text = {
                        OutlinedTextField(
                            value = profileName,
                            onValueChange = { profileName = it },
                            label = { Text("ชื่อโปรไฟล์") }
                        )
                    },
                    confirmButton = {
                        Button(onClick = {
                            if (profileName.isNotEmpty()) {
                                viewModel.saveProfile(com.universalwatermark.data.WorkflowProfile(
                                    name = profileName,
                                    projectName = cameraSettings.projectName,
                                    inspectorName = cameraSettings.inspectorName,
                                    customNote = cameraSettings.customNote,
                                    tags = cameraSettings.tags,
                                    isProjectEnabled = cameraSettings.isProjectEnabled,
                                    isInspectorEnabled = cameraSettings.isInspectorEnabled,
                                    isNoteEnabled = cameraSettings.isNoteEnabled,
                                    isTagsEnabled = cameraSettings.isTagsEnabled
                                ))
                            }
                            showSaveDialog = false
                        }) { Text("บันทึก") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showSaveDialog = false }) { Text("ยกเลิก") }
                    }
                )
            }
            
            // Summary Card
            Card(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("สถานะปัจจุบัน", style = MaterialTheme.typography.titleSmall)
                    Text("โครงการ: ${if (cameraSettings.projectName.isNotEmpty()) cameraSettings.projectName else "-"}", style = MaterialTheme.typography.bodySmall)
                    Text("เทมเพลต: รูปแบบที่ ${cameraSettings.templateId}", style = MaterialTheme.typography.bodySmall)
                    Text("บันทึกที่: ${if (cameraSettings.cloudPath.isNotEmpty()) cameraSettings.cloudPath else "โฟลเดอร์เริ่มต้น"}", style = MaterialTheme.typography.bodySmall)
                }
            }

            // Live Preview Card
            Text("ภาพตัวอย่างลายน้ำ (Live Preview)", style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp))
            LivePreviewCard(settings = cameraSettings)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToHistory,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View History")
            }
        }
    }
}
