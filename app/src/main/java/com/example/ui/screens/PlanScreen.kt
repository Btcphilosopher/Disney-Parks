package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun PlanScreen(
    park: Park,
    itinerary: List<ItineraryItem>,
    recommendations: List<Recommendation>,
    isLiveMode: Boolean,
    onToggleLiveMode: () -> Unit,
    onToggleItemComplete: (String) -> Unit,
    onRemoveItem: (String) -> Unit,
    onStartRoute: (String, String) -> Unit,
    onAddCustomItem: (ItineraryItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceLight)
    ) {
        // Clean Minimal Header with Mode Switcher
        Surface(
            color = Color.White,
            border = BorderStroke(1.dp, Slate100),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "MY DAY ITINERARY",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Slate500,
                            letterSpacing = 1.2.sp
                        )
                        Text(
                            text = "${park.name} Plan",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )
                    }

                    // Mode Toggle (Clean Pill Selector)
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Slate100,
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Row(modifier = Modifier.padding(3.dp)) {
                            Surface(
                                onClick = { if (isLiveMode) onToggleLiveMode() },
                                shape = RoundedCornerShape(14.dp),
                                color = if (!isLiveMode) CleanBlue else Color.Transparent
                            ) {
                                Text(
                                    text = "Plan",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (!isLiveMode) Color.White else Slate600,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                                )
                            }
                            Surface(
                                onClick = { if (!isLiveMode) onToggleLiveMode() },
                                shape = RoundedCornerShape(14.dp),
                                color = if (isLiveMode) CleanBlue else Color.Transparent
                            ) {
                                Text(
                                    text = "Live",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isLiveMode) Color.White else Slate600,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        if (isLiveMode) {
            // "My Day" Live Command Center
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Live Status Banner
                item {
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = CleanBlue,
                        shadowElevation = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(Emerald500)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "LIVE COMMAND CENTER",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = CleanBlue100,
                                        letterSpacing = 1.2.sp
                                    )
                                }
                                Text("11:15 AM", style = MaterialTheme.typography.bodySmall, color = CleanBlue100)
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Current Position: Tomorrowland Hub",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Next Scheduled: Space Mountain Lightning Lane window opens at 11:30 AM (15 min left)",
                                style = MaterialTheme.typography.bodySmall,
                                color = CleanBlue100
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { onStartRoute("Tomorrowland Hub", "Space Mountain") },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = CleanBlue
                                ),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.fillMaxWidth().testTag("live_navigate_next_btn")
                            ) {
                                Icon(Icons.Default.Navigation, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Navigate to Next Destination (6 min walk)", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // Dynamic Smart Recommendations (Next 90 Minutes)
                item {
                    Text(
                        text = "SMART RECOMMENDATIONS (NEXT 90 MIN)",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Slate800,
                        letterSpacing = 1.sp
                    )
                }

                items(recommendations) { rec ->
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Slate100),
                        shadowElevation = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = CleanBlue50
                                ) {
                                    Text(
                                        text = rec.reason,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = CleanBlue,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = rec.title,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                Text(
                                    text = rec.subtitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Slate500
                                )
                            }

                            Button(
                                onClick = { onStartRoute("Current Location", rec.title) },
                                colors = ButtonDefaults.buttonColors(containerColor = CleanBlue, contentColor = Color.White),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text("Go", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        } else {
            // Full-Day Timeline Itinerary
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Add Item CTA
                item {
                    Button(
                        onClick = { showAddDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CleanBlue50,
                            contentColor = CleanBlue
                        ),
                        shape = RoundedCornerShape(18.dp),
                        modifier = Modifier.fillMaxWidth().testTag("add_custom_plan_item_btn")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Add Experience to Today's Plan", fontWeight = FontWeight.Bold)
                    }
                }

                items(itinerary) { item ->
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (item.isCompleted) Color.White.copy(alpha = 0.6f) else Color.White,
                        border = BorderStroke(1.dp, if (item.isCompleted) Slate200 else Slate100),
                        shadowElevation = if (item.isCompleted) 0.dp else 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = item.isCompleted,
                                onCheckedChange = { onToggleItemComplete(item.id) },
                                colors = CheckboxDefaults.colors(checkedColor = Emerald500)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.time,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = if (item.isCompleted) Slate400 else Slate500
                                    )

                                    if (item.walkTimeMinutesFromPrev != null) {
                                        Text(
                                            text = "🚶 ${item.walkTimeMinutesFromPrev}m walk",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Slate400
                                        )
                                    }
                                }

                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = if (item.isCompleted) Slate400 else Slate900,
                                    textDecoration = if (item.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                                )

                                Text(
                                    text = "${item.location} • ${item.subtitle}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (item.isCompleted) Slate400 else Slate500,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            IconButton(onClick = { onRemoveItem(item.id) }) {
                                Icon(
                                    imageVector = Icons.Default.DeleteOutline,
                                    contentDescription = "Remove",
                                    tint = Slate400
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Add Plan Item Dialog
    if (showAddDialog) {
        var newTitle by remember { mutableStateOf("") }
        var newTime by remember { mutableStateOf("02:00 PM") }
        var newLocation by remember { mutableStateOf("Fantasyland") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add to Today's Plan", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        label = { Text("Activity / Attraction Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newTime,
                        onValueChange = { newTime = it },
                        label = { Text("Scheduled Time (e.g. 02:30 PM)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newLocation,
                        onValueChange = { newLocation = it },
                        label = { Text("Location / Land") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTitle.isNotBlank()) {
                            onAddCustomItem(
                                ItineraryItem(
                                    id = "itin_custom_${System.currentTimeMillis()}",
                                    time = newTime,
                                    title = newTitle,
                                    subtitle = "Custom Guest Experience",
                                    category = ItineraryCategory.ATTRACTION,
                                    location = newLocation,
                                    durationMinutes = 30
                                )
                            )
                            showAddDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CleanBlue, contentColor = Color.White)
                ) {
                    Text("Add Activity", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel", color = Slate500)
                }
            }
        )
    }
}
