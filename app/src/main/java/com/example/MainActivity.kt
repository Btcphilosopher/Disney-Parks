package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.Attraction
import com.example.model.Restaurant
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.viewmodel.DisneyNavTab
import com.example.viewmodel.DisneyViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: DisneyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DisneyParksTheme {
                DisneyApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisneyApp(viewModel: DisneyViewModel) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val destinations by viewModel.destinations.collectAsStateWithLifecycle()
    val activeDestination by viewModel.activeDestination.collectAsStateWithLifecycle()
    val activePark by viewModel.activePark.collectAsStateWithLifecycle()
    val attractions by viewModel.attractions.collectAsStateWithLifecycle()
    val filteredAttractions by viewModel.filteredAttractions.collectAsStateWithLifecycle()
    val restaurants by viewModel.restaurants.collectAsStateWithLifecycle()
    val entertainment by viewModel.entertainment.collectAsStateWithLifecycle()
    val facilities by viewModel.facilities.collectAsStateWithLifecycle()
    val tickets by viewModel.tickets.collectAsStateWithLifecycle()
    val itinerary by viewModel.itinerary.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()
    val hotelReservation by viewModel.hotelReservation.collectAsStateWithLifecycle()
    val familyMembers by viewModel.familyMembers.collectAsStateWithLifecycle()
    val smartRecommendations by viewModel.smartRecommendations.collectAsStateWithLifecycle()

    val isLiveSimulation by viewModel.isLiveSimulationActive.collectAsStateWithLifecycle()
    val isOfflineMode by viewModel.isOfflineMode.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedLandFilter by viewModel.selectedLandFilter.collectAsStateWithLifecycle()
    val selectedThrillFilter by viewModel.selectedThrillFilter.collectAsStateWithLifecycle()
    val onlyLightningLane by viewModel.onlyLightningLane.collectAsStateWithLifecycle()
    val onlyAccessible by viewModel.onlyAccessible.collectAsStateWithLifecycle()
    val sortOption by viewModel.sortOption.collectAsStateWithLifecycle()

    val selectedMapCategory by viewModel.selectedMapCategory.collectAsStateWithLifecycle()
    val selectedPin by viewModel.selectedPin.collectAsStateWithLifecycle()
    val activeRoute by viewModel.activeRoute.collectAsStateWithLifecycle()
    val isAccessibleRouteOnly by viewModel.isAccessibleRouteOnly.collectAsStateWithLifecycle()

    val isMyDayLiveMode by viewModel.isMyDayLiveMode.collectAsStateWithLifecycle()

    val selectedAttraction by viewModel.selectedAttraction.collectAsStateWithLifecycle()
    val selectedRestaurant by viewModel.selectedRestaurant.collectAsStateWithLifecycle()
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val showCartSheet by viewModel.showCartSheet.collectAsStateWithLifecycle()
    val feedbackMessage by viewModel.feedbackMessage.collectAsStateWithLifecycle()

    var showDestinationPicker by remember { mutableStateOf(false) }
    var showNotificationsSheet by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(feedbackMessage) {
        feedbackMessage?.let { msg ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(msg)
                viewModel.clearFeedback()
            }
        }
    }

    val unreadNotifCount = notifications.count { !it.read }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DisneyTopBar(
                activeDestination = activeDestination,
                activePark = activePark,
                unreadNotificationCount = unreadNotifCount,
                isOfflineMode = isOfflineMode,
                isLiveSimulation = isLiveSimulation,
                onOpenDestinationPicker = { showDestinationPicker = true },
                onOpenNotifications = { showNotificationsSheet = true },
                onToggleOffline = { viewModel.toggleOfflineMode() }
            )
        },
        bottomBar = {
            Surface(
                color = Color.White,
                border = BorderStroke(1.dp, Slate100),
                modifier = Modifier.fillMaxWidth()
            ) {
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 0.dp,
                    modifier = Modifier.height(72.dp)
                ) {
                    DisneyNavTab.values().forEach { tab ->
                        val isSelected = currentTab == tab
                        val emoji = when (tab) {
                            DisneyNavTab.HOME -> "🏠"
                            DisneyNavTab.MAP -> "🗺️"
                            DisneyNavTab.ATTRACTIONS -> "⚡"
                            DisneyNavTab.PLAN -> "📅"
                            DisneyNavTab.MORE -> "⋯"
                        }

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.selectTab(tab) },
                            icon = {
                                Box(
                                    modifier = Modifier
                                        .width(48.dp)
                                        .height(32.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) CleanBlue50 else Color.Transparent),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = emoji,
                                        fontSize = 18.sp
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = tab.label,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) CleanBlue else Slate400,
                                    fontSize = 10.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            ),
                            modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                        )
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 72.dp)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                DisneyNavTab.HOME -> {
                    HomeScreen(
                        destination = activeDestination,
                        park = activePark,
                        attractions = attractions,
                        itinerary = itinerary,
                        recommendations = smartRecommendations,
                        hotelReservation = hotelReservation,
                        onNavigateTab = { viewModel.selectTab(it) },
                        onSelectAttraction = { viewModel.openAttractionDetail(it) },
                        onStartRoute = { from, to -> viewModel.calculateRoute(from, to) },
                        onToggleItineraryItem = { viewModel.toggleItineraryComplete(it) }
                    )
                }

                DisneyNavTab.MAP -> {
                    MapScreen(
                        park = activePark,
                        attractions = attractions,
                        restaurants = restaurants,
                        entertainment = entertainment,
                        facilities = facilities,
                        selectedCategory = selectedMapCategory,
                        selectedPin = selectedPin,
                        activeRoute = activeRoute,
                        isAccessibleRouteOnly = isAccessibleRouteOnly,
                        onSelectCategory = { viewModel.setMapCategory(it) },
                        onSelectPin = { viewModel.selectMapPin(it) },
                        onCalculateRoute = { from, to -> viewModel.calculateRoute(from, to) },
                        onClearRoute = { viewModel.clearRoute() },
                        onToggleAccessibleRoute = { viewModel.toggleAccessibleRouteOnly() },
                        onOpenAttractionDetail = { viewModel.openAttractionDetail(it) },
                        onOpenRestaurantDetail = { viewModel.openRestaurantDetail(it) }
                    )
                }

                DisneyNavTab.ATTRACTIONS -> {
                    AttractionsScreen(
                        park = activePark,
                        attractions = filteredAttractions,
                        searchQuery = searchQuery,
                        selectedLandId = selectedLandFilter,
                        selectedThrill = selectedThrillFilter,
                        onlyLightningLane = onlyLightningLane,
                        onlyAccessible = onlyAccessible,
                        sortOption = sortOption,
                        onSearchChange = { viewModel.setQuery(it) },
                        onSelectLand = { viewModel.setLandFilter(it) },
                        onSelectThrill = { viewModel.setThrillFilter(it) },
                        onToggleLightningLane = { viewModel.toggleLightningLaneOnly() },
                        onToggleAccessible = { viewModel.toggleAccessibleOnly() },
                        onSelectSort = { viewModel.setSortOption(it) },
                        onSelectAttraction = { viewModel.openAttractionDetail(it) },
                        onAddToItinerary = { viewModel.addAttractionToItinerary(it) }
                    )
                }

                DisneyNavTab.PLAN -> {
                    PlanScreen(
                        park = activePark,
                        itinerary = itinerary,
                        recommendations = smartRecommendations,
                        isLiveMode = isMyDayLiveMode,
                        onToggleLiveMode = { viewModel.toggleMyDayMode() },
                        onToggleItemComplete = { viewModel.toggleItineraryComplete(it) },
                        onRemoveItem = { viewModel.removeItineraryItem(it) },
                        onStartRoute = { from, to -> viewModel.calculateRoute(from, to) },
                        onAddCustomItem = { viewModel.addCustomItineraryItem(it) }
                    )
                }

                DisneyNavTab.MORE -> {
                    MoreScreen(
                        destination = activeDestination,
                        park = activePark,
                        tickets = tickets,
                        restaurants = restaurants,
                        entertainment = entertainment,
                        hotelReservation = hotelReservation,
                        familyMembers = familyMembers,
                        isLiveSimulation = isLiveSimulation,
                        isOfflineMode = isOfflineMode,
                        onSelectRestaurant = { viewModel.openRestaurantDetail(it) },
                        onAddEventToPlan = { event, time -> viewModel.addEventToItinerary(event, time) },
                        onUpdateMagicBandColor = { id, color -> viewModel.updateMagicBandColor(id, color) },
                        onUnlockHotelKey = { viewModel.unlockDigitalKey() },
                        onToggleLiveSimulation = { viewModel.toggleLiveSimulation() },
                        onToggleOfflineMode = { viewModel.toggleOfflineMode() }
                    )
                }
            }
        }
    }

    // Modal Bottom Sheets
    if (showDestinationPicker) {
        DestinationSelectorSheet(
            destinations = destinations,
            activeDestination = activeDestination,
            activePark = activePark,
            onSelectDestination = { viewModel.selectDestination(it) },
            onSelectPark = { viewModel.selectPark(it) },
            onDismiss = { showDestinationPicker = false }
        )
    }

    if (showNotificationsSheet) {
        NotificationsSheet(
            notifications = notifications,
            onMarkRead = { viewModel.markNotificationRead(it) },
            onDismissItem = { viewModel.dismissNotification(it) },
            onDismissSheet = { showNotificationsSheet = false }
        )
    }

    selectedAttraction?.let { attr ->
        AttractionDetailSheet(
            attraction = attr,
            onBookLightningLane = { viewModel.bookLightningLane(it) },
            onAddToItinerary = { viewModel.addAttractionToItinerary(it) },
            onGetDirections = {
                viewModel.closeAttractionDetail()
                viewModel.calculateRoute("Current Location", it.name)
            },
            onDismiss = { viewModel.closeAttractionDetail() }
        )
    }

    selectedRestaurant?.let { rest ->
        val totalInCart = cartItems.sumOf { it.quantity }
        RestaurantDetailSheet(
            restaurant = rest,
            cartItemCount = totalInCart,
            onAddToCart = { viewModel.addToMobileCart(it) },
            onOpenCart = { viewModel.openCart() },
            onBookReservation = { r, time, size -> viewModel.bookDiningReservation(r, time, size) },
            onDismiss = { viewModel.closeRestaurantDetail() }
        )
    }

    if (showCartSheet && selectedRestaurant != null) {
        MobileOrderCartSheet(
            cartItems = cartItems,
            restaurantId = selectedRestaurant!!.id,
            onCheckout = { viewModel.checkoutMobileOrder(it) },
            onDismiss = { viewModel.closeCart() }
        )
    }
}
