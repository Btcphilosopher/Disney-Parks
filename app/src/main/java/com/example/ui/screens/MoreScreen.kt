package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*

enum class MoreSubSection(val label: String, val icon: ImageVector) {
    TICKETS("Tickets & MagicBand", Icons.Default.ConfirmationNumber),
    DINING("Dining & Food", Icons.Default.Restaurant),
    ENTERTAINMENT("Entertainment", Icons.Default.TheaterComedy),
    HOTEL("Resort Hotel", Icons.Default.Hotel),
    TRANSIT("Transit & Routes", Icons.Default.DirectionsTransit),
    FAMILY("Family & Party", Icons.Default.FamilyRestroom),
    SETTINGS("System & Offline", Icons.Default.Settings)
}

@Composable
fun MoreScreen(
    destination: Destination,
    park: Park,
    tickets: List<Ticket>,
    restaurants: List<Restaurant>,
    entertainment: List<EntertainmentEvent>,
    hotelReservation: RoomReservation?,
    familyMembers: List<FamilyMember>,
    isLiveSimulation: Boolean,
    isOfflineMode: Boolean,
    onSelectRestaurant: (Restaurant) -> Unit,
    onAddEventToPlan: (EntertainmentEvent, String) -> Unit,
    onUpdateMagicBandColor: (String, Long) -> Unit,
    onUnlockHotelKey: () -> Unit,
    onToggleLiveSimulation: () -> Unit,
    onToggleOfflineMode: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSection by remember { mutableStateOf(MoreSubSection.TICKETS) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceLight)
    ) {
        // Clean Minimal Header
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
                Text(
                    text = "RESORT SERVICES",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Slate500,
                    letterSpacing = 1.2.sp
                )
                Text(
                    text = "${destination.name} Hub",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Navigation Pill Tabs
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(MoreSubSection.values()) { section ->
                        val isSelected = section == selectedSection
                        Surface(
                            onClick = { selectedSection = section },
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) CleanBlue else Color.White,
                            border = BorderStroke(1.dp, if (isSelected) CleanBlue else Slate200),
                            modifier = Modifier.height(36.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            ) {
                                Icon(
                                    imageVector = section.icon,
                                    contentDescription = null,
                                    tint = if (isSelected) Color.White else Slate600,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = section.label,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else Slate700
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section Body
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            when (selectedSection) {
                MoreSubSection.TICKETS -> {
                    items(tickets) { ticket ->
                        TicketWalletCard(
                            ticket = ticket,
                            onColorChanged = { newColor ->
                                onUpdateMagicBandColor(ticket.id, newColor)
                            }
                        )
                    }
                }

                MoreSubSection.DINING -> {
                    items(restaurants) { rest ->
                        Surface(
                            shape = RoundedCornerShape(22.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Slate100),
                            shadowElevation = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectRestaurant(rest) }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "${rest.landName} • ${rest.priceTier}".uppercase(),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Slate400,
                                            fontSize = 9.sp
                                        )
                                        Text(
                                            text = rest.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Slate900
                                        )
                                        Text(
                                            text = "${rest.cuisine} • ${rest.serviceType.label}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Slate500
                                        )
                                    }

                                    Button(
                                        onClick = { onSelectRestaurant(rest) },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (rest.allowsMobileOrder) Orange600 else CleanBlue,
                                            contentColor = Color.White
                                        ),
                                        shape = RoundedCornerShape(14.dp)
                                    ) {
                                        Text(
                                            text = if (rest.allowsMobileOrder) "Order" else "Table",
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                MoreSubSection.ENTERTAINMENT -> {
                    items(entertainment) { event ->
                        Surface(
                            shape = RoundedCornerShape(22.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Slate100),
                            shadowElevation = 1.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = event.category.label.uppercase(),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Pink600,
                                            fontSize = 9.sp
                                        )
                                        Text(
                                            text = event.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Slate900
                                        )
                                        Text(
                                            text = "${event.locationDescription} • ${event.durationMinutes} min",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Slate500
                                        )
                                    }

                                    if (event.isNighttimeSpectacular) {
                                        Surface(
                                            shape = RoundedCornerShape(10.dp),
                                            color = Amber50
                                        ) {
                                            Text(
                                                text = "✨ Spectacular",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = Amber600,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = event.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Slate600
                                )

                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "TODAY'S SHOWTIMES",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate800,
                                    letterSpacing = 1.sp
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    event.showtimes.forEach { time ->
                                        Surface(
                                            onClick = { onAddEventToPlan(event, time) },
                                            shape = RoundedCornerShape(12.dp),
                                            color = CleanBlue50,
                                            border = BorderStroke(1.dp, CleanBlue100)
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(Icons.Default.Add, contentDescription = null, tint = CleanBlue, modifier = Modifier.size(14.dp))
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = time,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontWeight = FontWeight.Bold,
                                                    color = CleanBlue
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                MoreSubSection.HOTEL -> {
                    if (hotelReservation != null) {
                        item {
                            Surface(
                                shape = RoundedCornerShape(24.dp),
                                color = CleanBlue,
                                shadowElevation = 2.dp,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(20.dp)) {
                                    Text(
                                        text = "ACTIVE HOTEL RESERVATION",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = CleanBlue100,
                                        letterSpacing = 1.2.sp
                                    )
                                    Text(
                                        text = hotelReservation.hotelName,
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Room ${hotelReservation.roomNumber} (${hotelReservation.roomTypeName})",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "Stay Dates: ${hotelReservation.checkInDate} to ${hotelReservation.checkOutDate}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = CleanBlue100
                                    )

                                    Spacer(modifier = Modifier.height(18.dp))

                                    // Digital Key Button
                                    Button(
                                        onClick = onUnlockHotelKey,
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (hotelReservation.digitalKeyUnlocked) Emerald500 else Color.White,
                                            contentColor = if (hotelReservation.digitalKeyUnlocked) Color.White else CleanBlue
                                        ),
                                        shape = RoundedCornerShape(16.dp),
                                        modifier = Modifier.fillMaxWidth().testTag("unlock_digital_key_btn")
                                    ) {
                                        Icon(
                                            imageVector = if (hotelReservation.digitalKeyUnlocked) Icons.Default.CheckCircle else Icons.Default.Nfc,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (hotelReservation.digitalKeyUnlocked) "Room 4218 Unlocked ✨" else "Unlock Door with Digital Key",
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Text(
                            text = "RESORT HOTELS IN DESTINATION",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Slate800,
                            letterSpacing = 1.sp
                        )
                    }

                    items(destination.hotels) { hotel ->
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Slate100),
                            shadowElevation = 1.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = hotel.tier.uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Amber600,
                                    fontSize = 9.sp
                                )
                                Text(
                                    text = hotel.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                Text(
                                    text = hotel.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Slate500
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "🚝 Transit: ${hotel.transportConnections.firstOrNull() ?: "Monorail & Bus"}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = CleanBlue
                                )
                            }
                        }
                    }
                }

                MoreSubSection.TRANSIT -> {
                    item {
                        Text(
                            text = "LIVE TRANSPORTATION DEPARTURES",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Slate800,
                            letterSpacing = 1.sp
                        )
                    }

                    items(destination.transportRoutes) { route ->
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
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        val icon = when (route.mode) {
                                            TransportMode.MONORAIL -> Icons.Default.DirectionsTransit
                                            TransportMode.SKYLINER -> Icons.Default.Cable
                                            TransportMode.FERRY_BOAT -> Icons.Default.DirectionsBoat
                                            TransportMode.RESORT_BUS -> Icons.Default.DirectionsBus
                                            TransportMode.WALKING_TRAIL -> Icons.Default.DirectionsWalk
                                        }
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = null,
                                            tint = CleanBlue,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = route.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Slate900
                                        )
                                    }
                                    Text(
                                        text = "${route.fromStop} ➔ ${route.toStop}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Slate500
                                    )
                                    Text(
                                        text = "Trip: ~${route.durationMinutes} min",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Slate400
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = CleanBlue50
                                ) {
                                    Text(
                                        text = "${route.nextDepartureMinutes} min",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = CleanBlue,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                MoreSubSection.FAMILY -> {
                    item {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Slate100),
                            shadowElevation = 1.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Party Meeting Point",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                Text(
                                    text = "📍 Central Plaza Hub by Partner's Statue",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = CleanBlue,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "If members get separated, meet at this central point or contact Guest Relations.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Slate500
                                )
                            }
                        }
                    }

                    item {
                        Text(
                            text = "FAMILY & FRIENDS IN YOUR PARTY",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Slate800,
                            letterSpacing = 1.sp
                        )
                    }

                    items(familyMembers) { member ->
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
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(CleanBlue50),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = member.name.take(1),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = CleanBlue
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${member.name} (${member.role})",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = Slate900
                                    )
                                    Text(
                                        text = member.currentLocation,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Slate500
                                    )
                                }
                            }
                        }
                    }
                }

                MoreSubSection.SETTINGS -> {
                    item {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Slate100),
                            shadowElevation = 1.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    text = "System & Operations Control",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                Spacer(modifier = Modifier.height(14.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text("Live Telemetry Simulation", fontWeight = FontWeight.SemiBold, color = Slate900)
                                        Text("Simulates live queue wait time fluctuations", style = MaterialTheme.typography.bodySmall, color = Slate500)
                                    }
                                    Switch(
                                        checked = isLiveSimulation,
                                        onCheckedChange = { onToggleLiveSimulation() }
                                    )
                                }

                                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Slate100)

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text("Offline Mode (Cached Data)", fontWeight = FontWeight.SemiBold, color = Slate900)
                                        Text("Keeps all park maps & passes usable without network", style = MaterialTheme.typography.bodySmall, color = Slate500)
                                    }
                                    Switch(
                                        checked = isOfflineMode,
                                        onCheckedChange = { onToggleOfflineMode() }
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

@Composable
fun TicketWalletCard(
    ticket: Ticket,
    onColorChanged: (Long) -> Unit
) {
    val magicColors = listOf(
        0xFF2563EB to "Clean Blue",
        0xFF1D4ED8 to "Deep Blue",
        0xFFF59E0B to "Gold",
        0xFF10B981 to "Emerald",
        0xFF7C3AED to "Purple",
        0xFFEA580C to "Coral"
    )

    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Slate100),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "THEME PARK PASS",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Slate400,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = ticket.holderName,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = ticket.type.label,
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate500
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = CleanBlue50
                ) {
                    Text(
                        text = "Valid Today",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = CleanBlue,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // MagicBand+ LED Customizer
            Text(
                text = "MAGICBAND+ ACCENT COLOR",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = Slate800,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                magicColors.forEach { (colorVal, _) ->
                    val isCurrent = ticket.magicBandColorHex == colorVal
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(colorVal))
                            .clickable { onColorChanged(colorVal) }
                            .then(
                                if (isCurrent) Modifier.border(2.5.dp, Slate900, CircleShape) else Modifier
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Clean Barcode Area
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Slate50,
                border = BorderStroke(1.dp, Slate100),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "||| | |||| | || ||||| ||| || |||| |||",
                        style = MaterialTheme.typography.titleLarge,
                        letterSpacing = 4.sp,
                        color = Slate900
                    )
                    Text(
                        text = ticket.barcodeData,
                        style = MaterialTheme.typography.labelSmall,
                        color = Slate400,
                        letterSpacing = 2.sp
                    )
                }
            }
        }
    }
}
