package com.example.data

import com.example.R
import com.example.model.*

object MockDisneyDataFactory {

    fun getInitialDestinations(): List<Destination> {
        val wdwCastleBanner = R.drawable.banner_disney_castle_1787129476105
        val spaceMountainBanner = R.drawable.banner_space_mountain_1787129513335
        val grandFloridianBanner = R.drawable.banner_grand_floridian_1787129542792

        // Magic Kingdom Lands
        val mkLands = listOf(
            Land("land_mk_mainstreet", "park_mk", "Main Street, U.S.A.", "Turn-of-the-century American nostalgia", 0xFFDF9E27),
            Land("land_mk_tomorrowland", "park_mk", "Tomorrowland", "A galactic vision of the future", 0xFF2C74D8),
            Land("land_mk_fantasyland", "park_mk", "Fantasyland", "Timeless fairy tales and enchanted realms", 0xFF703DA6),
            Land("land_mk_adventureland", "park_mk", "Adventureland", "Exotic jungles, pirate coves, and tropical waters", 0xFF108A58),
            Land("land_mk_frontierland", "park_mk", "Frontierland", "The spirit of the legendary American West", 0xFFD97706),
            Land("land_mk_liberty", "park_mk", "Liberty Square", "Colonial America and revolutionary heritage", 0xFFB91C1C)
        )

        val mkAttractions = listOf(
            Attraction(
                id = "attr_space_mountain",
                parkId = "park_mk",
                landId = "land_mk_tomorrowland",
                landName = "Tomorrowland",
                name = "Space Mountain",
                description = "Blast off on a thrilling, high-speed rollercoaster journey through the dark depths of outer space.",
                waitTimeMinutes = 25,
                status = AttractionStatus.OPERATING,
                hasLightningLane = true,
                lightningLaneReturnTime = "11:15 AM",
                singleRiderAvailable = true,
                heightRequirementCm = 112,
                thrillLevel = ThrillLevel.HIGH,
                durationMinutes = 3,
                isIndoor = true,
                accessibilityWheelchair = true,
                accessibilityAudioDescription = true,
                accessibilityRiderSwitch = true,
                locationX = 0.82f,
                locationY = 0.38f,
                imageResId = spaceMountainBanner
            ),
            Attraction(
                id = "attr_seven_dwarfs",
                parkId = "park_mk",
                landId = "land_mk_fantasyland",
                landName = "Fantasyland",
                name = "Seven Dwarfs Mine Train",
                description = "Race through the diamond mine on a swinging mine cart coaster with Snow White and friends.",
                waitTimeMinutes = 45,
                status = AttractionStatus.OPERATING,
                hasLightningLane = true,
                lightningLaneReturnTime = "1:30 PM",
                singleRiderAvailable = false,
                heightRequirementCm = 97,
                thrillLevel = ThrillLevel.MODERATE,
                durationMinutes = 3,
                isIndoor = false,
                locationX = 0.58f,
                locationY = 0.28f,
                imageResId = wdwCastleBanner
            ),
            Attraction(
                id = "attr_peter_pan",
                parkId = "park_mk",
                landId = "land_mk_fantasyland",
                landName = "Fantasyland",
                name = "Peter Pan's Flight",
                description = "Fly over London in an enchanted pirate galleon all the way to Neverland.",
                waitTimeMinutes = 35,
                status = AttractionStatus.OPERATING,
                hasLightningLane = true,
                lightningLaneReturnTime = "12:00 PM",
                singleRiderAvailable = false,
                heightRequirementCm = null,
                thrillLevel = ThrillLevel.MILD,
                durationMinutes = 3,
                isIndoor = true,
                locationX = 0.44f,
                locationY = 0.32f
            ),
            Attraction(
                id = "attr_pirates",
                parkId = "park_mk",
                landId = "land_mk_adventureland",
                landName = "Adventureland",
                name = "Pirates of the Caribbean",
                description = "Sail on a swashbuckling voyage through uncharted waters amidst roguish pirates and cannons.",
                waitTimeMinutes = 20,
                status = AttractionStatus.OPERATING,
                hasLightningLane = true,
                lightningLaneReturnTime = "10:45 AM",
                singleRiderAvailable = false,
                heightRequirementCm = null,
                thrillLevel = ThrillLevel.MILD,
                durationMinutes = 9,
                isIndoor = true,
                locationX = 0.22f,
                locationY = 0.62f
            ),
            Attraction(
                id = "attr_haunted_mansion",
                parkId = "park_mk",
                landId = "land_mk_liberty",
                landName = "Liberty Square",
                name = "Haunted Mansion",
                description = "Climb aboard a Doom Buggy for a spirited tour of 999 happy haunts in a gothic manor.",
                waitTimeMinutes = 30,
                status = AttractionStatus.OPERATING,
                hasLightningLane = true,
                lightningLaneReturnTime = "12:45 PM",
                singleRiderAvailable = false,
                heightRequirementCm = null,
                thrillLevel = ThrillLevel.MILD,
                durationMinutes = 8,
                isIndoor = true,
                locationX = 0.32f,
                locationY = 0.36f
            ),
            Attraction(
                id = "attr_tron",
                parkId = "park_mk",
                landId = "land_mk_tomorrowland",
                landName = "Tomorrowland",
                name = "TRON Lightcycle / Run",
                description = "Sync into the Grid and race on two-wheeled Lightcycles in an epic digital showdown.",
                waitTimeMinutes = 60,
                status = AttractionStatus.OPERATING,
                hasLightningLane = true,
                lightningLaneReturnTime = "3:10 PM",
                singleRiderAvailable = true,
                heightRequirementCm = 122,
                thrillLevel = ThrillLevel.EXTREME,
                durationMinutes = 2,
                isIndoor = true,
                locationX = 0.88f,
                locationY = 0.26f,
                imageResId = spaceMountainBanner
            ),
            Attraction(
                id = "attr_big_thunder",
                parkId = "park_mk",
                landId = "land_mk_frontierland",
                landName = "Frontierland",
                name = "Big Thunder Mountain Railroad",
                description = "The wildest ride in the wilderness through runaway gold mine tunnels and caves.",
                waitTimeMinutes = 15,
                status = AttractionStatus.OPERATING,
                hasLightningLane = true,
                lightningLaneReturnTime = "11:00 AM",
                singleRiderAvailable = false,
                heightRequirementCm = 102,
                thrillLevel = ThrillLevel.MODERATE,
                durationMinutes = 4,
                isIndoor = false,
                locationX = 0.16f,
                locationY = 0.40f
            ),
            Attraction(
                id = "attr_small_world",
                parkId = "park_mk",
                landId = "land_mk_fantasyland",
                landName = "Fantasyland",
                name = "it's a small world",
                description = "Sing along on a whimsical water cruise celebrating global unity and joyful harmony.",
                waitTimeMinutes = 15,
                status = AttractionStatus.OPERATING,
                hasLightningLane = true,
                lightningLaneReturnTime = "10:30 AM",
                singleRiderAvailable = false,
                heightRequirementCm = null,
                thrillLevel = ThrillLevel.ALL_AGES,
                durationMinutes = 11,
                isIndoor = true,
                locationX = 0.40f,
                locationY = 0.22f
            ),
            Attraction(
                id = "attr_jungle_cruise",
                parkId = "park_mk",
                landId = "land_mk_adventureland",
                landName = "Adventureland",
                name = "Jungle Cruise",
                description = "Navigate dangerous waterways filled with wild animatronics and pun-loving skippers.",
                waitTimeMinutes = 40,
                status = AttractionStatus.OPERATING,
                hasLightningLane = true,
                lightningLaneReturnTime = "2:15 PM",
                singleRiderAvailable = false,
                heightRequirementCm = null,
                thrillLevel = ThrillLevel.ALL_AGES,
                durationMinutes = 10,
                isIndoor = false,
                locationX = 0.30f,
                locationY = 0.70f
            )
        )

        val mkRestaurants = listOf(
            Restaurant(
                id = "rest_be_our_guest",
                parkId = "park_mk",
                landId = "land_mk_fantasyland",
                landName = "Fantasyland",
                name = "Be Our Guest Restaurant",
                cuisine = "French-inspired Fine Dining",
                priceTier = "$$$$",
                serviceType = ServiceType.FINE_DINING,
                allowsMobileOrder = false,
                openingHours = "10:00 AM – 9:30 PM",
                rating = 4.8,
                dietaryOptions = listOf("Plant-Based", "Gluten-Free", "Nut-Free"),
                menuItems = listOf(
                    MenuItem("m1", "Filet Mignon", "Center-cut beef tenderloin, potato pavé, seasonal baby vegetables, red wine jus", 48.00, isGlutenFreeFriendly = true, isChefSpecial = true),
                    MenuItem("m2", "French Onion Soup", "Rich beef broth, toasted crouton, melted Gruyère cheese", 14.00),
                    MenuItem("m3", "The Master's Cupcake", "Chocolate sponge cake topped with the famous \"Grey Stuff\"", 8.50, isKidFriendly = true)
                ),
                availableReservationSlots = listOf("12:15 PM", "1:45 PM", "6:00 PM", "7:30 PM"),
                locationX = 0.52f,
                locationY = 0.20f,
                imageResId = wdwCastleBanner
            ),
            Restaurant(
                id = "rest_cosmic_rays",
                parkId = "park_mk",
                landId = "land_mk_tomorrowland",
                landName = "Tomorrowland",
                name = "Cosmic Ray's Starlight Café",
                cuisine = "American Classic & Burgers",
                priceTier = "$$",
                serviceType = ServiceType.QUICK_SERVICE,
                allowsMobileOrder = true,
                openingHours = "10:30 AM – 10:00 PM",
                rating = 4.4,
                dietaryOptions = listOf("Plant-Based Burgers", "Allergy Friendly Buns"),
                menuItems = listOf(
                    MenuItem("cr1", "Rocket Angus Cheeseburger", "1/3 lb angus patty, aged cheddar, toasted brioche, seasoned fries", 13.99),
                    MenuItem("cr2", "Plant-based Galaxy Wrap", "Crispy plant tenders, vegan ranch, crisp greens, whole grain tortilla", 12.49, isPlantBased = true),
                    MenuItem("cr3", "Sonny Eclipse Lunar Shake", "Cookies and cream hand-spun shake with starlight candy sprinkles", 6.99, isKidFriendly = true)
                ),
                availableReservationSlots = emptyList(),
                locationX = 0.74f,
                locationY = 0.44f,
                imageResId = spaceMountainBanner
            ),
            Restaurant(
                id = "rest_skippers_canteen",
                parkId = "park_mk",
                landId = "land_mk_adventureland",
                landName = "Adventureland",
                name = "Jungle Navigation Co. LTD Skipper Canteen",
                cuisine = "Bold Asian, South American & African Flavors",
                priceTier = "$$$",
                serviceType = ServiceType.TABLE_SERVICE,
                allowsMobileOrder = false,
                openingHours = "11:00 AM – 9:00 PM",
                rating = 4.7,
                dietaryOptions = listOf("Gluten-Free", "Vegetarian", "Dairy-Free"),
                menuItems = listOf(
                    MenuItem("sc1", "\"Tastes Like Chicken\" Because It Is!", "Crispy-fried chicken with chili glaze and spiced rice", 28.00),
                    MenuItem("sc2", "Curried Vegetable Stew", "Roasted squash, coconut curry broth, pineapple chutney, jasmine rice", 22.00, isPlantBased = true, isGlutenFreeFriendly = true),
                    MenuItem("sc3", "Kungaloosh Cake", "African chocolate cake topped with caramel and coffee dust", 9.50)
                ),
                availableReservationSlots = listOf("12:00 PM", "12:45 PM", "5:30 PM", "8:15 PM"),
                locationX = 0.34f,
                locationY = 0.66f
            ),
            Restaurant(
                id = "rest_aloha_isle",
                parkId = "park_mk",
                landId = "land_mk_adventureland",
                landName = "Adventureland",
                name = "Aloha Isle Refreshments",
                cuisine = "DOLE Whip® & Island Treats",
                priceTier = "$",
                serviceType = ServiceType.SNACK_CART,
                allowsMobileOrder = true,
                openingHours = "9:00 AM – 11:00 PM",
                rating = 4.9,
                dietaryOptions = listOf("Vegan", "Gluten-Free", "Dairy-Free"),
                menuItems = listOf(
                    MenuItem("ai1", "Original DOLE Whip® Soft Serve", "Iconic pineapple dairy-free soft serve swirl", 5.99, isPlantBased = true, isGlutenFreeFriendly = true),
                    MenuItem("ai2", "DOLE Whip® Pineapple Float", "Pineapple soft serve floating over 100% chilled Dole pineapple juice", 7.49, isPlantBased = true, isGlutenFreeFriendly = true)
                ),
                availableReservationSlots = emptyList(),
                locationX = 0.28f,
                locationY = 0.60f
            )
        )

        val mkEntertainment = listOf(
            EntertainmentEvent(
                id = "ent_fireworks_happily",
                parkId = "park_mk",
                landId = "land_mk_mainstreet",
                landName = "Main Street, U.S.A.",
                name = "Happily Ever After Fireworks Spectacular",
                category = EntertainmentCategory.FIREWORKS,
                showtimes = listOf("8:45 PM"),
                nextShowtime = "8:45 PM",
                durationMinutes = 18,
                locationDescription = "Cinderella Castle & Main Street Hub",
                description = "Transformative projection mapping, state-of-the-art pyrotechnics, and emotional Disney anthems illuminating the night sky.",
                isNighttimeSpectacular = true,
                locationX = 0.50f,
                locationY = 0.48f,
                imageResId = wdwCastleBanner
            ),
            EntertainmentEvent(
                id = "ent_parade_festival",
                parkId = "park_mk",
                landId = "land_mk_frontierland",
                landName = "Frontierland & Main Street",
                name = "Disney Festival of Fantasy Parade",
                category = EntertainmentCategory.PARADE,
                showtimes = listOf("12:00 PM", "3:00 PM"),
                nextShowtime = "3:00 PM",
                durationMinutes = 25,
                locationDescription = "Parade route from Frontierland to Town Square",
                description = "Towering vibrant floats featuring a fire-breathing Maleficent dragon, Peter Pan, Rapunzel, and beloved Disney characters.",
                isNighttimeSpectacular = false,
                locationX = 0.42f,
                locationY = 0.52f,
                imageResId = wdwCastleBanner
            ),
            EntertainmentEvent(
                id = "ent_mickey_meet",
                parkId = "park_mk",
                landId = "land_mk_mainstreet",
                landName = "Main Street, U.S.A.",
                name = "Meet Mickey Mouse at Town Square Theater",
                category = EntertainmentCategory.CHARACTER_EXPERIENCE,
                showtimes = listOf("Continuous 9:00 AM – 10:00 PM"),
                nextShowtime = "Available Now (15m wait)",
                durationMinutes = 5,
                locationDescription = "Town Square Theater, Right of Entrance",
                description = "Share a hug, snap a photo, and get an autograph from the main mouse himself in his Magician dressing room.",
                isNighttimeSpectacular = false,
                locationX = 0.54f,
                locationY = 0.88f
            )
        )

        val mkFacilities = listOf(
            MapFacility("fac_restroom_1", "park_mk", "Restrooms & Companion Care", MapCategory.RESTROOMS, "Fantasyland", 0.48f, 0.28f, "Companion Restroom & Baby Changing Station"),
            MapFacility("fac_restroom_2", "park_mk", "Tomorrowland Restrooms", MapCategory.RESTROOMS, "Tomorrowland", 0.78f, 0.46f, "Wheelchair accessible"),
            MapFacility("fac_firstaid", "park_mk", "Main Street First Aid & Care", MapCategory.FIRST_AID, "Main Street, U.S.A.", 0.56f, 0.76f, "Registered nurses, medication storage, first aid assistance"),
            MapFacility("fac_shop_emporium", "park_mk", "Emporium Merchandise Mega-Store", MapCategory.SHOPS, "Main Street, U.S.A.", 0.46f, 0.80f, "Apparel, pins, ears, magic bands, souvenirs"),
            MapFacility("fac_char_princess", "park_mk", "Princess Fairytale Hall", MapCategory.CHARACTERS, "Fantasyland", 0.54f, 0.34f, "Meet Cinderella, Elena, Tiana & Rapunzel")
        )

        val magicKingdom = Park(
            id = "park_mk",
            destinationId = "dest_wdw",
            name = "Magic Kingdom Park",
            tagLine = "The Most Magical Place on Earth",
            openHours = "Open until 11:00 PM (Early entry 8:30 AM)",
            operationalStatus = ParkOperationalStatus.NORMAL,
            averageWaitMinutes = 25,
            lands = mkLands,
            bannerDrawableId = wdwCastleBanner,
            centerMapX = 0.5f,
            centerMapY = 0.5f
        )

        val epcot = Park(
            id = "park_epcot",
            destinationId = "dest_wdw",
            name = "EPCOT",
            tagLine = "Celebrate human achievement, innovation & world cultures",
            openHours = "Open until 9:00 PM",
            operationalStatus = ParkOperationalStatus.NORMAL,
            averageWaitMinutes = 30,
            lands = listOf(
                Land("land_epcot_world_celebration", "park_epcot", "World Celebration", "Spaceship Earth and connections", 0xFF2C74D8),
                Land("land_epcot_world_discovery", "park_epcot", "World Discovery", "Cosmic adventures and science", 0xFFD84A75),
                Land("land_epcot_world_showcase", "park_epcot", "World Showcase", "11 international pavilions surrounding the lagoon", 0xFFDF9E27)
            ),
            bannerDrawableId = spaceMountainBanner
        )

        val hollywoodStudios = Park(
            id = "park_dhs",
            destinationId = "dest_wdw",
            name = "Disney's Hollywood Studios",
            tagLine = "Live your story in Star Wars: Galaxy's Edge & Toy Story Land",
            openHours = "Open until 9:00 PM",
            operationalStatus = ParkOperationalStatus.NORMAL,
            averageWaitMinutes = 38,
            lands = listOf(
                Land("land_dhs_galaxys_edge", "park_dhs", "Star Wars: Galaxy's Edge", "Batuu Black Spire Outpost", 0xFF108A58),
                Land("land_dhs_toy_story", "park_dhs", "Toy Story Land", "Andy's backyard shrunk down to toy size", 0xFFDF9E27),
                Land("land_dhs_sunset", "park_dhs", "Sunset Boulevard", "Hollywood Golden Age & Tower of Terror", 0xFF703DA6)
            ),
            bannerDrawableId = spaceMountainBanner
        )

        val animalKingdom = Park(
            id = "park_dak",
            destinationId = "dest_wdw",
            name = "Disney's Animal Kingdom",
            tagLine = "Celebrate the magic of nature and exotic realms",
            openHours = "Open until 7:00 PM",
            operationalStatus = ParkOperationalStatus.NORMAL,
            averageWaitMinutes = 20,
            lands = listOf(
                Land("land_dak_pandora", "park_dak", "Pandora – World of Avatar", "Bioluminescent rainforests and floating mountains", 0xFF2C74D8),
                Land("land_dak_africa", "park_dak", "Africa", "Harambe Village and Kilimanjaro Safaris", 0xFFD97706),
                Land("land_dak_asia", "park_dak", "Asia", "Expedition Everest and Anandapur", 0xFFB91C1C)
            ),
            bannerDrawableId = wdwCastleBanner
        )

        val wdwHotels = listOf(
            Hotel(
                id = "hotel_grand_floridian",
                destinationId = "dest_wdw",
                name = "Disney's Grand Floridian Resort & Spa",
                tier = "Deluxe Resort & Monorail Hub",
                description = "Victorian elegance meets Disney magic with sweeping lagoon views, live orchestra, and direct monorail access.",
                checkInTime = "3:00 PM",
                checkOutTime = "11:00 AM",
                amenities = listOf("Direct Monorail Access", "Beach Pool with Water Slide", "Grand Floridian Spa", "Marina Boat Rentals", "24-hr Health Club"),
                diningVenues = listOf("Victoria & Albert's", "Citricos", "Gasparilla Island Grill", "Narcoossee's"),
                transportConnections = listOf("Monorail to Magic Kingdom & EPCOT", "Water Taxi to Magic Kingdom", "Buses to all Parks"),
                poolHours = "8:00 AM – 11:00 PM",
                bannerDrawableId = grandFloridianBanner
            ),
            Hotel(
                id = "hotel_contemporary",
                destinationId = "dest_wdw",
                name = "Disney's Contemporary Resort",
                tier = "Deluxe Iconic Monorail Resort",
                description = "Ultra-modern landmark with the monorail gliding directly through the iconic Grand Canyon Concourse.",
                amenities = listOf("Monorail Inside Lobby", "Walking Path to Magic Kingdom (8 min)", "Bay Lake Marina", "Feature Pool & Cabanas"),
                diningVenues = listOf("California Grill (Rooftop Fireworks)", "Chef Mickey's Character Dining", "Contempo Café"),
                transportConnections = listOf("Resort Monorail", "Magic Kingdom Walkway", "Express Buses"),
                bannerDrawableId = grandFloridianBanner
            ),
            Hotel(
                id = "hotel_polynesian",
                destinationId = "dest_wdw",
                name = "Disney's Polynesian Village Resort",
                tier = "Deluxe Island Oasis",
                description = "South Pacific ambiance, tiki torches, overwater bungalows, and sandy shores on Seven Seas Lagoon.",
                amenities = listOf("Lava Pool with Volcano Slide", "Tiki Torch Lighting Ceremony", "Pineapple Lanai DOLE Whip"),
                diningVenues = listOf("‘Ohana Family Feast", "Trader Sam's Grog Grotto", "Kona Cafe"),
                transportConnections = listOf("Monorail", "Water Ferry", "Direct TTC walkway to EPCOT Monorail"),
                bannerDrawableId = grandFloridianBanner
            )
        )

        val wdwTransport = listOf(
            TransportationRoute(
                id = "trans_monorail_resort",
                destinationId = "dest_wdw",
                name = "Resort Monorail Loop",
                mode = TransportMode.MONORAIL,
                fromStop = "Disney's Grand Floridian Resort",
                toStop = "Magic Kingdom Park Entrance",
                durationMinutes = 6,
                frequencyMinutes = 5,
                nextDepartureMinutes = 3,
                operatingStatus = "Operating smoothly · High frequency",
                description = "Glides smoothly across Seven Seas Lagoon right to the Magic Kingdom turnstiles."
            ),
            TransportationRoute(
                id = "trans_monorail_epcot",
                destinationId = "dest_wdw",
                name = "EPCOT Express Monorail",
                mode = TransportMode.MONORAIL,
                fromStop = "Transportation & Ticket Center (TTC)",
                toStop = "EPCOT Main Gate",
                durationMinutes = 12,
                frequencyMinutes = 8,
                nextDepartureMinutes = 4,
                operatingStatus = "Operating normally",
                description = "Scenic elevated transit loop around Spaceship Earth into EPCOT."
            ),
            TransportationRoute(
                id = "trans_skyliner_studios",
                destinationId = "dest_wdw",
                name = "Disney Skyliner Route",
                mode = TransportMode.SKYLINER,
                fromStop = "Disney's Riviera Resort / Caribbean Beach",
                toStop = "Disney's Hollywood Studios",
                durationMinutes = 7,
                frequencyMinutes = 1,
                nextDepartureMinutes = 1,
                operatingStatus = "Continuous loading · 0 min queue",
                description = "Aerial gondolas soaring high over the waterways with bird's-eye views."
            ),
            TransportationRoute(
                id = "trans_ferry_magic",
                destinationId = "dest_wdw",
                name = "Seven Seas Lagoon Ferryboat",
                mode = TransportMode.FERRY_BOAT,
                fromStop = "TTC Dock",
                toStop = "Magic Kingdom Ferry Terminal",
                durationMinutes = 10,
                frequencyMinutes = 15,
                nextDepartureMinutes = 6,
                operatingStatus = "Cruising regularly",
                description = "Relaxing open-air nautical crossing with grand views of Cinderella Castle."
            )
        )

        val wdwDestination = Destination(
            id = "dest_wdw",
            name = "Walt Disney World Resort",
            shortName = "Disney World",
            location = "Orlando, Florida",
            country = "United States",
            currencySymbol = "$",
            timezone = "America/New_York",
            weather = WeatherData(
                temperatureC = 27,
                condition = "Sunny & Warm",
                precipitationChance = 10,
                uvIndex = 7,
                sunsetTime = "8:12 PM",
                alertMessage = "☀️ High UV today. Hydration stations available across all lands."
            ),
            parks = listOf(magicKingdom, epcot, hollywoodStudios, animalKingdom),
            hotels = wdwHotels,
            transportRoutes = wdwTransport,
            bannerDrawableId = wdwCastleBanner
        )

        // Destination 2: Disneyland Resort California
        val dlLands = listOf(
            Land("land_dl_mainstreet", "park_dl", "Main Street, U.S.A.", "Walt's original 1955 street", 0xFFDF9E27),
            Land("land_dl_tomorrowland", "park_dl", "Tomorrowland", "Adventures in technology & cosmos", 0xFF2C74D8),
            Land("land_dl_fantasyland", "park_dl", "Fantasyland", "Storybook adventures & Matterhorn", 0xFF703DA6),
            Land("land_dl_starwars", "park_dl", "Star Wars: Galaxy's Edge", "Rise of the Resistance & Millennium Falcon", 0xFF108A58),
            Land("land_dl_adventureland", "park_dl", "Adventureland", "Indiana Jones Adventure & Jungle Cruise", 0xFFD97706)
        )

        val dlAttractions = listOf(
            Attraction(
                id = "attr_dl_indy",
                parkId = "park_dl",
                landId = "land_dl_adventureland",
                landName = "Adventureland",
                name = "Indiana Jones™ Adventure: Temple of the Forbidden Eye",
                description = "Board a rugged troop transport into a treacherous crumbling temple.",
                waitTimeMinutes = 40,
                status = AttractionStatus.OPERATING,
                hasLightningLane = true,
                lightningLaneReturnTime = "1:45 PM",
                singleRiderAvailable = true,
                heightRequirementCm = 117,
                thrillLevel = ThrillLevel.HIGH,
                durationMinutes = 4,
                isIndoor = true,
                locationX = 0.28f,
                locationY = 0.58f
            ),
            Attraction(
                id = "attr_dl_matterhorn",
                parkId = "park_dl",
                landId = "land_dl_fantasyland",
                landName = "Fantasyland",
                name = "Matterhorn Bobsleds",
                description = "Careen down icy slopes and dodge the Abominable Snowman in the world's first tubular steel coaster.",
                waitTimeMinutes = 35,
                status = AttractionStatus.OPERATING,
                hasLightningLane = true,
                lightningLaneReturnTime = "2:10 PM",
                singleRiderAvailable = true,
                heightRequirementCm = 107,
                thrillLevel = ThrillLevel.MODERATE,
                durationMinutes = 3,
                isIndoor = false,
                locationX = 0.60f,
                locationY = 0.35f
            ),
            Attraction(
                id = "attr_dl_rise",
                parkId = "park_dl",
                landId = "land_dl_starwars",
                landName = "Star Wars: Galaxy's Edge",
                name = "Star Wars: Rise of the Resistance",
                description = "Join the Resistance in a breathtaking multi-system battle against the First Order and Kylo Ren.",
                waitTimeMinutes = 55,
                status = AttractionStatus.OPERATING,
                hasLightningLane = true,
                lightningLaneReturnTime = "4:30 PM",
                singleRiderAvailable = false,
                heightRequirementCm = 102,
                thrillLevel = ThrillLevel.HIGH,
                durationMinutes = 18,
                isIndoor = true,
                locationX = 0.25f,
                locationY = 0.25f,
                imageResId = spaceMountainBanner
            )
        )

        val disneylandPark = Park(
            id = "park_dl",
            destinationId = "dest_dlr",
            name = "Disneyland Park",
            tagLine = "The original happiest place on earth opened by Walt Disney",
            openHours = "Open until 12:00 AM Midnight",
            operationalStatus = ParkOperationalStatus.NORMAL,
            averageWaitMinutes = 28,
            lands = dlLands,
            bannerDrawableId = wdwCastleBanner
        )

        val dcaPark = Park(
            id = "park_dca",
            destinationId = "dest_dlr",
            name = "Disney California Adventure",
            tagLine = "Radiator Springs Racers, Avengers Campus & Pixar Pier",
            openHours = "Open until 10:00 PM",
            operationalStatus = ParkOperationalStatus.NORMAL,
            averageWaitMinutes = 32,
            lands = listOf(
                Land("land_dca_avengers", "park_dca", "Avengers Campus", "Heroic encounters and WEB SLINGERS", 0xFFB91C1C),
                Land("land_dca_cars", "park_dca", "Cars Land", "Route 66 in Radiator Springs", 0xFFD97706),
                Land("land_dca_pixar", "park_dca", "Pixar Pier", "Incredicoaster and boardwalk games", 0xFF2C74D8)
            ),
            bannerDrawableId = spaceMountainBanner
        )

        val dlrHotels = listOf(
            Hotel(
                id = "hotel_grand_californian",
                destinationId = "dest_dlr",
                name = "Disney's Grand Californian Hotel & Spa",
                tier = "Craftsman Luxury",
                description = "Direct private theme-park gate into Disney California Adventure with cozy stone fireplaces.",
                amenities = listOf("Exclusive DCA Theme Park Entrance", "Tenaya Stone Spa", "3 Themed Pools", "Napa Rose Fine Dining"),
                diningVenues = listOf("Napa Rose (Chef's Counter)", "Storytellers Cafe", "GCH Craftsman Bar"),
                transportConnections = listOf("Private DCA Turnstiles", "Downtown Disney Walkway"),
                bannerDrawableId = grandFloridianBanner
            )
        )

        val dlrTransport = listOf(
            TransportationRoute(
                id = "trans_dl_monorail",
                destinationId = "dest_dlr",
                name = "Disneyland Monorail",
                mode = TransportMode.MONORAIL,
                fromStop = "Downtown Disney District Station",
                toStop = "Tomorrowland Station (Inside Park)",
                durationMinutes = 5,
                frequencyMinutes = 6,
                nextDepartureMinutes = 2,
                operatingStatus = "Boarding now",
                description = "Skip the main gates with direct transit straight into Tomorrowland."
            )
        )

        val dlrDestination = Destination(
            id = "dest_dlr",
            name = "Disneyland Resort California",
            shortName = "Disneyland",
            location = "Anaheim, California",
            country = "United States",
            currencySymbol = "$",
            timezone = "America/Los_Angeles",
            weather = WeatherData(
                temperatureC = 24,
                condition = "Sunny & Clear",
                precipitationChance = 0,
                uvIndex = 6,
                sunsetTime = "7:45 PM"
            ),
            parks = listOf(disneylandPark, dcaPark),
            hotels = dlrHotels,
            transportRoutes = dlrTransport,
            bannerDrawableId = wdwCastleBanner
        )

        // Destination 3: Disneyland Paris
        val dlpLands = listOf(
            Land("land_dlp_mainstreet", "park_dlp", "Main Street, U.S.A.", "Victorian grand boulevard", 0xFFDF9E27),
            Land("land_dlp_discoveryland", "park_dlp", "Discoveryland", "Jules Verne visionary retro-futurism", 0xFF2C74D8),
            Land("land_dlp_fantasyland", "park_dlp", "Fantasyland", "Le Château de la Belle au Bois Dormant", 0xFF703DA6)
        )
        val dlpPark = Park(
            id = "park_dlp",
            destinationId = "dest_dlp",
            name = "Disneyland Park Paris",
            tagLine = "Fairy tales take flight beside Sleeping Beauty Castle",
            openHours = "Open until 11:00 PM (Disney Illuminations at 10:50 PM)",
            operationalStatus = ParkOperationalStatus.NORMAL,
            averageWaitMinutes = 22,
            lands = dlpLands,
            bannerDrawableId = wdwCastleBanner
        )
        val dlpStudios = Park(
            id = "park_dlp_studios",
            destinationId = "dest_dlp",
            name = "Walt Disney Studios Park",
            tagLine = "Marvel Avengers Campus & World of Frozen",
            openHours = "Open until 9:00 PM",
            operationalStatus = ParkOperationalStatus.NORMAL,
            averageWaitMinutes = 25,
            lands = listOf(
                Land("land_dlp_avengers", "park_dlp_studios", "Marvel Avengers Campus", "Flight Force & WEB Adventure", 0xFFB91C1C)
            ),
            bannerDrawableId = spaceMountainBanner
        )

        val dlpDestination = Destination(
            id = "dest_dlp",
            name = "Disneyland Paris",
            shortName = "Disneyland Paris",
            location = "Marne-la-Vallée",
            country = "France",
            currencySymbol = "€",
            timezone = "Europe/Paris",
            weather = WeatherData(
                temperatureC = 21,
                condition = "Partly Cloudy",
                precipitationChance = 20,
                uvIndex = 5,
                sunsetTime = "9:05 PM"
            ),
            parks = listOf(dlpPark, dlpStudios),
            hotels = listOf(
                Hotel(
                    id = "hotel_dlp_disneyland",
                    destinationId = "dest_dlp",
                    name = "Disneyland Hotel Paris",
                    tier = "5-Star Royal Palace",
                    description = "Majestic five-star residence right above the park entrance dedicated to Disney royalty.",
                    amenities = listOf("Private VIP Park Entrance", "Royal Kids Club", "Crystal Pool & Spa"),
                    diningVenues = listOf("La Table de Lumière", "Royal Banquet"),
                    transportConnections = listOf("Direct Park Gateway"),
                    bannerDrawableId = grandFloridianBanner
                )
            ),
            transportRoutes = listOf(
                TransportationRoute(
                    id = "trans_dlp_shuttle",
                    destinationId = "dest_dlp",
                    name = "Resort Hotel Free Shuttle",
                    mode = TransportMode.RESORT_BUS,
                    fromStop = "Disney Hotel New York",
                    toStop = "Disneyland Paris Station & Gates",
                    durationMinutes = 8,
                    frequencyMinutes = 10,
                    nextDepartureMinutes = 4,
                    description = "Complimentary electric shuttles running every 10 minutes."
                )
            ),
            bannerDrawableId = wdwCastleBanner
        )

        // Destination 4: Tokyo Disney Resort
        val tdrParks = listOf(
            Park(
                id = "park_tdl",
                destinationId = "dest_tdr",
                name = "Tokyo Disneyland",
                tagLine = "Kingdom of Dreams and Magic",
                openHours = "Open until 9:00 PM",
                operationalStatus = ParkOperationalStatus.NORMAL,
                averageWaitMinutes = 35,
                lands = listOf(
                    Land("land_tdl_beauty", "park_tdl", "Enchanted Tale of Beauty and the Beast", "Beast's Castle realm", 0xFF703DA6),
                    Land("land_tdl_tomorrowland", "park_tdl", "Tomorrowland", "Space Mountain & Baymax", 0xFF2C74D8)
                ),
                bannerDrawableId = wdwCastleBanner
            ),
            Park(
                id = "park_tds",
                destinationId = "dest_tdr",
                name = "Tokyo DisneySea",
                tagLine = "Where imagination and adventure set sail across Fantasy Springs",
                openHours = "Open until 9:00 PM",
                operationalStatus = ParkOperationalStatus.NORMAL,
                averageWaitMinutes = 40,
                lands = listOf(
                    Land("land_tds_fantasy_springs", "park_tds", "Fantasy Springs", "Frozen Kingdom, Rapunzel's Forest & Peter Pan", 0xFF108A58),
                    Land("land_tds_mediterranean", "park_tds", "Mediterranean Harbor", "Mount Prometheus and Venice canals", 0xFFDF9E27)
                ),
                bannerDrawableId = spaceMountainBanner
            )
        )

        val tdrDestination = Destination(
            id = "dest_tdr",
            name = "Tokyo Disney Resort",
            shortName = "Tokyo Disney",
            location = "Urayasu, Chiba",
            country = "Japan",
            currencySymbol = "¥",
            timezone = "Asia/Tokyo",
            weather = WeatherData(
                temperatureC = 26,
                condition = "Clear Sky",
                precipitationChance = 5,
                uvIndex = 6,
                sunsetTime = "6:30 PM"
            ),
            parks = tdrParks,
            hotels = listOf(
                Hotel(
                    id = "hotel_tds_miracosta",
                    destinationId = "dest_tdr",
                    name = "Tokyo DisneySea Hotel MiraCosta",
                    tier = "Luxury Theme-Park Hotel",
                    description = "The only hotel integrated inside Tokyo DisneySea overlooking Mediterranean Harbor.",
                    amenities = listOf("Direct Park Gateway into DisneySea", "Terme Venezia Indoor Pool", "Harbor View Rooms"),
                    diningVenues = listOf("Oceano", "Silk Road Garden", "BellaVista Lounge"),
                    transportConnections = listOf("Disney Resort Line Monorail Station (Direct Access)"),
                    bannerDrawableId = grandFloridianBanner
                )
            ),
            transportRoutes = listOf(
                TransportationRoute(
                    id = "trans_tdr_monorail",
                    destinationId = "dest_tdr",
                    name = "Disney Resort Line",
                    mode = TransportMode.MONORAIL,
                    fromStop = "Resort Gateway Station (Maihama)",
                    toStop = "Tokyo DisneySea Station",
                    durationMinutes = 9,
                    frequencyMinutes = 4,
                    nextDepartureMinutes = 2,
                    description = "Mickey-window monorail encircling the entire Tokyo Disney Resort perimeter."
                )
            ),
            bannerDrawableId = wdwCastleBanner
        )

        return listOf(wdwDestination, dlrDestination, dlpDestination, tdrDestination)
    }

    fun getInitialAttractions(parkId: String): List<Attraction> {
        val allDestinations = getInitialDestinations()
        val allParks = allDestinations.flatMap { it.parks }
        val matchedPark = allParks.find { it.id == parkId } ?: allParks.first()

        // If park_mk, return the rich list
        return when (matchedPark.id) {
            "park_mk" -> listOf(
                Attraction("attr_space_mountain", "park_mk", "land_mk_tomorrowland", "Tomorrowland", "Space Mountain", "Blast off on a thrilling, high-speed rollercoaster journey through the dark depths of outer space.", 25, AttractionStatus.OPERATING, true, "11:15 AM", true, 112, ThrillLevel.HIGH, 3, true, true, true, true, 0.82f, 0.38f, R.drawable.banner_space_mountain_1787129513335),
                Attraction("attr_seven_dwarfs", "park_mk", "land_mk_fantasyland", "Fantasyland", "Seven Dwarfs Mine Train", "Race through the diamond mine on a swinging mine cart coaster with Snow White and friends.", 45, AttractionStatus.OPERATING, true, "1:30 PM", false, 97, ThrillLevel.MODERATE, 3, false, true, true, true, 0.58f, 0.28f, R.drawable.banner_disney_castle_1787129476105),
                Attraction("attr_peter_pan", "park_mk", "land_mk_fantasyland", "Fantasyland", "Peter Pan's Flight", "Fly over London in an enchanted pirate galleon all the way to Neverland.", 35, AttractionStatus.OPERATING, true, "12:00 PM", false, null, ThrillLevel.MILD, 3, true, true, true, true, 0.44f, 0.32f),
                Attraction("attr_pirates", "park_mk", "land_mk_adventureland", "Adventureland", "Pirates of the Caribbean", "Sail on a swashbuckling voyage through uncharted waters amidst roguish pirates and cannons.", 20, AttractionStatus.OPERATING, true, "10:45 AM", false, null, ThrillLevel.MILD, 9, true, true, true, true, 0.22f, 0.62f),
                Attraction("attr_haunted_mansion", "park_mk", "land_mk_liberty", "Liberty Square", "Haunted Mansion", "Climb aboard a Doom Buggy for a spirited tour of 999 happy haunts in a gothic manor.", 30, AttractionStatus.OPERATING, true, "12:45 PM", false, null, ThrillLevel.MILD, 8, true, true, true, true, 0.32f, 0.36f),
                Attraction("attr_tron", "park_mk", "land_mk_tomorrowland", "Tomorrowland", "TRON Lightcycle / Run", "Sync into the Grid and race on two-wheeled Lightcycles in an epic digital showdown.", 60, AttractionStatus.OPERATING, true, "3:10 PM", true, 122, ThrillLevel.EXTREME, 2, true, true, true, true, 0.88f, 0.26f, R.drawable.banner_space_mountain_1787129513335),
                Attraction("attr_big_thunder", "park_mk", "land_mk_frontierland", "Frontierland", "Big Thunder Mountain Railroad", "The wildest ride in the wilderness through runaway gold mine tunnels and caves.", 15, AttractionStatus.OPERATING, true, "11:00 AM", false, 102, ThrillLevel.MODERATE, 4, false, true, true, true, 0.16f, 0.40f),
                Attraction("attr_small_world", "park_mk", "land_mk_fantasyland", "Fantasyland", "it's a small world", "Sing along on a whimsical water cruise celebrating global unity and joyful harmony.", 15, AttractionStatus.OPERATING, true, "10:30 AM", false, null, ThrillLevel.ALL_AGES, 11, true, true, true, true, 0.40f, 0.22f),
                Attraction("attr_jungle_cruise", "park_mk", "land_mk_adventureland", "Adventureland", "Jungle Cruise", "Navigate dangerous waterways filled with wild animatronics and pun-loving skippers.", 40, AttractionStatus.OPERATING, true, "2:15 PM", false, null, ThrillLevel.ALL_AGES, 10, false, true, true, true, 0.30f, 0.70f)
            )
            "park_epcot" -> listOf(
                Attraction("attr_guardians", "park_epcot", "land_epcot_world_discovery", "World Discovery", "Guardians of the Galaxy: Cosmic Rewind", "Save the galaxy on Disney's first reverse-launch omnicoaster with a custom mixtape.", 55, AttractionStatus.OPERATING, true, "2:00 PM", true, 107, ThrillLevel.HIGH, 4, true, true, true, true, 0.75f, 0.30f, R.drawable.banner_space_mountain_1787129513335),
                Attraction("attr_soarin", "park_epcot", "land_epcot_world_celebration", "World Nature", "Soarin' Around the World", "Hang-glide over majestic landmarks with gentle scents and winds.", 25, AttractionStatus.OPERATING, true, "11:30 AM", true, 102, ThrillLevel.MILD, 5, true, true, true, true, 0.30f, 0.40f),
                Attraction("attr_remy", "park_epcot", "land_epcot_world_showcase", "France Pavilion", "Remy's Ratatouille Adventure", "Shrink down to rat size for a culinary chase through Gusteau's restaurant.", 45, AttractionStatus.OPERATING, true, "1:15 PM", true, null, ThrillLevel.ALL_AGES, 5, true, true, true, true, 0.65f, 0.75f)
            )
            "park_dhs" -> listOf(
                Attraction("attr_rise_wdw", "park_dhs", "land_dhs_galaxys_edge", "Star Wars: Galaxy's Edge", "Star Wars: Rise of the Resistance", "Join the Resistance in a multi-platform battle against the First Order.", 65, AttractionStatus.OPERATING, true, "4:00 PM", false, 102, ThrillLevel.HIGH, 18, true, true, true, true, 0.25f, 0.30f, R.drawable.banner_space_mountain_1787129513335),
                Attraction("attr_tower_terror", "park_dhs", "land_dhs_sunset", "Sunset Boulevard", "The Twilight Zone Tower of Terror™", "Plummet down an elevator shaft into the 5th dimension.", 35, AttractionStatus.OPERATING, true, "1:00 PM", false, 102, ThrillLevel.HIGH, 4, true, true, true, true, 0.80f, 0.70f),
                Attraction("attr_slinky", "park_dhs", "land_dhs_toy_story", "Toy Story Land", "Slinky Dog Dash", "Spring across Andy's backyard on a family coaster created by Andy himself.", 50, AttractionStatus.OPERATING, true, "2:45 PM", false, 97, ThrillLevel.MODERATE, 3, false, true, true, true, 0.50f, 0.50f)
            )
            "park_dl" -> listOf(
                Attraction("attr_dl_indy", "park_dl", "land_dl_adventureland", "Adventureland", "Indiana Jones™ Adventure", "Rugged troop transport into a treacherous temple.", 40, AttractionStatus.OPERATING, true, "1:45 PM", true, 117, ThrillLevel.HIGH, 4, true, true, true, true, 0.28f, 0.58f),
                Attraction("attr_dl_matterhorn", "park_dl", "land_dl_fantasyland", "Fantasyland", "Matterhorn Bobsleds", "Careen down icy slopes and dodge the Abominable Snowman.", 35, AttractionStatus.OPERATING, true, "2:10 PM", true, 107, ThrillLevel.MODERATE, 3, false, true, true, true, 0.60f, 0.35f),
                Attraction("attr_dl_space", "park_dl", "land_dl_tomorrowland", "Tomorrowland", "Space Mountain", "High-speed outer space hyperspace coaster.", 30, AttractionStatus.OPERATING, true, "12:30 PM", true, 102, ThrillLevel.HIGH, 3, true, true, true, true, 0.80f, 0.40f, R.drawable.banner_space_mountain_1787129513335)
            )
            else -> listOf(
                Attraction("attr_generic_castle", matchedPark.id, "land_1", "Center Court", "${matchedPark.name} Grand Voyage", "An iconic signature adventure celebrating the park's wonders.", 20, AttractionStatus.OPERATING, true, "11:00 AM", true, null, ThrillLevel.ALL_AGES, 6, true, true, true, true, 0.5f, 0.5f, R.drawable.banner_disney_castle_1787129476105)
            )
        }
    }

    fun getInitialRestaurants(parkId: String): List<Restaurant> {
        val wdwCastleBanner = R.drawable.banner_disney_castle_1787129476105
        val spaceMountainBanner = R.drawable.banner_space_mountain_1787129513335

        return listOf(
            Restaurant(
                id = "rest_be_our_guest",
                parkId = parkId,
                landId = "land_mk_fantasyland",
                landName = "Fantasyland",
                name = "Be Our Guest Restaurant",
                cuisine = "French-inspired Fine Dining",
                priceTier = "$$$$",
                serviceType = ServiceType.FINE_DINING,
                allowsMobileOrder = false,
                openingHours = "10:00 AM – 9:30 PM",
                rating = 4.8,
                dietaryOptions = listOf("Plant-Based", "Gluten-Free", "Nut-Free"),
                menuItems = listOf(
                    MenuItem("m1", "Filet Mignon", "Center-cut beef tenderloin, potato pavé, seasonal baby vegetables, red wine jus", 48.00, isGlutenFreeFriendly = true, isChefSpecial = true),
                    MenuItem("m2", "French Onion Soup", "Rich beef broth, toasted crouton, melted Gruyère cheese", 14.00),
                    MenuItem("m3", "The Master's Cupcake", "Chocolate sponge cake topped with the famous \"Grey Stuff\"", 8.50, isKidFriendly = true)
                ),
                availableReservationSlots = listOf("12:15 PM", "1:45 PM", "6:00 PM", "7:30 PM"),
                locationX = 0.52f,
                locationY = 0.20f,
                imageResId = wdwCastleBanner
            ),
            Restaurant(
                id = "rest_cosmic_rays",
                parkId = parkId,
                landId = "land_mk_tomorrowland",
                landName = "Tomorrowland",
                name = "Cosmic Ray's Starlight Café",
                cuisine = "American Classic & Burgers",
                priceTier = "$$",
                serviceType = ServiceType.QUICK_SERVICE,
                allowsMobileOrder = true,
                openingHours = "10:30 AM – 10:00 PM",
                rating = 4.4,
                dietaryOptions = listOf("Plant-Based Burgers", "Allergy Friendly Buns"),
                menuItems = listOf(
                    MenuItem("cr1", "Rocket Angus Cheeseburger", "1/3 lb angus patty, aged cheddar, toasted brioche, seasoned fries", 13.99),
                    MenuItem("cr2", "Plant-based Galaxy Wrap", "Crispy plant tenders, vegan ranch, crisp greens, whole grain tortilla", 12.49, isPlantBased = true),
                    MenuItem("cr3", "Sonny Eclipse Lunar Shake", "Cookies and cream hand-spun shake with starlight candy sprinkles", 6.99, isKidFriendly = true)
                ),
                availableReservationSlots = emptyList(),
                locationX = 0.74f,
                locationY = 0.44f,
                imageResId = spaceMountainBanner
            ),
            Restaurant(
                id = "rest_skippers_canteen",
                parkId = parkId,
                landId = "land_mk_adventureland",
                landName = "Adventureland",
                name = "Skipper Canteen",
                cuisine = "Bold Asian, South American & African Flavors",
                priceTier = "$$$",
                serviceType = ServiceType.TABLE_SERVICE,
                allowsMobileOrder = false,
                openingHours = "11:00 AM – 9:00 PM",
                rating = 4.7,
                dietaryOptions = listOf("Gluten-Free", "Vegetarian", "Dairy-Free"),
                menuItems = listOf(
                    MenuItem("sc1", "\"Tastes Like Chicken\" Because It Is!", "Crispy-fried chicken with chili glaze and spiced rice", 28.00),
                    MenuItem("sc2", "Curried Vegetable Stew", "Roasted squash, coconut curry broth, pineapple chutney, jasmine rice", 22.00, isPlantBased = true, isGlutenFreeFriendly = true),
                    MenuItem("sc3", "Kungaloosh Cake", "African chocolate cake topped with caramel and coffee dust", 9.50)
                ),
                availableReservationSlots = listOf("12:00 PM", "12:45 PM", "5:30 PM", "8:15 PM"),
                locationX = 0.34f,
                locationY = 0.66f
            ),
            Restaurant(
                id = "rest_aloha_isle",
                parkId = parkId,
                landId = "land_mk_adventureland",
                landName = "Adventureland",
                name = "Aloha Isle Refreshments",
                cuisine = "DOLE Whip® & Island Treats",
                priceTier = "$",
                serviceType = ServiceType.SNACK_CART,
                allowsMobileOrder = true,
                openingHours = "9:00 AM – 11:00 PM",
                rating = 4.9,
                dietaryOptions = listOf("Vegan", "Gluten-Free", "Dairy-Free"),
                menuItems = listOf(
                    MenuItem("ai1", "Original DOLE Whip® Soft Serve", "Iconic pineapple dairy-free soft serve swirl", 5.99, isPlantBased = true, isGlutenFreeFriendly = true),
                    MenuItem("ai2", "DOLE Whip® Pineapple Float", "Pineapple soft serve floating over 100% chilled Dole pineapple juice", 7.49, isPlantBased = true, isGlutenFreeFriendly = true)
                ),
                availableReservationSlots = emptyList(),
                locationX = 0.28f,
                locationY = 0.60f
            )
        )
    }

    fun getInitialEntertainment(parkId: String): List<EntertainmentEvent> {
        val wdwCastleBanner = R.drawable.banner_disney_castle_1787129476105
        return listOf(
            EntertainmentEvent(
                id = "ent_fireworks_happily",
                parkId = parkId,
                landId = "land_mk_mainstreet",
                landName = "Main Street, U.S.A.",
                name = "Happily Ever After Fireworks Spectacular",
                category = EntertainmentCategory.FIREWORKS,
                showtimes = listOf("8:45 PM"),
                nextShowtime = "8:45 PM",
                durationMinutes = 18,
                locationDescription = "Cinderella Castle & Main Street Hub",
                description = "Transformative projection mapping, pyrotechnics, and emotional Disney anthems illuminating the night sky.",
                isNighttimeSpectacular = true,
                locationX = 0.50f,
                locationY = 0.48f,
                imageResId = wdwCastleBanner
            ),
            EntertainmentEvent(
                id = "ent_parade_festival",
                parkId = parkId,
                landId = "land_mk_frontierland",
                landName = "Frontierland & Main Street",
                name = "Disney Festival of Fantasy Parade",
                category = EntertainmentCategory.PARADE,
                showtimes = listOf("12:00 PM", "3:00 PM"),
                nextShowtime = "3:00 PM",
                durationMinutes = 25,
                locationDescription = "Parade route from Frontierland to Town Square",
                description = "Towering vibrant floats featuring a fire-breathing Maleficent dragon, Peter Pan, Rapunzel, and beloved Disney characters.",
                isNighttimeSpectacular = false,
                locationX = 0.42f,
                locationY = 0.52f,
                imageResId = wdwCastleBanner
            ),
            EntertainmentEvent(
                id = "ent_mickey_meet",
                parkId = parkId,
                landId = "land_mk_mainstreet",
                landName = "Main Street, U.S.A.",
                name = "Meet Mickey Mouse at Town Square Theater",
                category = EntertainmentCategory.CHARACTER_EXPERIENCE,
                showtimes = listOf("Continuous 9:00 AM – 10:00 PM"),
                nextShowtime = "Available Now (15m wait)",
                durationMinutes = 5,
                locationDescription = "Town Square Theater, Right of Entrance",
                description = "Share a hug, snap a photo, and get an autograph from Mickey Mouse in his Magician dressing room.",
                isNighttimeSpectacular = false,
                locationX = 0.54f,
                locationY = 0.88f
            )
        )
    }

    fun getInitialFacilities(parkId: String): List<MapFacility> {
        return listOf(
            MapFacility("fac_restroom_1", parkId, "Restrooms & Companion Care", MapCategory.RESTROOMS, "Fantasyland", 0.48f, 0.28f, "Companion Restroom & Baby Changing Station"),
            MapFacility("fac_restroom_2", parkId, "Tomorrowland Restrooms", MapCategory.RESTROOMS, "Tomorrowland", 0.78f, 0.46f, "Wheelchair accessible"),
            MapFacility("fac_firstaid", parkId, "Main Street First Aid & Care", MapCategory.FIRST_AID, "Main Street, U.S.A.", 0.56f, 0.76f, "Registered nurses, medication storage, first aid assistance"),
            MapFacility("fac_shop_emporium", parkId, "Emporium Merchandise Mega-Store", MapCategory.SHOPS, "Main Street, U.S.A.", 0.46f, 0.80f, "Apparel, pins, ears, magic bands, souvenirs"),
            MapFacility("fac_char_princess", parkId, "Princess Fairytale Hall", MapCategory.CHARACTERS, "Fantasyland", 0.54f, 0.34f, "Meet Cinderella, Elena, Tiana & Rapunzel")
        )
    }

    fun getInitialTickets(): List<Ticket> {
        return listOf(
            Ticket(
                id = "tkt_ap_01",
                title = "Disney Incredi-Pass (Annual Pass)",
                type = TicketType.ANNUAL_PASS,
                holderName = "Tom",
                validFrom = "Jan 1, 2026",
                validTo = "Dec 31, 2026",
                parksAllowedSummary = "All 4 Theme Parks · No Blockout Dates · Free Theme Park Parking",
                hasParkHopper = true,
                hasLightningLanePass = true,
                qrCodeData = "DISNEY-AP-TOM-9842104-VALID",
                barcodeData = "9842104882190",
                magicBandColorHex = 0xFFDF9E27,
                status = "Valid Today"
            ),
            Ticket(
                id = "tkt_day_02",
                title = "4-Day Park Hopper Plus Ticket",
                type = TicketType.PARK_HOPPER,
                holderName = "Sarah",
                validFrom = "Aug 18, 2026",
                validTo = "Aug 22, 2026",
                parksAllowedSummary = "Valid for Magic Kingdom, EPCOT, Hollywood Studios, Animal Kingdom",
                hasParkHopper = true,
                hasLightningLanePass = true,
                qrCodeData = "DISNEY-PH-SARAH-4829103-VALID",
                barcodeData = "4829103829102",
                magicBandColorHex = 0xFFE64A6E,
                status = "Day 2 of 4 Active"
            ),
            Ticket(
                id = "tkt_child_03",
                title = "4-Day Theme Park Child Ticket",
                type = TicketType.MULTI_DAY,
                holderName = "Leo (Child)",
                validFrom = "Aug 18, 2026",
                validTo = "Aug 22, 2026",
                parksAllowedSummary = "Park Hopper Included with Family Pass",
                hasParkHopper = true,
                hasLightningLanePass = true,
                qrCodeData = "DISNEY-CH-LEO-3918201-VALID",
                barcodeData = "3918201991820",
                magicBandColorHex = 0xFF2C74D8,
                status = "Day 2 of 4 Active"
            )
        )
    }

    fun getInitialItinerary(): List<ItineraryItem> {
        return listOf(
            ItineraryItem(
                id = "itin_01",
                time = "09:00 AM",
                title = "Enter Magic Kingdom Park",
                subtitle = "Early Theme Park Entry via Turnstile #4",
                category = ItineraryCategory.TRANSIT,
                location = "Main Entrance & Town Square",
                durationMinutes = 15,
                walkTimeMinutesFromPrev = 0,
                isCompleted = true
            ),
            ItineraryItem(
                id = "itin_02",
                time = "09:30 AM",
                title = "Seven Dwarfs Mine Train",
                subtitle = "Standby Queue completed (22 min wait)",
                category = ItineraryCategory.ATTRACTION,
                location = "Fantasyland",
                durationMinutes = 25,
                walkTimeMinutesFromPrev = 6,
                isCompleted = true,
                linkedAttractionId = "attr_seven_dwarfs"
            ),
            ItineraryItem(
                id = "itin_03",
                time = "10:30 AM",
                title = "Space Mountain",
                subtitle = "Lightning Lane Multi Pass Window: 10:30 AM – 11:30 AM",
                category = ItineraryCategory.ATTRACTION,
                location = "Tomorrowland",
                durationMinutes = 30,
                walkTimeMinutesFromPrev = 6,
                isCompleted = false,
                note = "Hold personal items or use free lockers near queue.",
                linkedAttractionId = "attr_space_mountain"
            ),
            ItineraryItem(
                id = "itin_04",
                time = "12:00 PM",
                title = "Lunch at Cosmic Ray's Starlight Café",
                subtitle = "Mobile Order Window: Ready at 12:05 PM",
                category = ItineraryCategory.DINING,
                location = "Tomorrowland",
                durationMinutes = 45,
                walkTimeMinutesFromPrev = 3,
                isCompleted = false,
                linkedRestaurantId = "rest_cosmic_rays"
            ),
            ItineraryItem(
                id = "itin_05",
                time = "02:30 PM",
                title = "Disney Festival of Fantasy Parade",
                subtitle = "Reserved viewing area in Liberty Square / Hub",
                category = ItineraryCategory.SHOW,
                location = "Main Street Hub",
                durationMinutes = 30,
                walkTimeMinutesFromPrev = 5,
                isCompleted = false
            ),
            ItineraryItem(
                id = "itin_06",
                time = "04:15 PM",
                title = "Pirates of the Caribbean",
                subtitle = "Standby Queue expected 20m wait",
                category = ItineraryCategory.ATTRACTION,
                location = "Adventureland",
                durationMinutes = 30,
                walkTimeMinutesFromPrev = 8,
                isCompleted = false,
                linkedAttractionId = "attr_pirates"
            ),
            ItineraryItem(
                id = "itin_07",
                time = "06:00 PM",
                title = "Dinner at Be Our Guest Restaurant",
                subtitle = "Table Service Reservation #BG-9482 for Party of 4",
                category = ItineraryCategory.DINING,
                location = "Fantasyland (Beast's Castle)",
                durationMinutes = 75,
                walkTimeMinutesFromPrev = 7,
                isCompleted = false,
                linkedRestaurantId = "rest_be_our_guest"
            ),
            ItineraryItem(
                id = "itin_08",
                time = "08:30 PM",
                title = "Happily Ever After Fireworks",
                subtitle = "Nighttime Spectacular at Cinderella Castle Plaza",
                category = ItineraryCategory.FIREWORKS,
                location = "Cinderella Castle Hub",
                durationMinutes = 30,
                walkTimeMinutesFromPrev = 6,
                isCompleted = false
            )
        )
    }

    fun getInitialNotifications(): List<NotificationItem> {
        return listOf(
            NotificationItem(
                id = "notif_01",
                title = "Lightning Lane Window Approaching",
                message = "Your Space Mountain return window opens at 10:30 AM (in 12 minutes).",
                timestamp = "10:18 AM",
                priority = NotificationPriority.CRITICAL,
                read = false,
                actionLabel = "View Pass",
                targetType = "attraction",
                targetId = "attr_space_mountain"
            ),
            NotificationItem(
                id = "notif_02",
                title = "Wait Time Dropped!",
                message = "Big Thunder Mountain Railroad wait has dropped to 15 minutes.",
                timestamp = "10:05 AM",
                priority = NotificationPriority.IMPORTANT,
                read = false,
                actionLabel = "Navigate",
                targetType = "attraction",
                targetId = "attr_big_thunder"
            ),
            NotificationItem(
                id = "notif_03",
                title = "Dining Reservation Reminder",
                message = "Be Our Guest table reservation confirmed for 6:00 PM today.",
                timestamp = "09:00 AM",
                priority = NotificationPriority.USEFUL,
                read = true,
                actionLabel = "View Table",
                targetType = "dining",
                targetId = "rest_be_our_guest"
            ),
            NotificationItem(
                id = "notif_04",
                title = "Weather Tip",
                message = "Afternoon high of 27°C with bright sunshine. Remember to stay hydrated!",
                timestamp = "08:30 AM",
                priority = NotificationPriority.OPTIONAL,
                read = true
            )
        )
    }

    fun getInitialFamilyMembers(): List<FamilyMember> {
        return listOf(
            FamilyMember("fam_01", "Tom (You)", "Passholder / Lead", 0xFFDF9E27, false, 0xFFDF9E27, "Near Tomorrowland Hub"),
            FamilyMember("fam_02", "Sarah", "Adult", 0xFFE64A6E, true, 0xFFE64A6E, "Near Tomorrowland Hub"),
            FamilyMember("fam_03", "Leo", "Child (Age 7)", 0xFF2C74D8, true, 0xFF2C74D8, "With Tom"),
            FamilyMember("fam_04", "Maya", "Child (Age 5)", 0xFF108A58, true, 0xFF108A58, "With Sarah")
        )
    }

    fun getInitialRoomReservation(): RoomReservation {
        return RoomReservation(
            id = "res_room_984",
            hotelId = "hotel_grand_floridian",
            hotelName = "Disney's Grand Floridian Resort & Spa",
            roomTypeName = "Outer Building – Theme Park View Room (2 Queen Beds)",
            checkInDate = "Aug 18, 2026",
            checkOutDate = "Aug 22, 2026",
            roomNumber = "4218 (Boca Chica)",
            guestName = "Tom",
            digitalKeyUnlocked = true
        )
    }
}
