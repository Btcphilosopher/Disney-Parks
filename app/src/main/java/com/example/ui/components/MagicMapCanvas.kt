package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*
import com.example.viewmodel.RoutePlan
import kotlin.math.sqrt

@Composable
fun MagicMapCanvas(
    park: Park,
    attractions: List<Attraction>,
    restaurants: List<Restaurant>,
    entertainment: List<EntertainmentEvent>,
    facilities: List<MapFacility>,
    selectedCategory: MapCategory,
    selectedPin: Any?,
    activeRoute: RoutePlan?,
    onSelectPin: (Any?) -> Unit,
    onCalculateRoute: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    val userPos = remember { Offset(0.52f, 0.58f) } // User location near Hub / Tomorrowland

    val textMeasurer = rememberTextMeasurer()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE8EFF5))
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(0.7f, 3.5f)
                    val maxOffset = 600f * scale
                    offsetX = (offsetX + pan.x).coerceIn(-maxOffset, maxOffset)
                    offsetY = (offsetY + pan.y).coerceIn(-maxOffset, maxOffset)
                }
            }
            .pointerInput(selectedCategory, attractions, restaurants, entertainment, facilities) {
                detectTapGestures { tapOffset ->
                    // Convert screen tap offset to normalized map space (0..1)
                    val mapWidth = size.width
                    val mapHeight = size.height
                    val centerX = mapWidth / 2f + offsetX
                    val centerY = mapHeight / 2f + offsetY

                    // Invert transform
                    val normX = (tapOffset.x - centerX) / (mapWidth * scale) + 0.5f
                    val normY = (tapOffset.y - centerY) / (mapHeight * scale) + 0.5f

                    // Hit test pins within radius
                    val hitRadius = 0.07f / scale

                    val clickedAttr = if (selectedCategory == MapCategory.ALL || selectedCategory == MapCategory.ATTRACTIONS) {
                        attractions.find {
                            val dx = it.locationX - normX
                            val dy = it.locationY - normY
                            sqrt(dx * dx + dy * dy) < hitRadius
                        }
                    } else null

                    val clickedRest = if (clickedAttr == null && (selectedCategory == MapCategory.ALL || selectedCategory == MapCategory.DINING)) {
                        restaurants.find {
                            val dx = it.locationX - normX
                            val dy = it.locationY - normY
                            sqrt(dx * dx + dy * dy) < hitRadius
                        }
                    } else null

                    val clickedEnt = if (clickedAttr == null && clickedRest == null && (selectedCategory == MapCategory.ALL || selectedCategory == MapCategory.CHARACTERS)) {
                        entertainment.find {
                            val dx = it.locationX - normX
                            val dy = it.locationY - normY
                            sqrt(dx * dx + dy * dy) < hitRadius
                        }
                    } else null

                    val clickedFac = if (clickedAttr == null && clickedRest == null && clickedEnt == null) {
                        facilities.find {
                            val isCatMatch = when (selectedCategory) {
                                MapCategory.ALL -> true
                                MapCategory.RESTROOMS -> it.category == MapCategory.RESTROOMS
                                MapCategory.FIRST_AID -> it.category == MapCategory.FIRST_AID
                                MapCategory.SHOPS -> it.category == MapCategory.SHOPS
                                MapCategory.CHARACTERS -> it.category == MapCategory.CHARACTERS
                                else -> false
                            }
                            if (isCatMatch) {
                                val dx = it.x - normX
                                val dy = it.y - normY
                                sqrt(dx * dx + dy * dy) < hitRadius
                            } else false
                        }
                    } else null

                    val selected = clickedAttr ?: clickedRest ?: clickedEnt ?: clickedFac
                    onSelectPin(selected)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasW = size.width
            val canvasH = size.height
            val cx = canvasW / 2f + offsetX
            val cy = canvasH / 2f + offsetY

            // Helper to transform normalized coord to canvas coord
            fun toCanvas(normX: Float, normY: Float): Offset {
                return Offset(
                    x = cx + (normX - 0.5f) * canvasW * scale,
                    y = cy + (normY - 0.5f) * canvasH * scale
                )
            }

            // Draw Park Base Lands Contours
            // 1. Water Moat & Seven Seas Lagoon Waterway
            drawCircle(
                color = Color(0xFFC7E2F7),
                radius = 180f * scale,
                center = toCanvas(0.5f, 0.48f)
            )

            // 2. Central Hub Lawn
            drawCircle(
                color = Color(0xFFD4ECD5),
                radius = 120f * scale,
                center = toCanvas(0.5f, 0.50f)
            )

            // 3. Tomorrowland Sector (East)
            drawRoundRect(
                color = Color(0xFFDCEAF7),
                topLeft = toCanvas(0.68f, 0.22f),
                size = Size(240f * scale, 220f * scale),
                cornerRadius = CornerRadius(24f * scale, 24f * scale)
            )

            // 4. Fantasyland Sector (North)
            drawRoundRect(
                color = Color(0xFFEFE5F8),
                topLeft = toCanvas(0.34f, 0.12f),
                size = Size(320f * scale, 180f * scale),
                cornerRadius = CornerRadius(28f * scale, 28f * scale)
            )

            // 5. Adventureland & Frontierland Sector (West)
            drawRoundRect(
                color = Color(0xFFE4EFD8),
                topLeft = toCanvas(0.12f, 0.32f),
                size = Size(220f * scale, 300f * scale),
                cornerRadius = CornerRadius(24f * scale, 24f * scale)
            )

            // 6. Main Street Promenade (South)
            drawRoundRect(
                color = Color(0xFFF9EFE0),
                topLeft = toCanvas(0.44f, 0.56f),
                size = Size(120f * scale, 300f * scale),
                cornerRadius = CornerRadius(16f * scale, 16f * scale)
            )

            // Land Label Texts
            val labelStyle = TextStyle(
                color = DisneyNavy.copy(alpha = 0.65f),
                fontSize = (11 * scale).coerceIn(9f, 15f).sp,
                fontWeight = FontWeight.Bold
            )
            drawText(textMeasurer, "TOMORROWLAND", toCanvas(0.70f, 0.25f), labelStyle)
            drawText(textMeasurer, "FANTASYLAND", toCanvas(0.42f, 0.15f), labelStyle)
            drawText(textMeasurer, "ADVENTURELAND", toCanvas(0.15f, 0.55f), labelStyle)
            drawText(textMeasurer, "MAIN STREET, U.S.A.", toCanvas(0.43f, 0.75f), labelStyle)

            // Draw Castle Landmark in Center
            val castleCenter = toCanvas(0.50f, 0.46f)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(DisneyGoldContainer, DisneyGold.copy(alpha = 0.5f)),
                    center = castleCenter,
                    radius = 32f * scale
                ),
                radius = 28f * scale,
                center = castleCenter
            )
            drawCircle(
                color = DisneyNavy,
                radius = 20f * scale,
                center = castleCenter
            )
            drawText(
                textMeasurer,
                "🏰 Castle",
                castleCenter.plus(Offset(-22f * scale, -8f * scale)),
                TextStyle(color = Color.White, fontSize = (10 * scale).coerceIn(8f, 14f).sp, fontWeight = FontWeight.Bold)
            )

            // Draw Active Walking Route if exists
            if (activeRoute != null) {
                val targetPin = attractions.find { it.name == activeRoute.toName }
                val targetCoord = if (targetPin != null) {
                    toCanvas(targetPin.locationX, targetPin.locationY)
                } else {
                    toCanvas(0.82f, 0.38f)
                }
                val userCoord = toCanvas(userPos.x, userPos.y)

                // Draw connecting bezier walk trail
                val path = Path().apply {
                    moveTo(userCoord.x, userCoord.y)
                    val midX = (userCoord.x + targetCoord.x) / 2f
                    val midY = (userCoord.y + targetCoord.y) / 2f - 40f * scale
                    quadraticTo(midX, midY, targetCoord.x, targetCoord.y)
                }

                drawPath(
                    path = path,
                    color = DisneyGold,
                    style = Stroke(
                        width = 5f * scale,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(16f * scale, 12f * scale), 0f),
                        cap = StrokeCap.Round
                    )
                )

                // Route midpoint ETA bubble
                val midBubble = Offset((userCoord.x + targetCoord.x) / 2f, (userCoord.y + targetCoord.y) / 2f - 24f * scale)
                drawRoundRect(
                    color = DisneyNavy,
                    topLeft = midBubble.plus(Offset(-45f * scale, -14f * scale)),
                    size = Size(90f * scale, 28f * scale),
                    cornerRadius = CornerRadius(14f * scale, 14f * scale)
                )
                drawText(
                    textMeasurer,
                    "🚶 ${activeRoute.walkTimeMinutes} min",
                    midBubble.plus(Offset(-36f * scale, -7f * scale)),
                    TextStyle(color = DisneyGoldLight, fontSize = (10 * scale).coerceIn(8f, 13f).sp, fontWeight = FontWeight.Bold)
                )
            }

            // Draw User Location Starlight Pulse
            val userCanvasPos = toCanvas(userPos.x, userPos.y)
            drawCircle(
                color = DisneyBlueAccent.copy(alpha = 0.25f),
                radius = 24f * scale,
                center = userCanvasPos
            )
            drawCircle(
                color = Color.White,
                radius = 10f * scale,
                center = userCanvasPos
            )
            drawCircle(
                color = DisneyBlueAccent,
                radius = 7f * scale,
                center = userCanvasPos
            )

            // Draw Attractions Pins
            if (selectedCategory == MapCategory.ALL || selectedCategory == MapCategory.ATTRACTIONS) {
                attractions.forEach { attr ->
                    val pos = toCanvas(attr.locationX, attr.locationY)
                    val isSelected = selectedPin == attr

                    val pinBg = when (attr.status) {
                        AttractionStatus.OPERATING -> when {
                            attr.waitTimeMinutes <= 20 -> Color(0xFF108A58)
                            attr.waitTimeMinutes <= 45 -> Color(0xFFD97706)
                            else -> Color(0xFFDC2626)
                        }
                        else -> Color(0xFF64748B)
                    }

                    // Selection aura
                    if (isSelected) {
                        drawCircle(
                            color = DisneyGold,
                            radius = 28f * scale,
                            center = pos,
                            style = Stroke(width = 3f * scale)
                        )
                    }

                    // Pin Badge Bubble (Pill shape)
                    val pillWidth = 56f * scale
                    val pillHeight = 26f * scale
                    drawRoundRect(
                        color = if (isSelected) DisneyNavy else pinBg,
                        topLeft = pos.plus(Offset(-pillWidth / 2f, -pillHeight / 2f)),
                        size = Size(pillWidth, pillHeight),
                        cornerRadius = CornerRadius(13f * scale, 13f * scale)
                    )
                    drawRoundRect(
                        color = Color.White,
                        topLeft = pos.plus(Offset(-pillWidth / 2f, -pillHeight / 2f)),
                        size = Size(pillWidth, pillHeight),
                        cornerRadius = CornerRadius(13f * scale, 13f * scale),
                        style = Stroke(width = 1.5f * scale)
                    )

                    val waitLabel = if (attr.status == AttractionStatus.OPERATING) "${attr.waitTimeMinutes}m" else "Off"
                    drawText(
                        textMeasurer,
                        waitLabel,
                        pos.plus(Offset(-16f * scale, -7f * scale)),
                        TextStyle(color = Color.White, fontSize = (10 * scale).coerceIn(8f, 13f).sp, fontWeight = FontWeight.Bold)
                    )
                }
            }

            // Draw Dining Pins
            if (selectedCategory == MapCategory.ALL || selectedCategory == MapCategory.DINING) {
                restaurants.forEach { rest ->
                    val pos = toCanvas(rest.locationX, rest.locationY)
                    val isSelected = selectedPin == rest

                    if (isSelected) {
                        drawCircle(
                            color = DisneyGold,
                            radius = 22f * scale,
                            center = pos,
                            style = Stroke(width = 3f * scale)
                        )
                    }

                    drawCircle(
                        color = DisneyCoral,
                        radius = 14f * scale,
                        center = pos
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 14f * scale,
                        center = pos,
                        style = Stroke(width = 1.5f * scale)
                    )
                    drawText(
                        textMeasurer,
                        "🍴",
                        pos.plus(Offset(-7f * scale, -8f * scale)),
                        TextStyle(fontSize = (10 * scale).coerceIn(8f, 13f).sp)
                    )
                }
            }

            // Draw Character / Entertainment Pins
            if (selectedCategory == MapCategory.ALL || selectedCategory == MapCategory.CHARACTERS) {
                entertainment.forEach { ent ->
                    val pos = toCanvas(ent.locationX, ent.locationY)
                    val isSelected = selectedPin == ent

                    if (isSelected) {
                        drawCircle(
                            color = DisneyGold,
                            radius = 22f * scale,
                            center = pos,
                            style = Stroke(width = 3f * scale)
                        )
                    }

                    drawCircle(
                        color = DisneyPurple,
                        radius = 14f * scale,
                        center = pos
                    )
                    drawText(
                        textMeasurer,
                        "✨",
                        pos.plus(Offset(-7f * scale, -8f * scale)),
                        TextStyle(fontSize = (10 * scale).coerceIn(8f, 13f).sp)
                    )
                }
            }

            // Draw Facilities Pins (Restrooms, First Aid, Shops)
            facilities.forEach { fac ->
                val shouldDraw = when (selectedCategory) {
                    MapCategory.ALL -> false // Don't crowd ALL with restrooms
                    MapCategory.RESTROOMS -> fac.category == MapCategory.RESTROOMS
                    MapCategory.FIRST_AID -> fac.category == MapCategory.FIRST_AID
                    MapCategory.SHOPS -> fac.category == MapCategory.SHOPS
                    else -> false
                }

                if (shouldDraw) {
                    val pos = toCanvas(fac.x, fac.y)
                    val iconText = when (fac.category) {
                        MapCategory.RESTROOMS -> "🚻"
                        MapCategory.FIRST_AID -> "🏥"
                        MapCategory.SHOPS -> "🛍️"
                        else -> "ℹ️"
                    }
                    drawCircle(
                        color = DisneyNavy,
                        radius = 14f * scale,
                        center = pos
                    )
                    drawText(
                        textMeasurer,
                        iconText,
                        pos.plus(Offset(-7f * scale, -8f * scale)),
                        TextStyle(fontSize = (10 * scale).coerceIn(8f, 13f).sp)
                    )
                }
            }
        }

        // Map Float Controls (Zoom In/Out, Recenter on User)
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = if (selectedPin != null) 180.dp else 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FloatingActionButton(
                onClick = {
                    scale = (scale * 1.3f).coerceAtMost(3.5f)
                },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(44.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Zoom In")
            }

            FloatingActionButton(
                onClick = {
                    scale = (scale / 1.3f).coerceAtLeast(0.7f)
                },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(44.dp)
            ) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = "Zoom Out")
            }

            FloatingActionButton(
                onClick = {
                    scale = 1.2f
                    offsetX = -(userPos.x - 0.5f) * 600f
                    offsetY = -(userPos.y - 0.5f) * 600f
                },
                containerColor = DisneyNavy,
                contentColor = DisneyGoldLight,
                modifier = Modifier.size(44.dp)
            ) {
                Icon(imageVector = Icons.Default.MyLocation, contentDescription = "Locate Me")
            }
        }
    }
}
