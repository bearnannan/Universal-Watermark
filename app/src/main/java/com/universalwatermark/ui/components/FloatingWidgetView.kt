package com.universalwatermark.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import com.universalwatermark.ui.theme.UniversalWatermarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.universalwatermark.data.SettingsRepository
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatingWidgetView(
    onClose: () -> Unit,
    onFocusRequest: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val settingsRepository = remember { SettingsRepository(context) }
    val cameraSettings by settingsRepository.cameraSettingsFlow.collectAsState(initial = null)
    val savedNotes by settingsRepository.savedNotesFlow.collectAsState(initial = emptySet())
    
    var isExpanded by remember { mutableStateOf(false) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    
    val scope = rememberCoroutineScope()
    
    // Notify window manager when expanded state changes so we can receive keyboard inputs
    LaunchedEffect(isExpanded) {
        onFocusRequest(isExpanded)
    }

    if (cameraSettings == null) return

    UniversalWatermarkTheme {
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
        ) {
        if (!isExpanded) {
            // Collapsed State: AssistiveTouch Bubble
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            offsetX += dragAmount.x
                            offsetY += dragAmount.y
                        }
                    }
            ) {
                FloatingActionButton(
                    onClick = { isExpanded = true },
                    shape = CircleShape,
                    containerColor = Color(0xFF121212),
                    contentColor = Color(0xFFFF6D00),
                    modifier = Modifier
                        .size(56.dp)
                        .align(Alignment.Center)
                ) {
                    Text(
                        "UW", 
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black)
                    )
                }
            }
        } else {
            // Expanded State: Quick Settings Dialog
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .width(300.dp)
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("ตั้งค่าลายน้ำด่วน", style = MaterialTheme.typography.titleMedium)
                        IconButton(onClick = { isExpanded = false }) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    var noteText by remember(cameraSettings?.customNote) { mutableStateOf(cameraSettings?.customNote ?: "") }
                    var tagsText by remember(cameraSettings?.tags) { mutableStateOf(cameraSettings?.tags ?: "") }

                    OutlinedTextField(
                        value = noteText,
                        onValueChange = { noteText = it },
                        label = { Text("ข้อความ / Note") },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            if (noteText.isNotBlank()) {
                                IconButton(onClick = { 
                                    scope.launch { settingsRepository.addSavedNote(noteText) }
                                }) {
                                    Icon(Icons.Default.Save, contentDescription = "Save Note")
                                }
                            }
                        }
                    )
                    
                    if (savedNotes.isNotEmpty()) {
                        androidx.compose.foundation.lazy.LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        ) {
                            items(
                                count = savedNotes.size,
                                itemContent = { index ->
                                    val note = savedNotes.elementAt(index)
                                    SuggestionChip(
                                        onClick = { noteText = note },
                                        label = { Text(note) }
                                    )
                                }
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    OutlinedTextField(
                        value = tagsText,
                        onValueChange = { tagsText = it },
                        label = { Text("แท็ก (Tags)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilledTonalButton(
                            onClick = onClose,
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                contentColor = MaterialTheme.colorScheme.onErrorContainer
                            ),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Icon(Icons.Default.PowerSettingsNew, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("ปิดทำงาน")
                        }
                        
                        Row {
                            TextButton(onClick = { isExpanded = false }) {
                                Text("ยกเลิก")
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Button(
                                onClick = {
                                    scope.launch {
                                        settingsRepository.updateCustomNote(noteText)
                                        settingsRepository.updateTags(tagsText)
                                        isExpanded = false
                                    }
                                },
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text("บันทึก")
                            }
                        }
                    }
                    }
                }
            }
        }
    }
}
