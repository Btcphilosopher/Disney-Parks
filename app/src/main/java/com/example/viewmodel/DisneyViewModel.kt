package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.*
import com.example.service.DisneyParkService
import com.example.service.DisneyRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class DisneyNavTab(val label: String) {
    HOME("Home"),
    MAP("Map"),
    ATTRACTIONS("Attractions"),
    PLAN("Plan"),
    MORE("More")
}

data class RoutePlan(
    val fromName: String,
    val toName: String,
    val distanceMeters: Int,
    val walkTimeMinutes: Int,
    val steps: List<String>,
    val isAccessible: Boolean
)

data class MobileCartItem(
    val menuItem: MenuItem,
    val quantity: Int
)

data class FilterCriteria(
    val query: String,
    val land: String?,
    val thrill: ThrillLevel?,
    val llOnly: Boolean,
    val accOnly: Boolean,
    val sort: String
)

class DisneyViewModel(
    private val repository: DisneyParkService = DisneyRepositoryImpl()
) : ViewModel() {

    val destinations = repository.destinations
    val activeDestination = repository.activeDestination
    val activePark = repository.activePark
    val attractions = repository.attractions
    val restaurants = repository.restaurants
    val entertainment = repository.entertainment
    val facilities = repository.facilities
    val tickets = repository.tickets
    val itinerary = repository.itinerary
    val notifications = repository.notifications
    val hotelReservation = repository.hotelReservation
    val familyMembers = repository.familyMembers
    val isLiveSimulationActive = repository.isLiveSimulationActive
    val isOfflineMode = repository.isOfflineMode

    private val _currentTab = MutableStateFlow(DisneyNavTab.HOME)
    val currentTab: StateFlow<DisneyNavTab> = _currentTab.asStateFlow()

    // Search
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Attractions Filter & Sort
    private val _selectedLandFilter = MutableStateFlow<String?>(null)
    val selectedLandFilter: StateFlow<String?> = _selectedLandFilter.asStateFlow()

    private val _selectedThrillFilter = MutableStateFlow<ThrillLevel?>(null)
    val selectedThrillFilter: StateFlow<ThrillLevel?> = _selectedThrillFilter.asStateFlow()

    private val _onlyLightningLane = MutableStateFlow(false)
    val onlyLightningLane: StateFlow<Boolean> = _onlyLightningLane.asStateFlow()

    private val _onlyAccessible = MutableStateFlow(false)
    val onlyAccessible: StateFlow<Boolean> = _onlyAccessible.asStateFlow()

    private val _sortOption = MutableStateFlow("Shortest Wait")
    val sortOption: StateFlow<String> = _sortOption.asStateFlow()

    // Map
    private val _selectedMapCategory = MutableStateFlow(MapCategory.ALL)
    val selectedMapCategory: StateFlow<MapCategory> = _selectedMapCategory.asStateFlow()

    private val _selectedPin = MutableStateFlow<Any?>(null)
    val selectedPin: StateFlow<Any?> = _selectedPin.asStateFlow()

    private val _activeRoute = MutableStateFlow<RoutePlan?>(null)
    val activeRoute: StateFlow<RoutePlan?> = _activeRoute.asStateFlow()

    private val _isAccessibleRouteOnly = MutableStateFlow(false)
    val isAccessibleRouteOnly: StateFlow<Boolean> = _isAccessibleRouteOnly.asStateFlow()

    // Plan / My Day Mode
    private val _isMyDayLiveMode = MutableStateFlow(false)
    val isMyDayLiveMode: StateFlow<Boolean> = _isMyDayLiveMode.asStateFlow()

    // Detail Bottom Sheets / Dialogs
    private val _selectedAttraction = MutableStateFlow<Attraction?>(null)
    val selectedAttraction: StateFlow<Attraction?> = _selectedAttraction.asStateFlow()

    private val _selectedRestaurant = MutableStateFlow<Restaurant?>(null)
    val selectedRestaurant: StateFlow<Restaurant?> = _selectedRestaurant.asStateFlow()

    private val _selectedHotel = MutableStateFlow<Hotel?>(null)
    val selectedHotel: StateFlow<Hotel?> = _selectedHotel.asStateFlow()

    // Mobile Order Cart
    private val _cartItems = MutableStateFlow<List<MobileCartItem>>(emptyList())
    val cartItems: StateFlow<List<MobileCartItem>> = _cartItems.asStateFlow()

    private val _showCartSheet = MutableStateFlow(false)
    val showCartSheet: StateFlow<Boolean> = _showCartSheet.asStateFlow()

    // Toast / Feedback message
    private val _feedbackMessage = MutableStateFlow<String?>(null)
    val feedbackMessage: StateFlow<String?> = _feedbackMessage.asStateFlow()

    init {
        // Start background simulated live updates if enabled
        viewModelScope.launch {
            while (true) {
                delay(12000)
                if (isLiveSimulationActive.value && !isOfflineMode.value) {
                    simulateWaitTimeFluctuations()
                }
            }
        }
    }

    private fun simulateWaitTimeFluctuations() {
        val currentAttrs = attractions.value
        if (currentAttrs.isNotEmpty()) {
            val randomAttr = currentAttrs.random()
            val delta = (-5..5).random()
            val newWait = (randomAttr.waitTimeMinutes + delta).coerceIn(5, 95)
            repository.updateWaitTime(randomAttr.id, newWait)
        }
    }

    fun selectTab(tab: DisneyNavTab) {
        _currentTab.value = tab
    }

    fun setQuery(q: String) {
        _searchQuery.value = q
    }

    fun selectDestination(destId: String) {
        repository.selectDestination(destId)
        _selectedAttraction.value = null
        _selectedRestaurant.value = null
        _activeRoute.value = null
        _feedbackMessage.value = "Switched to ${activeDestination.value.name}"
    }

    fun selectPark(parkId: String) {
        repository.selectPark(parkId)
        _selectedLandFilter.value = null
        _selectedAttraction.value = null
        _selectedRestaurant.value = null
        _activeRoute.value = null
        _feedbackMessage.value = "Viewing ${activePark.value.name}"
    }

    fun setLandFilter(landId: String?) {
        _selectedLandFilter.value = landId
    }

    fun setThrillFilter(thrill: ThrillLevel?) {
        _selectedThrillFilter.value = thrill
    }

    fun toggleLightningLaneOnly() {
        _onlyLightningLane.value = !_onlyLightningLane.value
    }

    fun toggleAccessibleOnly() {
        _onlyAccessible.value = !_onlyAccessible.value
    }

    fun setSortOption(sort: String) {
        _sortOption.value = sort
    }

    fun setMapCategory(category: MapCategory) {
        _selectedMapCategory.value = category
    }

    fun selectMapPin(pin: Any?) {
        _selectedPin.value = pin
    }

    fun toggleMyDayMode() {
        _isMyDayLiveMode.value = !_isMyDayLiveMode.value
    }

    fun openAttractionDetail(attraction: Attraction) {
        _selectedAttraction.value = attraction
    }

    fun closeAttractionDetail() {
        _selectedAttraction.value = null
    }

    fun openRestaurantDetail(restaurant: Restaurant) {
        _selectedRestaurant.value = restaurant
    }

    fun closeRestaurantDetail() {
        _selectedRestaurant.value = null
    }

    fun openHotelDetail(hotel: Hotel) {
        _selectedHotel.value = hotel
    }

    fun closeHotelDetail() {
        _selectedHotel.value = null
    }

    fun clearFeedback() {
        _feedbackMessage.value = null
    }

    fun calculateRoute(fromName: String, toName: String) {
        val walkTime = (3..9).random()
        val dist = walkTime * 85
        _activeRoute.value = RoutePlan(
            fromName = fromName,
            toName = toName,
            distanceMeters = dist,
            walkTimeMinutes = walkTime,
            steps = listOf(
                "Head towards Central Plaza Hub (2 min)",
                "Follow path towards ${toName} marquee entrance",
                "Arrive at destination (${walkTime} min total walk)"
            ),
            isAccessible = _isAccessibleRouteOnly.value
        )
        _currentTab.value = DisneyNavTab.MAP
    }

    fun toggleAccessibleRouteOnly() {
        _isAccessibleRouteOnly.value = !_isAccessibleRouteOnly.value
        val cur = _activeRoute.value
        if (cur != null) {
            _activeRoute.value = cur.copy(isAccessible = _isAccessibleRouteOnly.value)
        }
    }

    fun clearRoute() {
        _activeRoute.value = null
    }

    fun bookLightningLane(attraction: Attraction) {
        val success = repository.bookLightningLane(attraction.id)
        if (success) {
            _feedbackMessage.value = "Lightning Lane booked for ${attraction.name}! ✨"
            _selectedAttraction.value = null
        }
    }

    fun addAttractionToItinerary(attraction: Attraction) {
        val item = ItineraryItem(
            id = "itin_attr_${System.currentTimeMillis()}",
            time = "03:45 PM",
            title = attraction.name,
            subtitle = "Standby Queue (~${attraction.waitTimeMinutes}m wait)",
            category = ItineraryCategory.ATTRACTION,
            location = attraction.landName,
            durationMinutes = attraction.durationMinutes + attraction.waitTimeMinutes,
            walkTimeMinutesFromPrev = 6,
            linkedAttractionId = attraction.id
        )
        repository.addItineraryItem(item)
        _feedbackMessage.value = "Added ${attraction.name} to Today's Plan"
        _selectedAttraction.value = null
    }

    fun addCustomItineraryItem(item: ItineraryItem) {
        repository.addItineraryItem(item)
        _feedbackMessage.value = "Added ${item.title} to Today's Plan"
    }

    fun addEventToItinerary(event: EntertainmentEvent, time: String) {
        val item = ItineraryItem(
            id = "itin_ent_${System.currentTimeMillis()}",
            time = time,
            title = event.name,
            subtitle = event.locationDescription,
            category = if (event.isNighttimeSpectacular) ItineraryCategory.FIREWORKS else ItineraryCategory.SHOW,
            location = event.locationDescription,
            durationMinutes = event.durationMinutes,
            walkTimeMinutesFromPrev = 5
        )
        repository.addItineraryItem(item)
        _feedbackMessage.value = "Added ${event.name} ($time) to Plan"
    }

    fun toggleItineraryComplete(itemId: String) {
        repository.toggleItineraryComplete(itemId)
    }

    fun removeItineraryItem(itemId: String) {
        repository.removeItineraryItem(itemId)
        _feedbackMessage.value = "Removed item from Plan"
    }

    fun bookDiningReservation(restaurant: Restaurant, time: String, partySize: Int) {
        repository.bookDiningReservation(restaurant.id, time, partySize)
        _feedbackMessage.value = "Table booked at ${restaurant.name} for $time!"
        _selectedRestaurant.value = null
    }

    fun addToMobileCart(menuItem: MenuItem) {
        val existing = _cartItems.value.find { it.menuItem.id == menuItem.id }
        if (existing != null) {
            _cartItems.value = _cartItems.value.map {
                if (it.menuItem.id == menuItem.id) it.copy(quantity = it.quantity + 1) else it
            }
        } else {
            _cartItems.value = _cartItems.value + MobileCartItem(menuItem, 1)
        }
        _feedbackMessage.value = "Added ${menuItem.name} to Mobile Order"
    }

    fun removeFromMobileCart(menuItemId: String) {
        _cartItems.value = _cartItems.value.mapNotNull {
            if (it.menuItem.id == menuItemId) {
                if (it.quantity > 1) it.copy(quantity = it.quantity - 1) else null
            } else it
        }
    }

    fun openCart() {
        _showCartSheet.value = true
    }

    fun closeCart() {
        _showCartSheet.value = false
    }

    fun checkoutMobileOrder(restaurantId: String) {
        val items = _cartItems.value.flatMap { item -> List(item.quantity) { item.menuItem } }
        val orderNo = repository.placeMobileOrder(restaurantId, items)
        _cartItems.value = emptyList()
        _showCartSheet.value = false
        _selectedRestaurant.value = null
        _feedbackMessage.value = "Mobile Order $orderNo Placed Successfully!"
    }

    fun markNotificationRead(id: String) {
        repository.markNotificationRead(id)
    }

    fun dismissNotification(id: String) {
        repository.dismissNotification(id)
    }

    fun toggleLiveSimulation() {
        repository.toggleLiveSimulation()
        val state = if (isLiveSimulationActive.value) "Active" else "Paused"
        _feedbackMessage.value = "Live Telemetry $state"
    }

    fun toggleOfflineMode() {
        repository.toggleOfflineMode()
        val state = if (isOfflineMode.value) "Offline Mode Active (Cached data)" else "Connected Live"
        _feedbackMessage.value = state
    }

    fun updateMagicBandColor(ticketId: String, colorHex: Long) {
        repository.updateMagicBandColor(ticketId, colorHex)
        _feedbackMessage.value = "MagicBand+ Color Updated! ✨"
    }

    fun unlockDigitalKey() {
        repository.unlockDigitalKey()
        _feedbackMessage.value = "Digital Key Activated! Room 4218 Unlocked."
    }

    // Smart Recommendations: Generates next 90 minutes recommendations
    val smartRecommendations: StateFlow<List<Recommendation>> = combine(
        attractions,
        restaurants,
        itinerary
    ) { attrs, rests, itin ->
        val operating = attrs.filter { it.status == AttractionStatus.OPERATING }
        val shortestWait = operating.minByOrNull { it.waitTimeMinutes }
        val popular = operating.find { it.hasLightningLane && it.thrillLevel == ThrillLevel.HIGH } ?: operating.firstOrNull()
        val diningRec = rests.firstOrNull { it.allowsMobileOrder }

        val list = mutableListOf<Recommendation>()
        if (shortestWait != null) {
            list.add(
                Recommendation(
                    id = "rec_short",
                    title = shortestWait.name,
                    subtitle = "${shortestWait.waitTimeMinutes} min wait · ${shortestWait.landName}",
                    reason = "Shortest wait nearby right now",
                    waitMinutes = shortestWait.waitTimeMinutes,
                    walkMinutes = 4,
                    targetType = "attraction",
                    targetId = shortestWait.id
                )
            )
        }
        if (popular != null && popular.id != shortestWait?.id) {
            list.add(
                Recommendation(
                    id = "rec_popular",
                    title = popular.name,
                    subtitle = "${popular.waitTimeMinutes} min wait · Lightning Lane Available",
                    reason = "Top rated highlight of your day",
                    waitMinutes = popular.waitTimeMinutes,
                    walkMinutes = 6,
                    targetType = "attraction",
                    targetId = popular.id
                )
            )
        }
        if (diningRec != null) {
            list.add(
                Recommendation(
                    id = "rec_dining",
                    title = diningRec.name,
                    subtitle = "Quick Service · Mobile Order Ready in 5m",
                    reason = "Ideal lunch window before 1:00 PM crowds",
                    waitMinutes = 0,
                    walkMinutes = 5,
                    targetType = "dining",
                    targetId = diningRec.id
                )
            )
        }
        list
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val filterCriteria = combine(
        searchQuery,
        selectedLandFilter,
        selectedThrillFilter,
        onlyLightningLane,
        onlyAccessible
    ) { q, land, thrill, ll, acc ->
        FilterCriteria(q, land, thrill, ll, acc, sortOption.value)
    }.combine(sortOption) { crit, sort ->
        crit.copy(sort = sort)
    }

    // Filtered attractions based on search, land, thrill, height, sorting
    val filteredAttractions: StateFlow<List<Attraction>> = combine(
        attractions,
        filterCriteria
    ) { attrs: List<Attraction>, criteria: FilterCriteria ->
        var list = attrs

        if (criteria.query.isNotBlank()) {
            val q = criteria.query.trim().lowercase()
            list = list.filter {
                it.name.lowercase().contains(q) ||
                it.landName.lowercase().contains(q) ||
                it.description.lowercase().contains(q) ||
                it.thrillLevel.label.lowercase().contains(q)
            }
        }

        if (criteria.land != null) {
            list = list.filter { it.landId == criteria.land }
        }

        if (criteria.thrill != null) {
            list = list.filter { it.thrillLevel == criteria.thrill }
        }

        if (criteria.llOnly) {
            list = list.filter { it.hasLightningLane }
        }

        if (criteria.accOnly) {
            list = list.filter { it.accessibilityWheelchair }
        }

        when (criteria.sort) {
            "Shortest Wait" -> list.sortedBy { it.waitTimeMinutes }
            "Longest Wait" -> list.sortedByDescending { it.waitTimeMinutes }
            "Name (A–Z)" -> list.sortedBy { it.name }
            "Thrill Level" -> list.sortedByDescending { it.thrillLevel.ordinal }
            else -> list
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Universal Search Results
    val universalSearchResults: StateFlow<UniversalSearchResults> = combine(
        searchQuery,
        attractions,
        restaurants,
        entertainment,
        activeDestination
    ) { query, attrs, rests, ents, dest ->
        if (query.isBlank()) {
            return@combine UniversalSearchResults(emptyList(), emptyList(), emptyList(), emptyList(), emptyList())
        }
        val q = query.trim().lowercase()

        val matchedAttrs = attrs.filter { it.name.lowercase().contains(q) || it.landName.lowercase().contains(q) || it.description.lowercase().contains(q) }
        val matchedRests = rests.filter { it.name.lowercase().contains(q) || it.cuisine.lowercase().contains(q) || it.landName.lowercase().contains(q) || it.menuItems.any { m -> m.name.lowercase().contains(q) } }
        val matchedEnts = ents.filter { it.name.lowercase().contains(q) || it.category.label.lowercase().contains(q) || it.locationDescription.lowercase().contains(q) }
        val matchedHotels = dest.hotels.filter { it.name.lowercase().contains(q) || it.tier.lowercase().contains(q) }
        val matchedRoutes = dest.transportRoutes.filter { it.name.lowercase().contains(q) || it.mode.label.lowercase().contains(q) || it.fromStop.lowercase().contains(q) || it.toStop.lowercase().contains(q) }

        UniversalSearchResults(matchedAttrs, matchedRests, matchedEnts, matchedHotels, matchedRoutes)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UniversalSearchResults(emptyList(), emptyList(), emptyList(), emptyList(), emptyList()))
}

data class UniversalSearchResults(
    val attractions: List<Attraction>,
    val restaurants: List<Restaurant>,
    val entertainment: List<EntertainmentEvent>,
    val hotels: List<Hotel>,
    val transportRoutes: List<TransportationRoute>
)
