package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.*
import com.example.ui.components.MagicMapCanvas
import com.example.ui.components.WaitTimeBadge
import com.example.ui.theme.*
import com.example.viewmodel.RoutePlan

@Composable
fun MapScreen(
    park: Park,
    attractions: List<Attraction>,
    restaurants: List<Restaurant>,
    entertainment: List<EntertainmentEvent>,
    facilities: List<MapFacility>,
    selectedCategory: MapCategory,
    selectedPin: Any?,
    activeRoute: RoutePlan?,
    isAccessibleRouteOnly: Boolean,
    onSelectCategory: (MapCategory) -> Unit,
    onSelectPin: (Any?) -> Unit,
    onCalculateRoute: (String, String) -> Unit,
    onClearRoute: () -> Unit,
    onToggleAccessibleRoute: () -> Unit,
    onOpenAttractionDetail: (Attraction) -> Unit,
    onOpenRestaurantDetail: (Restaurant) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        MapCategory.ALL,
        MapCategory.ATTRACTIONS,
        MapCategory.DINING,
        MapCategory.CHARACTERS,
        MapCategory.RESTROOMS,
        MapCategory.TRANSPORT,
        MapCategory.FIRST_AID,
        MapCategory.SHOPS
    )

    Box(modifier = modifier.fillMaxSize()) {
        // Map Canvas
        MagicMapCanvas(
            park = park,
            attractions = attractions,
            restaurants = restaurants,
            entertainment = entertainment,
            facilities = facilities,
            selectedCategory = selectedCategory,
            selectedPin = selectedPin,
            activeRoute = activeRoute,
            onSelectPin = onSelectPin,
            onCalculateRoute = onCalculateRoute,
            modifier = Modifier.fillMaxSize()
        )

        // Floating Category Filter Bar at Top
        Surface(
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
            shape = RoundedCornerShape(24.dp),
            tonalElevation = 4.dp,
            shadowElevation = 4.dp,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 12.dp, start = 16.dp, end = 16.dp)
        ) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = cat == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectCategory(cat) },
                        label = {
                            Text(
                                text = cat.label,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = DisneyNavy,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Active Route Step-by-Step Floating Card
        AnimatedVisibility(
            visible = activeRoute != null,
            enter = slideInVertically { -it } + fadeIn(),
            exit = slideOutVertically { -it } + fadeOut(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 70.dp, start = 16.dp, end = 16.dp)
        ) {
            if (activeRoute != null) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DisneyNavy),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(6.dp, RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Walking to ${activeRoute.toName}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "${activeRoute.walkTimeMinutes} min (${activeRoute.distanceMeters}m) · ${if (activeRoute.isAccessible) "♿ Step-Free Route" else "Standard Path"}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = DisneyGoldLight
                                )
                            }

                            Row {
                                IconButton(onClick = onToggleAccessibleRoute) {
                                    Icon(
                                        imageVector = Icons.Default.Accessible,
                                        contentDescription = "Toggle Accessible Route",
                                        tint = if (isAccessibleRouteOnly) DisneyGold else Color.White.copy(alpha = 0.6f)
                                    )
                                }
                                IconButton(onClick = onClearRoute) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Clear Route",
                                        tint = Color.White
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Next: ${activeRoute.steps.firstOrNull() ?: "Follow highlighted gold path"}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }

        // Bottom Selected Pin Preview Card
        AnimatedVisibility(
            visible = selectedPin != null,
            enter = slideInVertically { it } + fadeIn(),
            exit = slideOutVertically { it } + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .padding(bottom = 64.dp) // Avoid bottom bar overlap
        ) {
            when (selectedPin) {
                is Attraction -> {
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(6.dp, RoundedCornerShape(18.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = selectedPin.landName.uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = selectedPin.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                WaitTimeBadge(
                                    waitTimeMinutes = selectedPin.waitTimeMinutes,
                                    status = selectedPin.status
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedButton(
                                    onClick = { onCalculateRoute("Current Location", selectedPin.name) },
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(Icons.Default.DirectionsWalk, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Walk")
                                }

                                Button(
                                    onClick = { onOpenAttractionDetail(selectedPin) },
                                    colors = ButtonDefaults.buttonColors(containerColor = DisneyNavy),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text("Details")
                                }
                            }
                        }
                    }
                }
                is Restaurant -> {
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(6.dp, RoundedCornerShape(18.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${selectedPin.landName} · ${selectedPin.priceTier}".uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = selectedPin.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${selectedPin.cuisine} · ${selectedPin.serviceType.label}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Button(
                                onClick = { onOpenRestaurantDetail(selectedPin) },
                                colors = ButtonDefaults.buttonColors(containerColor = DisneyCoral),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(if (selectedPin.allowsMobileOrder) "Mobile Order" else "Menu & Table")
                            }
                        }
                    }
                }
                is EntertainmentEvent -> {
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(6.dp, RoundedCornerShape(18.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = selectedPin.category.label.uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = DisneyPurple
                                )
                                Text(
                                    text = selectedPin.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Next: ${selectedPin.nextShowtime}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
                is MapFacility -> {
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(6.dp, RoundedCornerShape(18.dp))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = selectedPin.category.label.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = selectedPin.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${selectedPin.landName} · ${selectedPin.extraInfo}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
