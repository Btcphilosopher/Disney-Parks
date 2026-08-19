package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisneyTopBar(
    activeDestination: Destination,
    activePark: Park,
    unreadNotificationCount: Int,
    isOfflineMode: Boolean,
    isLiveSimulation: Boolean,
    onOpenDestinationPicker: () -> Unit,
    onOpenNotifications: () -> Unit,
    onToggleOffline: () -> Unit
) {
    Surface(
        color = Color.White,
        border = BorderStroke(1.dp, Slate100),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Destination & Park selector badge
                Surface(
                    onClick = onOpenDestinationPicker,
                    shape = RoundedCornerShape(18.dp),
                    color = CleanBlue50,
                    border = BorderStroke(1.dp, CleanBlue100),
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .testTag("destination_picker_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "🏰",
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = activeDestination.shortName.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                color = CleanBlue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = activePark.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Change Park",
                                    tint = Slate500,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Offline Mode Chip
                    if (isOfflineMode) {
                        Surface(
                            onClick = onToggleOffline,
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFFEE2E2),
                            border = BorderStroke(1.dp, Color(0xFFEF4444))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CloudOff,
                                    contentDescription = "Offline Mode",
                                    tint = Color(0xFFDC2626),
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Offline",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFFDC2626),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Notification Bell with Badge
                    IconButton(
                        onClick = onOpenNotifications,
                        modifier = Modifier.testTag("notifications_button")
                    ) {
                        BadgedBox(
                            badge = {
                                if (unreadNotificationCount > 0) {
                                    Badge(
                                        containerColor = CleanBlue,
                                        contentColor = Color.White
                                    ) {
                                        Text("$unreadNotificationCount")
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (unreadNotificationCount > 0) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                                contentDescription = "Notifications",
                                tint = Slate700
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WaitTimeBadge(
    waitTimeMinutes: Int,
    status: AttractionStatus,
    modifier: Modifier = Modifier
) {
    val (bg, fg, text) = when (status) {
        AttractionStatus.OPERATING -> {
            when {
                waitTimeMinutes <= 20 -> Triple(Emerald50, Emerald600, "$waitTimeMinutes min")
                waitTimeMinutes <= 45 -> Triple(Amber50, Amber600, "$waitTimeMinutes min")
                else -> Triple(Color(0xFFFFF1F2), Color(0xFFE11D48), "$waitTimeMinutes min")
            }
        }
        AttractionStatus.TEMPORARILY_CLOSED -> Triple(Slate100, Slate500, "Closed")
        AttractionStatus.DELAYED -> Triple(Amber50, Amber600, "Delayed")
        AttractionStatus.REFURBISHMENT -> Triple(Slate100, Slate500, "Refurb")
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = bg,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(fg)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = fg
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DestinationSelectorSheet(
    destinations: List<Destination>,
    activeDestination: Destination,
    activePark: Park,
    onSelectDestination: (String) -> Unit,
    onSelectPark: (String) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
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
                Column {
                    Text(
                        text = "Disney Destinations",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = "Switch resort destination or choose a park",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate500
                    )
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Slate500)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            destinations.forEach { dest ->
                val isSelectedDest = dest.id == activeDestination.id

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelectedDest) CleanBlue50 else Color.White,
                    border = BorderStroke(
                        width = if (isSelectedDest) 1.5.dp else 1.dp,
                        color = if (isSelectedDest) CleanBlue else Slate100
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { onSelectDestination(dest.id) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = dest.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                Text(
                                    text = "${dest.location} · ${dest.country}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Slate500
                                )
                            }
                            if (isSelectedDest) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Selected",
                                    tint = CleanBlue
                                )
                            }
                        }

                        if (isSelectedDest) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "PARKS IN THIS DESTINATION",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = CleanBlue,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                dest.parks.forEach { park ->
                                    val isSelectedPark = park.id == activePark.id
                                    FilterChip(
                                        selected = isSelectedPark,
                                        onClick = {
                                            onSelectPark(park.id)
                                            onDismiss()
                                        },
                                        label = {
                                            Text(
                                                text = park.name,
                                                fontWeight = if (isSelectedPark) FontWeight.Bold else FontWeight.Medium
                                            )
                                        },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = CleanBlue,
                                            selectedLabelColor = Color.White
                                        ),
                                        shape = RoundedCornerShape(12.dp)
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
