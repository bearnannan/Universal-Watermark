package com.universalwatermark.ui.screen.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.universalwatermark.data.local.db.entity.WatermarkHistoryEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatermarkHistoryScreen(
    onNavigateBack: () -> Unit,
    viewModel: WatermarkHistoryViewModel = hiltViewModel()
) {
    val historyList by viewModel.historyList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Watermark History") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (historyList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("No history found.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(historyList) { item ->
                    HistoryItemCard(item)
                }
            }
        }
    }
}

@Composable
fun HistoryItemCard(item: WatermarkHistoryEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Status: ${item.status}", style = MaterialTheme.typography.titleMedium)
            Text("Output: ${item.watermarkedUri}", style = MaterialTheme.typography.bodySmall)
            // Format timestamp in real app
            Text("Time: ${item.processedAt}", style = MaterialTheme.typography.bodySmall) 
        }
    }
}
