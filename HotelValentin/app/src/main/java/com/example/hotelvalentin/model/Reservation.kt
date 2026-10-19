package com.example.hotelvalentin.model

data class Reservation(
    val id: String,
    val room: Room,
    val guestName: String,
    val checkInDate: String,
    val checkOutDate: String,
    val nights: Int,
    val totalAmount: Double,
    val status: String = "Confirmada" // "Confirmada", "En Habitación", "Completada"
)

data class GuestCheckIn(
    val id: String,
    val guestName: String,
    val roomNumber: String,
    val roomType: String,
    val checkInDate: String,
    val checkOutDate: String,
    val status: String // "En Estadía", "Check-in Pendiente", "Check-out Listo"
)
