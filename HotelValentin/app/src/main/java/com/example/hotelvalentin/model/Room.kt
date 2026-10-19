package com.example.hotelvalentin.model

data class Amenity(
    val id: String,
    val name: String,
    val iconName: String
)

data class Room(
    val id: String,
    val name: String,
    val category: String, // "Todas", "Deluxe", "Estándar", "Presidencial"
    val pricePerNight: Double,
    val rating: Double,
    val reviewCount: Int,
    val imageRes: Int,
    val description: String,
    val maxGuests: Int,
    val bedType: String,
    val sizeSqMeters: Int,
    val amenities: List<String>,
    val isAvailable: Boolean = true
)
