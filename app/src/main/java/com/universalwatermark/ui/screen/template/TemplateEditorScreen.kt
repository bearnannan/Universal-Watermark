package com.universalwatermark.ui.screen.template

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateEditorScreen(
    onNavigateBack: () -> Unit,
    viewModel: TemplateEditorViewModel = hiltViewModel()
) {
    val templateText by viewModel.templateText.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Template Editor") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = { viewModel.saveTemplate() }) {
                        Text("Save")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = templateText,
                onValueChange = { viewModel.updateTemplateText(it) },
                label = { Text("Watermark Template") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                placeholder = {
                    Text("[DATE] [TIME]\n[GPS]\n[USER]")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Placeholders", style = MaterialTheme.typography.titleMedium)
            // A simple grid or row of placeholder chips to insert into the text
            val placeholders = listOf("[DATE]", "[TIME]", "[GPS]", "[ADDRESS]", "[USER]", "[DEVICE]")
            
            // Just displaying as simple row for mockup
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                placeholders.take(3).forEach { p ->
                    AssistChip(onClick = { viewModel.appendPlaceholder(p) }, label = { Text(p) })
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                placeholders.drop(3).forEach { p ->
                    AssistChip(onClick = { viewModel.appendPlaceholder(p) }, label = { Text(p) })
                }
            }
        }
    }
}
