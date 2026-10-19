package com.example.hotelvalentin.model

enum class UserRole {
    CLIENT,
    ADMIN
}

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val points: Int = 1250,
    val memberLevel: String = "Socio Oro VIP"
)
