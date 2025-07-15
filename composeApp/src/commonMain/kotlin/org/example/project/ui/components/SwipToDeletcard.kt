package org.example.project.ui.components
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
@Composable
fun SwipeToDeleteCard(
    onDeleteConfirmed: () -> Unit,
    content: @Composable () -> Unit
) {
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if (offsetX.value < -150f) {
                            showDialog = true
                        } else {
                            scope.launch { offsetX.animateTo(0f) }
                        }
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        scope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount)
                        }
                    }
                )
            }
            .graphicsLayer {
                translationX = offsetX.value
            }
    ) {
        content()
    }

 if (showDialog) {
        AlertDialog(
            onDismissRequest = {
              showDialog = false
                scope.launch { offsetX.animateTo(0f) }
            },

            title = { Text("Confirm Delete") },
            text = { Text("Are you sure you want to delete this item?") },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            showDialog = false
                            onDeleteConfirmed()
                            scope.launch { offsetX.snapTo(0f) }

                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Delete")
                    }

                    Button(
                        onClick = {
                            showDialog = false
                            scope.launch { offsetX.animateTo(0f) }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancle")
                    }

                }

            }
        )
    }


}
