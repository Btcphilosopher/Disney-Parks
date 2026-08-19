package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.components.WaitTimeBadge
import com.example.ui.theme.*
import com.example.viewmodel.DisneyNavTab

@Composable
fun HomeScreen(
    destination: Destination,
    park: Park,
    attractions: List<Attraction>,
    itinerary: List<ItineraryItem>,
    recommendations: List<Recommendation>,
    hotelReservation: RoomReservation?,
    onNavigateTab: (DisneyNavTab) -> Unit,
    onSelectAttraction: (Attraction) -> Unit,
    onStartRoute: (String, String) -> Unit,
    onToggleItineraryItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val nextAttraction = attractions.find { it.name == "Space Mountain" } ?: attractions.firstOrNull()
    val upcomingPlanItems = itinerary.take(5)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceLight),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Clean Minimal Header with Profile Avatar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "GOOD MORNING",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Slate500,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = "Elias Miller",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Slate900,
                        letterSpacing = (-0.5).sp
                    )
                }

                // Avatar
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(CleanBlue)
                        .shadow(2.dp, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "EM",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        // Park Operational Status Card (Clean Minimal Badge)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 2.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Slate100),
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Emerald500)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "${park.name} • ${park.openHours}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = Slate700
                            )
                        }

                        Text(
                            text = "${destination.weather.temperatureC}°C · ${destination.weather.condition}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Slate500
                        )
                    }
                }
            }
        }

        // Hero "Your Next Adventure" Card (Signature Clean Blue rounded-[32px])
        if (nextAttraction != null) {
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Card(
                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(containerColor = CleanBlue),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(12.dp, RoundedCornerShape(32.dp), spotColor = CleanBlue.copy(alpha = 0.35f))
                            .clickable { onSelectAttraction(nextAttraction) }
                            .testTag("hero_next_attraction_card")
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        ) {
                            // Top Row: Label + Land Pill
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column {
                                    Text(
                                        text = "YOUR NEXT ADVENTURE",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = CleanBlue100,
                                        letterSpacing = 1.5.sp
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = nextAttraction.name,
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color.White.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = nextAttraction.landName,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Bottom Row: Stats + "GO NOW" Button
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "WAIT TIME",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = CleanBlue100,
                                            fontSize = 9.sp,
                                            letterSpacing = 1.sp
                                        )
                                        Text(
                                            text = "${nextAttraction.waitTimeMinutes} min",
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }

                                    Divider(
                                        modifier = Modifier
                                            .height(32.dp)
                                            .width(1.dp),
                                        color = Color.White.copy(alpha = 0.25f)
                                    )

                                    Column {
                                        Text(
                                            text = "WALK",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = CleanBlue100,
                                            fontSize = 9.sp,
                                            letterSpacing = 1.sp
                                        )
                                        Text(
                                            text = "6 min",
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                }

                                Button(
                                    onClick = { onStartRoute("Central Plaza Hub", nextAttraction.name) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White,
                                        contentColor = CleanBlue
                                    ),
                                    shape = RoundedCornerShape(18.dp),
                                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                                    modifier = Modifier
                                        .height(48.dp)
                                        .testTag("hero_go_button")
                                ) {
                                    Text(
                                        text = "GO NOW",
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.5.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Quick Category Grid: 3-column / horizontal Clean Minimal Tiles
        item {
            Spacer(modifier = Modifier.height(18.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    CleanMinimalCategoryTile(
                        iconEmoji = "⏲️",
                        title = "WAITS",
                        bgColor = Indigo50,
                        tintColor = Indigo600,
                        onClick = { onNavigateTab(DisneyNavTab.ATTRACTIONS) }
                    )
                }
                item {
                    CleanMinimalCategoryTile(
                        iconEmoji = "🍔",
                        title = "FOOD",
                        bgColor = Orange50,
                        tintColor = Orange600,
                        onClick = { onNavigateTab(DisneyNavTab.MORE) }
                    )
                }
                item {
                    CleanMinimalCategoryTile(
                        iconEmoji = "🏰",
                        title = "SHOWS",
                        bgColor = Pink50,
                        tintColor = Pink600,
                        onClick = { onNavigateTab(DisneyNavTab.MORE) }
                    )
                }
                item {
                    CleanMinimalCategoryTile(
                        iconEmoji = "🗺️",
                        title = "MAP",
                        bgColor = Emerald50,
                        tintColor = Emerald600,
                        onClick = { onNavigateTab(DisneyNavTab.MAP) }
                    )
                }
                item {
                    CleanMinimalCategoryTile(
                        iconEmoji = "🎟️",
                        title = "PASSES",
                        bgColor = CleanBlue50,
                        tintColor = CleanBlue,
                        onClick = { onNavigateTab(DisneyNavTab.MORE) }
                    )
                }
            }
        }

        // "Your Day" Section
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "YOUR DAY",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Slate800,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = "Edit Plan",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = CleanBlue,
                        modifier = Modifier.clickable { onNavigateTab(DisneyNavTab.PLAN) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                upcomingPlanItems.forEachIndexed { index, item ->
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (item.isCompleted) Color.White.copy(alpha = 0.6f) else Color.White,
                        border = BorderStroke(
                            1.dp,
                            if (item.isCompleted) Slate200 else Slate100
                        ),
                        shadowElevation = if (item.isCompleted) 0.dp else 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onToggleItineraryItem(item.id) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.time,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (item.isCompleted) Slate400 else Slate500,
                                modifier = Modifier.width(54.dp)
                            )

                            // Clean Vertical Line Pill Indicator
                            Box(
                                modifier = Modifier
                                    .width(4.dp)
                                    .height(32.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(if (item.isCompleted) Slate200 else CleanBlue)
                            )

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (item.isCompleted) Slate500 else Slate900,
                                    textDecoration = if (item.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "${item.location} · ${item.subtitle}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (item.isCompleted) Slate400 else Slate500,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            val emoji = when (item.category) {
                                ItineraryCategory.DINING -> "🍽️"
                                ItineraryCategory.ATTRACTION -> "⚡"
                                ItineraryCategory.SHOW -> "🎭"
                                ItineraryCategory.FIREWORKS -> "✨"
                                else -> "📍"
                            }
                            Text(
                                text = emoji,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        // Live Wait Times Spotlight
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "LIVE WAIT TIMES",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Slate800,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = "See All (${attractions.size})",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = CleanBlue,
                        modifier = Modifier.clickable { onNavigateTab(DisneyNavTab.ATTRACTIONS) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(attractions.take(6)) { attr ->
                        Surface(
                            shape = RoundedCornerShape(22.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Slate100),
                            shadowElevation = 1.dp,
                            modifier = Modifier
                                .width(200.dp)
                                .clickable { onSelectAttraction(attr) }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = attr.landName.uppercase(),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Slate400,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp
                                    )
                                    WaitTimeBadge(
                                        waitTimeMinutes = attr.waitTimeMinutes,
                                        status = attr.status
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = attr.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${attr.thrillLevel.label} · ${attr.durationMinutes} min",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Slate500
                                )
                            }
                        }
                    }
                }
            }
        }

        // Hotel & Resort Key Section
        if (hotelReservation != null) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Text(
                        text = "HOTEL RESERVATION",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Slate800,
                        letterSpacing = 1.2.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Slate100),
                        shadowElevation = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = hotelReservation.hotelName,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                Text(
                                    text = "Room ${hotelReservation.roomNumber} • ${hotelReservation.checkInDate} to ${hotelReservation.checkOutDate}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Slate500
                                )
                            }

                            Button(
                                onClick = { onNavigateTab(DisneyNavTab.MORE) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = CleanBlue,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Icon(Icons.Default.Key, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Key", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CleanMinimalCategoryTile(
    iconEmoji: String,
    title: String,
    bgColor: Color,
    tintColor: Color,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Slate100),
        shadowElevation = 1.dp,
        modifier = Modifier
            .width(96.dp)
            .height(100.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = iconEmoji,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = Slate500,
                fontSize = 10.sp,
                letterSpacing = 0.5.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
