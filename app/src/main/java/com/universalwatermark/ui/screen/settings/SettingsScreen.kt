package com.universalwatermark.ui.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToWorkflow: () -> Unit,
    onNavigateToStyle: () -> Unit,
    onNavigateToContent: () -> Unit,
    onNavigateToSystem: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ตั้งค่า (Settings)") },
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
                SettingsCategoryItem(
                    title = "💼 ข้อมูลงาน (Workflow Info)",
                    subtitle = "Project, Inspector, Notes, Tags",
                    onClick = onNavigateToWorkflow
                )
                Divider()
            }
            item {
                SettingsCategoryItem(
                    title = "🎨 ดีไซน์ (Style & Theme)",
                    subtitle = "Template, Font, Color, Size, Logo",
                    onClick = onNavigateToStyle
                )
                Divider()
            }
            item {
                SettingsCategoryItem(
                    title = "📍 ข้อมูลที่แสดง (Display Content)",
                    subtitle = "Date/Time, Location, GPS, Compass, Altitude",
                    onClick = onNavigateToContent
                )
                Divider()
            }
            item {
                SettingsCategoryItem(
                    title = "⚙️ ระบบ (System & Output)",
                    subtitle = "Resolution, Quality, Cloud Path",
                    onClick = onNavigateToSystem
                )
            }
        }
    }
}

@Composable
fun SettingsCategoryItem(title: String, subtitle: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
