package com.example.service

import com.example.data.MockDisneyDataFactory
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface DisneyParkService {
    val destinations: StateFlow<List<Destination>>
    val activeDestination: StateFlow<Destination>
    val activePark: StateFlow<Park>
    val attractions: StateFlow<List<Attraction>>
    val restaurants: StateFlow<List<Restaurant>>
    val entertainment: StateFlow<List<EntertainmentEvent>>
    val facilities: StateFlow<List<MapFacility>>
    val tickets: StateFlow<List<Ticket>>
    val itinerary: StateFlow<List<ItineraryItem>>
    val notifications: StateFlow<List<NotificationItem>>
    val hotelReservation: StateFlow<RoomReservation?>
    val familyMembers: StateFlow<List<FamilyMember>>
    val isLiveSimulationActive: StateFlow<Boolean>
    val isOfflineMode: StateFlow<Boolean>

    fun selectDestination(destId: String)
    fun selectPark(parkId: String)
    fun updateWaitTime(attractionId: String, newMinutes: Int)
    fun bookLightningLane(attractionId: String): Boolean
    fun toggleItineraryComplete(itemId: String)
    fun addItineraryItem(item: ItineraryItem)
    fun removeItineraryItem(itemId: String)
    fun bookDiningReservation(restaurantId: String, time: String, partySize: Int): Boolean
    fun placeMobileOrder(restaurantId: String, items: List<MenuItem>): String
    fun markNotificationRead(notifId: String)
    fun dismissNotification(notifId: String)
    fun toggleLiveSimulation()
    fun toggleOfflineMode()
    fun updateMagicBandColor(ticketId: String, colorHex: Long)
    fun unlockDigitalKey(): Boolean
}

class DisneyRepositoryImpl : DisneyParkService {

    private val initialDestinations = MockDisneyDataFactory.getInitialDestinations()
    private val defaultDest = initialDestinations.first()
    private val defaultPark = defaultDest.parks.first()

    private val _destinations = MutableStateFlow(initialDestinations)
    override val destinations: StateFlow<List<Destination>> = _destinations.asStateFlow()

    private val _activeDestination = MutableStateFlow(defaultDest)
    override val activeDestination: StateFlow<Destination> = _activeDestination.asStateFlow()

    private val _activePark = MutableStateFlow(defaultPark)
    override val activePark: StateFlow<Park> = _activePark.asStateFlow()

    private val _attractions = MutableStateFlow(MockDisneyDataFactory.getInitialAttractions(defaultPark.id))
    override val attractions: StateFlow<List<Attraction>> = _attractions.asStateFlow()

    private val _restaurants = MutableStateFlow(MockDisneyDataFactory.getInitialRestaurants(defaultPark.id))
    override val restaurants: StateFlow<List<Restaurant>> = _restaurants.asStateFlow()

    private val _entertainment = MutableStateFlow(MockDisneyDataFactory.getInitialEntertainment(defaultPark.id))
    override val entertainment: StateFlow<List<EntertainmentEvent>> = _entertainment.asStateFlow()

    private val _facilities = MutableStateFlow(MockDisneyDataFactory.getInitialFacilities(defaultPark.id))
    override val facilities: StateFlow<List<MapFacility>> = _facilities.asStateFlow()

    private val _tickets = MutableStateFlow(MockDisneyDataFactory.getInitialTickets())
    override val tickets: StateFlow<List<Ticket>> = _tickets.asStateFlow()

    private val _itinerary = MutableStateFlow(MockDisneyDataFactory.getInitialItinerary())
    override val itinerary: StateFlow<List<ItineraryItem>> = _itinerary.asStateFlow()

    private val _notifications = MutableStateFlow(MockDisneyDataFactory.getInitialNotifications())
    override val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val _hotelReservation = MutableStateFlow<RoomReservation?>(MockDisneyDataFactory.getInitialRoomReservation())
    override val hotelReservation: StateFlow<RoomReservation?> = _hotelReservation.asStateFlow()

    private val _familyMembers = MutableStateFlow(MockDisneyDataFactory.getInitialFamilyMembers())
    override val familyMembers: StateFlow<List<FamilyMember>> = _familyMembers.asStateFlow()

    private val _isLiveSimulationActive = MutableStateFlow(true)
    override val isLiveSimulationActive: StateFlow<Boolean> = _isLiveSimulationActive.asStateFlow()

    private val _isOfflineMode = MutableStateFlow(false)
    override val isOfflineMode: StateFlow<Boolean> = _isOfflineMode.asStateFlow()

    override fun selectDestination(destId: String) {
        val dest = _destinations.value.find { it.id == destId } ?: return
        _activeDestination.value = dest
        val firstPark = dest.parks.firstOrNull()
        if (firstPark != null) {
            selectPark(firstPark.id)
        }
    }

    override fun selectPark(parkId: String) {
        val allParks = _destinations.value.flatMap { it.parks }
        val park = allParks.find { it.id == parkId } ?: return
        _activePark.value = park
        _attractions.value = MockDisneyDataFactory.getInitialAttractions(parkId)
        _restaurants.value = MockDisneyDataFactory.getInitialRestaurants(parkId)
        _entertainment.value = MockDisneyDataFactory.getInitialEntertainment(parkId)
        _facilities.value = MockDisneyDataFactory.getInitialFacilities(parkId)
    }

    override fun updateWaitTime(attractionId: String, newMinutes: Int) {
        _attractions.update { list ->
            list.map { if (it.id == attractionId) it.copy(waitTimeMinutes = newMinutes) else it }
        }
    }

    override fun bookLightningLane(attractionId: String): Boolean {
        val attr = _attractions.value.find { it.id == attractionId } ?: return false
        val timeSlot = attr.lightningLaneReturnTime ?: "1:30 PM"

        // Add to itinerary
        val newItem = ItineraryItem(
            id = "itin_ll_${System.currentTimeMillis()}",
            time = timeSlot,
            title = "${attr.name} (Lightning Lane)",
            subtitle = "Return Window: $timeSlot – Valid for 1 Hour",
            category = ItineraryCategory.ATTRACTION,
            location = attr.landName,
            durationMinutes = 20,
            walkTimeMinutesFromPrev = 5,
            linkedAttractionId = attr.id
        )
        addItineraryItem(newItem)

        // Add notification
        val notif = NotificationItem(
            id = "notif_${System.currentTimeMillis()}",
            title = "Lightning Lane Confirmed! ✨",
            message = "Your pass for ${attr.name} is booked for $timeSlot.",
            timestamp = "Just now",
            priority = NotificationPriority.IMPORTANT,
            read = false,
            actionLabel = "View Pass",
            targetType = "attraction",
            targetId = attr.id
        )
        _notifications.update { listOf(notif) + it }
        return true
    }

    override fun toggleItineraryComplete(itemId: String) {
        _itinerary.update { list ->
            list.map { if (it.id == itemId) it.copy(isCompleted = !it.isCompleted) else it }
        }
    }

    override fun addItineraryItem(item: ItineraryItem) {
        _itinerary.update { list ->
            // Insert and keep sorted roughly by time or append
            val mutable = list.toMutableList()
            mutable.add(item)
            mutable
        }
    }

    override fun removeItineraryItem(itemId: String) {
        _itinerary.update { list -> list.filterNot { it.id == itemId } }
    }

    override fun bookDiningReservation(restaurantId: String, time: String, partySize: Int): Boolean {
        val rest = _restaurants.value.find { it.id == restaurantId } ?: return false
        val newItem = ItineraryItem(
            id = "itin_dining_${System.currentTimeMillis()}",
            time = time,
            title = "Dinner at ${rest.name}",
            subtitle = "Reservation Confirmed for Party of $partySize",
            category = ItineraryCategory.DINING,
            location = "${rest.landName} · ${rest.cuisine}",
            durationMinutes = 75,
            linkedRestaurantId = rest.id
        )
        addItineraryItem(newItem)

        val notif = NotificationItem(
            id = "notif_din_${System.currentTimeMillis()}",
            title = "Dining Reservation Confirmed 🍽️",
            message = "Table for $partySize reserved at ${rest.name} for $time.",
            timestamp = "Just now",
            priority = NotificationPriority.IMPORTANT,
            read = false,
            actionLabel = "View Plan",
            targetType = "dining",
            targetId = rest.id
        )
        _notifications.update { listOf(notif) + it }
        return true
    }

    override fun placeMobileOrder(restaurantId: String, items: List<MenuItem>): String {
        val rest = _restaurants.value.find { it.id == restaurantId }
        val orderNumber = "MO-${(1000..9999).random()}"
        val total = items.sumOf { it.price }

        val notif = NotificationItem(
            id = "notif_mo_${System.currentTimeMillis()}",
            title = "Mobile Order #$orderNumber Placed",
            message = "Your order at ${rest?.name ?: "Disney Dining"} ($${String.format("%.2f", total)}) is being prepared.",
            timestamp = "Just now",
            priority = NotificationPriority.CRITICAL,
            read = false,
            actionLabel = "Pick Up",
            targetType = "dining",
            targetId = restaurantId
        )
        _notifications.update { listOf(notif) + it }
        return orderNumber
    }

    override fun markNotificationRead(notifId: String) {
        _notifications.update { list ->
            list.map { if (it.id == notifId) it.copy(read = true) else it }
        }
    }

    override fun dismissNotification(notifId: String) {
        _notifications.update { list -> list.filterNot { it.id == notifId } }
    }

    override fun toggleLiveSimulation() {
        _isLiveSimulationActive.update { !it }
    }

    override fun toggleOfflineMode() {
        _isOfflineMode.update { !it }
    }

    override fun updateMagicBandColor(ticketId: String, colorHex: Long) {
        _tickets.update { list ->
            list.map { if (it.id == ticketId) it.copy(magicBandColorHex = colorHex) else it }
        }
    }

    override fun unlockDigitalKey(): Boolean {
        _hotelReservation.update { res ->
            res?.copy(digitalKeyUnlocked = true)
        }
        return true
    }
}
