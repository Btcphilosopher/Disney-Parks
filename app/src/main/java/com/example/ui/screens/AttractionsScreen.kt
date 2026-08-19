package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.components.WaitTimeBadge
import com.example.ui.theme.*

@Composable
fun AttractionsScreen(
    park: Park,
    attractions: List<Attraction>,
    searchQuery: String,
    selectedLandId: String?,
    selectedThrill: ThrillLevel?,
    onlyLightningLane: Boolean,
    onlyAccessible: Boolean,
    sortOption: String,
    onSearchChange: (String) -> Unit,
    onSelectLand: (String?) -> Unit,
    onSelectThrill: (ThrillLevel?) -> Unit,
    onToggleLightningLane: () -> Unit,
    onToggleAccessible: () -> Unit,
    onSelectSort: (String) -> Unit,
    onSelectAttraction: (Attraction) -> Unit,
    onAddToItinerary: (Attraction) -> Unit,
    modifier: Modifier = Modifier
) {
    var showSortMenu by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceLight)
    ) {
        // Clean Minimal Search & Filter Header
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
                    text = "ATTRACTIONS & QUEUES",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Slate500,
                    letterSpacing = 1.2.sp
                )
                Text(
                    text = "${park.name} Wait Times",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    placeholder = { Text("Search rides, attractions, lands...", color = Slate400) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Slate400) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchChange("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Slate400)
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CleanBlue,
                        unfocusedBorderColor = Slate200,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Slate50
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("attractions_search_field")
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Land Filter Chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    item {
                        Surface(
                            onClick = { onSelectLand(null) },
                            shape = RoundedCornerShape(12.dp),
                            color = if (selectedLandId == null) CleanBlue else Color.White,
                            border = BorderStroke(1.dp, if (selectedLandId == null) CleanBlue else Slate200)
                        ) {
                            Text(
                                text = "All Lands",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (selectedLandId == null) FontWeight.Bold else FontWeight.Medium,
                                color = if (selectedLandId == null) Color.White else Slate700,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                    items(park.lands) { land ->
                        val isSelected = land.id == selectedLandId
                        Surface(
                            onClick = { onSelectLand(if (isSelected) null else land.id) },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) CleanBlue else Color.White,
                            border = BorderStroke(1.dp, if (isSelected) CleanBlue else Slate200)
                        ) {
                            Text(
                                text = land.name,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else Slate700,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                // Quick Toggle Filters (LL, Accessibility, Sort)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Surface(
                            onClick = onToggleLightningLane,
                            shape = RoundedCornerShape(12.dp),
                            color = if (onlyLightningLane) Amber50 else Color.White,
                            border = BorderStroke(1.dp, if (onlyLightningLane) Amber500 else Slate200)
                        ) {
                            Text(
                                text = "⚡ Lightning Lane",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (onlyLightningLane) Amber600 else Slate600,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }

                        Surface(
                            onClick = onToggleAccessible,
                            shape = RoundedCornerShape(12.dp),
                            color = if (onlyAccessible) CleanBlue50 else Color.White,
                            border = BorderStroke(1.dp, if (onlyAccessible) CleanBlue else Slate200)
                        ) {
                            Text(
                                text = "♿ Accessible",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (onlyAccessible) CleanBlue else Slate600,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }

                    // Sort Dropdown button
                    Box {
                        Surface(
                            onClick = { showSortMenu = true },
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Slate200)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(sortOption, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Slate700)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Slate500, modifier = Modifier.size(16.dp))
                            }
                        }

                        DropdownMenu(
                            expanded = showSortMenu,
                            onDismissRequest = { showSortMenu = false }
                        ) {
                            listOf("Shortest Wait", "Longest Wait", "Name (A–Z)", "Thrill Level").forEach { sort ->
                                DropdownMenuItem(
                                    text = { Text(sort) },
                                    onClick = {
                                        onSelectSort(sort)
                                        showSortMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Attractions List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (attractions.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.SearchOff,
                                contentDescription = null,
                                tint = Slate400,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "No attractions match your filters",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Slate500
                            )
                        }
                    }
                }
            }

            items(attractions) { attr ->
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Slate100),
                    shadowElevation = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectAttraction(attr) }
                        .testTag("attraction_card_${attr.id}")
                ) {
                    Column {
                        if (attr.imageResId != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = attr.imageResId),
                                    contentDescription = attr.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = attr.landName.uppercase(),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Slate400,
                                        fontSize = 9.sp
                                    )
                                    Text(
                                        text = attr.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Slate900
                                    )
                                }

                                WaitTimeBadge(
                                    waitTimeMinutes = attr.waitTimeMinutes,
                                    status = attr.status
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = attr.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = Slate500,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = Slate50
                                    ) {
                                        Text(
                                            text = attr.thrillLevel.label,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Slate600,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }

                                    if (attr.hasLightningLane) {
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = Amber50
                                        ) {
                                            Text(
                                                text = "⚡ LL ${attr.lightningLaneReturnTime ?: "11:30 AM"}",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = Amber600,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                            )
                                        }
                                    }
                                }

                                IconButton(
                                    onClick = { onAddToItinerary(attr) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.BookmarkAdd,
                                        contentDescription = "Add to Plan",
                                        tint = CleanBlue
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
