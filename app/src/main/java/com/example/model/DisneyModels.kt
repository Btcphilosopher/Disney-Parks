package com.example.model

enum class ParkOperationalStatus(val label: String, val badgeColorHex: Long) {
    NORMAL("Most attractions operating normally", 0xFF108A58),
    MINOR_DELAYS("Several attractions experiencing delays", 0xFFD97706),
    DISRUPTED("Major operational disruption", 0xFFDC2626)
}

enum class AttractionStatus(val label: String, val colorHex: Long) {
    OPERATING("Operating", 0xFF108A58),
    TEMPORARILY_CLOSED("Temporarily Closed", 0xFFDC2626),
    DELAYED("Delayed", 0xFFD97706),
    REFURBISHMENT("Refurbishment", 0xFF64748B)
}

enum class ThrillLevel(val label: String) {
    ALL_AGES("All Ages"),
    MILD("Mild"),
    MODERATE("Moderate"),
    HIGH("High Thrill"),
    EXTREME("Extreme Thrill")
}

enum class ServiceType(val label: String) {
    QUICK_SERVICE("Quick Service"),
    TABLE_SERVICE("Table Service"),
    FINE_DINING("Fine / Signature Dining"),
    LOUNGE("Lounge & Bar"),
    SNACK_CART("Snacks & Treats")
}

enum class EntertainmentCategory(val label: String) {
    PARADE("Parade"),
    FIREWORKS("Fireworks & Nighttime Spectacular"),
    STAGE_SHOW("Stage Show"),
    CHARACTER_EXPERIENCE("Character Meet & Greet"),
    LIVE_MUSIC("Atmosphere & Live Music")
}

enum class TransportMode(val label: String) {
    MONORAIL("Monorail"),
    SKYLINER("Disney Skyliner"),
    FERRY_BOAT("Water Ferry"),
    RESORT_BUS("Resort Bus"),
    WALKING_TRAIL("Walking Trail")
}

enum class TicketType(val label: String) {
    ANNUAL_PASS("Magic Key / Annual Pass"),
    PARK_HOPPER("1-Day Park Hopper"),
    MULTI_DAY("4-Day Theme Park Ticket"),
    SPECIAL_EVENT("After Hours Special Ticket")
}

enum class ItineraryCategory {
    ATTRACTION,
    DINING,
    SHOW,
    BREAK,
    TRANSIT,
    HOTEL,
    FIREWORKS
}

enum class NotificationPriority {
    CRITICAL,
    IMPORTANT,
    USEFUL,
    OPTIONAL
}

enum class MapCategory(val label: String) {
    ALL("All"),
    ATTRACTIONS("Attractions"),
    DINING("Dining"),
    CHARACTERS("Characters"),
    RESTROOMS("Toilets"),
    TRANSPORT("Transport"),
    FIRST_AID("First Aid"),
    SHOPS("Shops")
}

data class WeatherData(
    val temperatureC: Int,
    val condition: String,
    val precipitationChance: Int,
    val uvIndex: Int,
    val sunsetTime: String,
    val alertMessage: String? = null
)

data class Destination(
    val id: String,
    val name: String,
    val shortName: String,
    val location: String,
    val country: String,
    val currencySymbol: String,
    val timezone: String,
    val weather: WeatherData,
    val parks: List<Park>,
    val hotels: List<Hotel>,
    val transportRoutes: List<TransportationRoute>,
    val bannerDrawableId: Int? = null
)

data class Park(
    val id: String,
    val destinationId: String,
    val name: String,
    val tagLine: String,
    val openHours: String,
    val operationalStatus: ParkOperationalStatus,
    val averageWaitMinutes: Int,
    val lands: List<Land>,
    val bannerDrawableId: Int? = null,
    val centerMapX: Float = 0.5f,
    val centerMapY: Float = 0.5f
)

data class Land(
    val id: String,
    val parkId: String,
    val name: String,
    val description: String,
    val colorHex: Long = 0xFF1A4B8C
)

data class Attraction(
    val id: String,
    val parkId: String,
    val landId: String,
    val landName: String,
    val name: String,
    val description: String,
    val waitTimeMinutes: Int,
    val status: AttractionStatus,
    val hasLightningLane: Boolean,
    val lightningLaneReturnTime: String? = null,
    val singleRiderAvailable: Boolean = false,
    val heightRequirementCm: Int? = null, // null = any height
    val thrillLevel: ThrillLevel,
    val durationMinutes: Int,
    val isIndoor: Boolean,
    val accessibilityWheelchair: Boolean = true,
    val accessibilityAudioDescription: Boolean = true,
    val accessibilityRiderSwitch: Boolean = true,
    val locationX: Float, // normalized 0f..1f on map
    val locationY: Float,
    val imageResId: Int? = null
)

data class MenuItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val isPlantBased: Boolean = false,
    val isGlutenFreeFriendly: Boolean = false,
    val isKidFriendly: Boolean = true,
    val isChefSpecial: Boolean = false
)

data class Restaurant(
    val id: String,
    val parkId: String,
    val landId: String,
    val landName: String,
    val name: String,
    val cuisine: String,
    val priceTier: String, // "$", "$$", "$$$", "$$$$"
    val serviceType: ServiceType,
    val allowsMobileOrder: Boolean,
    val openingHours: String,
    val rating: Double,
    val dietaryOptions: List<String>,
    val menuItems: List<MenuItem>,
    val availableReservationSlots: List<String>,
    val locationX: Float,
    val locationY: Float,
    val imageResId: Int? = null
)

data class EntertainmentEvent(
    val id: String,
    val parkId: String,
    val landId: String,
    val landName: String,
    val name: String,
    val category: EntertainmentCategory,
    val showtimes: List<String>,
    val nextShowtime: String,
    val durationMinutes: Int,
    val locationDescription: String,
    val description: String,
    val isNighttimeSpectacular: Boolean = false,
    val locationX: Float,
    val locationY: Float,
    val imageResId: Int? = null
)

data class Hotel(
    val id: String,
    val destinationId: String,
    val name: String,
    val tier: String,
    val description: String,
    val checkInTime: String = "3:00 PM",
    val checkOutTime: String = "11:00 AM",
    val amenities: List<String>,
    val diningVenues: List<String>,
    val transportConnections: List<String>,
    val poolHours: String = "8:00 AM – 10:00 PM",
    val bannerDrawableId: Int? = null
)

data class RoomReservation(
    val id: String,
    val hotelId: String,
    val hotelName: String,
    val roomTypeName: String,
    val checkInDate: String,
    val checkOutDate: String,
    val roomNumber: String,
    val guestName: String,
    val digitalKeyUnlocked: Boolean = false
)

data class TransportationRoute(
    val id: String,
    val destinationId: String,
    val name: String,
    val mode: TransportMode,
    val fromStop: String,
    val toStop: String,
    val durationMinutes: Int,
    val frequencyMinutes: Int,
    val nextDepartureMinutes: Int,
    val operatingStatus: String = "Running every 4–6 mins",
    val description: String
)

data class Ticket(
    val id: String,
    val title: String,
    val type: TicketType,
    val holderName: String,
    val validFrom: String,
    val validTo: String,
    val parksAllowedSummary: String,
    val hasParkHopper: Boolean,
    val hasLightningLanePass: Boolean,
    val qrCodeData: String,
    val barcodeData: String,
    val magicBandColorHex: Long = 0xFF1A4B8C,
    val status: String = "Valid Today"
)

data class ItineraryItem(
    val id: String,
    val time: String,
    val title: String,
    val subtitle: String,
    val category: ItineraryCategory,
    val location: String,
    val durationMinutes: Int,
    val walkTimeMinutesFromPrev: Int = 5,
    val isCompleted: Boolean = false,
    val note: String? = null,
    val linkedAttractionId: String? = null,
    val linkedRestaurantId: String? = null
)

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val priority: NotificationPriority,
    val read: Boolean = false,
    val actionLabel: String? = null,
    val targetType: String? = null,
    val targetId: String? = null
)

data class FamilyMember(
    val id: String,
    val name: String,
    val role: String,
    val avatarColorHex: Long,
    val isManaged: Boolean,
    val magicBandColorHex: Long,
    val currentLocation: String
)

data class Recommendation(
    val id: String,
    val title: String,
    val subtitle: String,
    val reason: String,
    val waitMinutes: Int,
    val walkMinutes: Int,
    val targetType: String, // "attraction", "dining", "show"
    val targetId: String
)

data class MapFacility(
    val id: String,
    val parkId: String,
    val name: String,
    val category: MapCategory,
    val landName: String,
    val x: Float,
    val y: Float,
    val extraInfo: String
)
