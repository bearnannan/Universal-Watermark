package com.universalwatermark.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.universalwatermark.data.WatermarkItemType
import java.util.Collections

@Composable
fun ReorderTextDialog(
    currentOrder: List<WatermarkItemType>,
    onSave: (List<WatermarkItemType>) -> Unit,
    onDismiss: () -> Unit
) {
    var items by remember { mutableStateOf(currentOrder) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {

            Column(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFEEEEEE))
                        .padding(12.dp)
                ) {
                    Text(
                        text = "เคล็ดลับ: กดค้างเพื่อเปลี่ยนลำดับข้อความ",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Draggable List
                DraggableList(
                    items = items,
                    onMove = { from, to ->
                        val mutableList = items.toMutableList()
                        Collections.swap(mutableList, from, to)
                        items = mutableList
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = { 
                         items = listOf(
                            WatermarkItemType.DATE_TIME, WatermarkItemType.GPS, WatermarkItemType.COMPASS,
                            WatermarkItemType.ADDRESS, WatermarkItemType.ALTITUDE_SPEED,
                            WatermarkItemType.PROJECT_NAME, WatermarkItemType.INSPECTOR_NAME, WatermarkItemType.NOTE, WatermarkItemType.TAGS
                        ).filter { true } 
                    }) {
                        Text("คืนค่า")
                    }

                    Row {
                        TextButton(onClick = onDismiss) {
                            Text("ยกเลิก")
                        }
                        Button(onClick = { onSave(items); onDismiss() }) {
                            Text("ตกลง")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DraggableList(
    items: List<WatermarkItemType>,
    onMove: (Int, Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items.forEachIndexed { index, item ->
            DraggableItem(
                item = item,
                index = index,
                itemCount = items.size,
                onMove = onMove
            )
        }
    }
}

@Composable
fun DraggableItem(
    item: WatermarkItemType,
    index: Int,
    itemCount: Int,
    onMove: (Int, Int) -> Unit
) {
    var offsetY by remember { mutableStateOf(0f) }
    var deployment by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Drag",
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = item.displayName,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            
            // Fail-safe manual reorder buttons
            IconButton(
                onClick = { if(index > 0) onMove(index, index - 1) },
                enabled = index > 0
            ) {
               Text("▲", fontSize = 12.sp, color = Color.Black)
            }
            IconButton(
                onClick = { if(index < itemCount - 1) onMove(index, index + 1) },
                enabled = index < itemCount - 1
            ) {
               Text("▼", fontSize = 12.sp, color = Color.Black)
            }
        }
    }
}
