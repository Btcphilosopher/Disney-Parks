package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*
import com.example.viewmodel.MobileCartItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionDetailSheet(
    attraction: Attraction,
    onBookLightningLane: (Attraction) -> Unit,
    onAddToItinerary: (Attraction) -> Unit,
    onGetDirections: (Attraction) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
        ) {
            // Header image if available
            if (attraction.imageResId != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Image(
                        painter = painterResource(id = attraction.imageResId),
                        contentDescription = attraction.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Title & Land
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = attraction.landName.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = attraction.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                WaitTimeBadge(
                    waitTimeMinutes = attraction.waitTimeMinutes,
                    status = attraction.status
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = attraction.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lightning Lane Card if available
            if (attraction.hasLightningLane) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = DisneyGoldContainer.copy(alpha = 0.6f)
                    ),
                    border = BorderStroke(1.dp, DisneyGold.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = "Lightning Lane",
                                tint = DisneyGold,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Lightning Lane Multi Pass",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6B4800)
                                )
                                Text(
                                    text = "Return Window: ${attraction.lightningLaneReturnTime ?: "11:30 AM"}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF855D00)
                                )
                            }
                        }

                        Button(
                            onClick = { onBookLightningLane(attraction) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DisneyNavy,
                                contentColor = DisneyGoldLight
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("book_lightning_lane_btn")
                        ) {
                            Text("Book LL")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Specs Grid (Thrill, Height, Duration, Single Rider)
            Text(
                text = "EXPERIENCE DETAILS",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoPill(
                    icon = Icons.Default.Speed,
                    label = attraction.thrillLevel.label,
                    modifier = Modifier.weight(1f)
                )
                InfoPill(
                    icon = Icons.Default.Straighten,
                    label = if (attraction.heightRequirementCm != null) "${attraction.heightRequirementCm} cm min" else "Any Height",
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoPill(
                    icon = Icons.Default.Timer,
                    label = "${attraction.durationMinutes} min ride",
                    modifier = Modifier.weight(1f)
                )
                InfoPill(
                    icon = if (attraction.isIndoor) Icons.Default.Nightlight else Icons.Default.WbSunny,
                    label = if (attraction.isIndoor) "Indoor (Air Cond)" else "Outdoor",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Accessibility Features
            Text(
                text = "ACCESSIBILITY & GUEST SERVICES",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (attraction.accessibilityWheelchair) {
                    SuggestionChip(
                        onClick = {},
                        label = { Text("Wheelchair / ECV Access", style = MaterialTheme.typography.bodySmall) },
                        icon = { Icon(Icons.Default.Accessible, null, modifier = Modifier.size(16.dp)) }
                    )
                }
                if (attraction.accessibilityAudioDescription) {
                    SuggestionChip(
                        onClick = {},
                        label = { Text("Audio Description", style = MaterialTheme.typography.bodySmall) },
                        icon = { Icon(Icons.Default.Hearing, null, modifier = Modifier.size(16.dp)) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action CTAs
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { onGetDirections(attraction) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("get_directions_btn"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.DirectionsWalk, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Directions")
                }

                Button(
                    onClick = { onAddToItinerary(attraction) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("add_to_plan_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = DisneyNavy),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Add to Plan")
                }
            }
        }
    }
}

@Composable
private fun InfoPill(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun RestaurantDetailSheet(
    restaurant: Restaurant,
    cartItemCount: Int,
    onAddToCart: (MenuItem) -> Unit,
    onOpenCart: () -> Unit,
    onBookReservation: (Restaurant, String, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var partySize by remember { mutableIntStateOf(4) }
    var selectedTimeSlot by remember { mutableStateOf(restaurant.availableReservationSlots.firstOrNull() ?: "12:00 PM") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
        ) {
            if (restaurant.imageResId != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Image(
                        painter = painterResource(id = restaurant.imageResId),
                        contentDescription = restaurant.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${restaurant.landName} · ${restaurant.priceTier}".uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = restaurant.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${restaurant.cuisine} · ${restaurant.serviceType.label}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Dietary chips
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                restaurant.dietaryOptions.forEach { opt ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFE8F5E9),
                        border = BorderStroke(1.dp, Color(0xFF81C784))
                    ) {
                        Text(
                            text = "🌱 $opt",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Table Service Reservation Booking Section
            if (restaurant.availableReservationSlots.isNotEmpty()) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Reserve a Table",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Party Size: $partySize Guests", style = MaterialTheme.typography.bodyMedium)
                            Row {
                                IconButton(onClick = { if (partySize > 1) partySize-- }) {
                                    Icon(Icons.Default.RemoveCircleOutline, contentDescription = "Decrease")
                                }
                                IconButton(onClick = { if (partySize < 12) partySize++ }) {
                                    Icon(Icons.Default.AddCircleOutline, contentDescription = "Increase")
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Available Today:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(6.dp))

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            restaurant.availableReservationSlots.forEach { slot ->
                                val isSelected = slot == selectedTimeSlot
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { selectedTimeSlot = slot },
                                    label = { Text(slot) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = { onBookReservation(restaurant, selectedTimeSlot, partySize) },
                            colors = ButtonDefaults.buttonColors(containerColor = DisneyNavy),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().testTag("confirm_dining_reservation_btn")
                        ) {
                            Text("Book Table for $selectedTimeSlot")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Menu & Mobile Order Section
            Text(
                text = "SIGNATURE MENU ITEMS",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(10.dp))

            restaurant.menuItems.forEach { item ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                if (item.isChefSpecial) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("⭐ Special", style = MaterialTheme.typography.labelSmall, color = DisneyGold)
                                }
                            }
                            Text(
                                text = item.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "$${String.format("%.2f", item.price)}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        if (restaurant.allowsMobileOrder) {
                            Spacer(modifier = Modifier.width(12.dp))
                            IconButton(
                                onClick = { onAddToCart(item) },
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(DisneyIceBlue)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Add to Cart", tint = DisneyNavy)
                            }
                        }
                    }
                }
            }

            if (restaurant.allowsMobileOrder && cartItemCount > 0) {
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = onOpenCart,
                    colors = ButtonDefaults.buttonColors(containerColor = DisneyCoral),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().testTag("view_mobile_cart_btn")
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("View Mobile Order Cart ($cartItemCount items)")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MobileOrderCartSheet(
    cartItems: List<MobileCartItem>,
    restaurantId: String,
    onCheckout: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val subtotal = cartItems.sumOf { it.menuItem.price * it.quantity }
    val tax = subtotal * 0.065
    val total = subtotal + tax

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "Mobile Order Cart",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Pick-up window: Ready in approx. 10–15 minutes",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            cartItems.forEach { cartItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = cartItem.menuItem.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Qty: ${cartItem.quantity} · $${String.format("%.2f", cartItem.menuItem.price)} each",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "$${String.format("%.2f", cartItem.menuItem.price * cartItem.quantity)}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Subtotal", style = MaterialTheme.typography.bodySmall)
                Text("$${String.format("%.2f", subtotal)}", style = MaterialTheme.typography.bodySmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Florida State Tax (6.5%)", style = MaterialTheme.typography.bodySmall)
                Text("$${String.format("%.2f", tax)}", style = MaterialTheme.typography.bodySmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("$${String.format("%.2f", total)}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = DisneyNavy)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { onCheckout(restaurantId) },
                colors = ButtonDefaults.buttonColors(containerColor = DisneyNavy, contentColor = DisneyGoldLight),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().testTag("place_mobile_order_btn")
            ) {
                Text("Pay & Place Mobile Order ($${String.format("%.2f", total)})", fontWeight = FontWeight.Bold)
            }
        }
    }
}
