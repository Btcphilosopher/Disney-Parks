package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsSheet(
    notifications: List<NotificationItem>,
    onMarkRead: (String) -> Unit,
    onDismissItem: (String) -> Unit,
    onDismissSheet: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissSheet,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Guest Notifications",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
                IconButton(onClick = onDismissSheet) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Slate500)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (notifications.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "You're all caught up! ✨",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Slate500
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(notifications) { notif ->
                        val priorityColor = when (notif.priority) {
                            NotificationPriority.CRITICAL -> Color(0xFFEF4444)
                            NotificationPriority.IMPORTANT -> Amber500
                            NotificationPriority.USEFUL -> CleanBlue
                            NotificationPriority.OPTIONAL -> Slate400
                        }

                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (notif.read) Slate50 else Color.White,
                            border = BorderStroke(1.dp, if (notif.read) Slate100 else priorityColor.copy(alpha = 0.5f)),
                            shadowElevation = if (notif.read) 0.dp else 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onMarkRead(notif.id) }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = notif.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = if (notif.read) FontWeight.SemiBold else FontWeight.Bold,
                                            color = Slate900
                                        )
                                        if (!notif.read) {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Badge(containerColor = CleanBlue)
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = notif.message,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Slate600
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = notif.timestamp,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Slate400
                                    )
                                }

                                IconButton(onClick = { onDismissItem(notif.id) }) {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = "Dismiss",
                                        tint = Slate400,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
