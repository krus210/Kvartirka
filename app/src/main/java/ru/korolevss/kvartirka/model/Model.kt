package ru.korolevss.kvartirka.model

data class Model(
    val city_id: Int,
    val currency: Currency,
    val flats: List<Flat>,
    val query: Query,
    val version: Double
)

data class Currency(
    val id: Int,
    val label: String
)

data class Flat(
    val address: String,
    val badges: Badges,
    val building_type: String,
    val city_id: Int,
    val contacts: Contacts,
    val coordinates: Coordinates,
    val description: String,
    val description_full: String,
    val distance_from_point_text: String,
    val id: Int,
    val metro: String,
    val photo_default: PhotoDefault,
    val photos: List<Photo>,
    val prices: Prices,
    val rooms: Int,
    val title: String,
    val url: String
)

data class Query(
    val filter: Filter,
    val meta: Meta
)

data class Badges(
    val more_than_year: Boolean,
    val owner_confirmed: Boolean,
    val payed: Int
)

data class Contacts(
    val flats_count: Int,
    val id: Int,
    val name: String,
    val phones: List<Phone>,
    val send_booking_request_allowed: Boolean,
    val show_prepayment_warning: Boolean,
    val skype: String,
    val vk_profile: String
)

data class Coordinates(
    val lat: Double,
    val lon: Double
)

data class PhotoDefault(
    val height: Int,
    val url: String,
    val verified: Boolean,
    val width: Int
)

data class Photo(
    val url: String,
    val verified: Boolean
)

data class Prices(
    val day: Int,
    val hour: Int,
    val night: Int
)

data class Phone(
    val normalized: String,
    val phone: String
)

data class Filter(
    val app_user_id: Any,
    val currency_id: Int,
    val point: Point
)

data class Meta(
    val limit: Int,
    val nearest: Int,
    val offset: Int
)

data class Point(
    val point_lat: Double,
    val point_lng: Double
)