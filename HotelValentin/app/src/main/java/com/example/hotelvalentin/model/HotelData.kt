package com.example.hotelvalentin.model

import androidx.compose.runtime.mutableStateListOf
import com.example.hotelvalentin.R

object HotelData {
    // Usuarios predeterminados para inicio de sesión
    val demoUsers = listOf(
        User(
            id = "usr_client",
            name = "Arturo Mendoza",
            email = "cliente@valentin.com",
            role = UserRole.CLIENT,
            points = 1450,
            memberLevel = "Socio Oro VIP"
        ),
        User(
            id = "usr_admin",
            name = "Gerencia Valentín",
            email = "admin@valentin.com",
            role = UserRole.ADMIN,
            points = 0,
            memberLevel = "Administrador Master"
        )
    )

    // Habitaciones
    val sampleRooms = mutableStateListOf(
        Room(
            id = "room_1",
            name = "Suite Deluxe con Jardín Zen",
            category = "Deluxe",
            pricePerNight = 185.00,
            rating = 4.95,
            reviewCount = 142,
            imageRes = R.drawable.room_deluxe,
            description = "Elegante suite con cama King size, iluminación cálida de diseño, acabados en madera fina y ventanales panorámicos hacia nuestro exclusivo jardín privado.",
            maxGuests = 2,
            bedType = "1 Cama King Size",
            sizeSqMeters = 52,
            amenities = listOf("WiFi 6", "King Size", "Vista al Jardín", "Jacuzzi", "Cafetera"),
            isAvailable = true
        ),
        Room(
            id = "room_2",
            name = "Habitación Estándar Minimalista",
            category = "Estándar",
            pricePerNight = 95.00,
            rating = 4.82,
            reviewCount = 98,
            imageRes = R.drawable.room_standard,
            description = "Espacio contemporáneo con diseño escandinavo, escritorio ergonómico para trabajo remoto, cama Queen premium y luz natural envolvente.",
            maxGuests = 2,
            bedType = "1 Cama Queen Size",
            sizeSqMeters = 34,
            amenities = listOf("WiFi de alta velocidad", "Cama Queen", "Escritorio", "Smart TV 50''"),
            isAvailable = true
        ),
        Room(
            id = "room_3",
            name = "Penthouse Suite Presidencial",
            category = "Presidencial",
            pricePerNight = 420.00,
            rating = 4.99,
            reviewCount = 67,
            imageRes = R.drawable.room_presidential,
            description = "La máxima expresión del lujo: piso completo en el ático con vistas panorámicas 360° al atardecer, sala de estar con mármol, bar privado y mayordomo personal.",
            maxGuests = 4,
            bedType = "2 Camas King Size",
            sizeSqMeters = 110,
            amenities = listOf("Mayordomo 24/7", "Bar privado", "Vistas 360°", "Salón VIP", "Pisos de mármol"),
            isAvailable = true
        ),
        Room(
            id = "room_4",
            name = "Suite Terraza con Infinity Pool",
            category = "Deluxe",
            pricePerNight = 240.00,
            rating = 4.91,
            reviewCount = 115,
            imageRes = R.drawable.hotel_pool,
            description = "Suite con terraza privada y acceso directo a la piscina infinita en la azotea. Disfruta de atardeceres dorados frente al mar con camas balinesas reservadas.",
            maxGuests = 2,
            bedType = "1 Cama King Size",
            sizeSqMeters = 60,
            amenities = listOf("Acceso Infinity Pool", "Cama balinesa", "Cóctel de bienvenida", "Desayuno buffet"),
            isAvailable = false
        )
    )

    val categories = listOf("Todas", "Deluxe", "Estándar", "Presidencial")

    // Lista mutable reactiva de reservas del cliente
    val userReservations = mutableStateListOf(
        Reservation(
            id = "HV-2026-881",
            room = sampleRooms[0],
            guestName = "Arturo Mendoza",
            checkInDate = "24 Oct 2026",
            checkOutDate = "27 Oct 2026",
            nights = 3,
            totalAmount = 555.00,
            status = "Confirmada"
        )
    )

    // Datos del panel de administración
    val activeGuests = mutableStateListOf(
        GuestCheckIn("G-101", "Carlos Santana", "Hab. 302", "Suite Deluxe", "18 Oct 2026", "22 Oct 2026", "En Estadía"),
        GuestCheckIn("G-102", "Valeria Rossi", "Hab. 501", "Presidencial", "17 Oct 2026", "21 Oct 2026", "En Estadía"),
        GuestCheckIn("G-103", "Fernando Gómez", "Hab. 204", "Estándar", "19 Oct 2026", "23 Oct 2026", "Check-in Pendiente"),
        GuestCheckIn("G-104", "Elena Silva", "Hab. 410", "Deluxe Pool", "15 Oct 2026", "19 Oct 2026", "Check-out Listo")
    )

    // Métricas del Hotel para el Administrador
    const val monthlyRevenue = 48250.00
    const val occupancyRate = 87
    const val totalRooms = 28
    const val occupiedRooms = 24
}

data class QuickService(
    val id: String,
    val title: String,
    val subtitle: String
)
