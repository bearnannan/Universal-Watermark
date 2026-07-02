package com.universalwatermark.ui.screen.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.*
import com.universalwatermark.ui.components.ProButton
import com.universalwatermark.ui.components.ProCard
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
            ProCard(
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



            // Live Preview Card
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("ภาพตัวอย่างลายน้ำ (Live Preview)", style = MaterialTheme.typography.titleMedium)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.TouchApp,
                        contentDescription = "Interactive Hint",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "แตะเพื่อเปลี่ยนตำแหน่ง",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            }
            LivePreviewCard(
                settings = cameraSettings,
                onPositionSelected = { pos -> viewModel.updateOverlayPosition(pos) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProButton(
                onClick = onNavigateToHistory,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View History")
            }
        }
    }
}
