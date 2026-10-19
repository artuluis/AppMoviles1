package com.example.hotelvalentin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.hotelvalentin.model.User
import com.example.hotelvalentin.model.UserRole
import com.example.hotelvalentin.ui.screens.LoginScreen
import com.example.hotelvalentin.ui.screens.admin.AdminPanelScreen
import com.example.hotelvalentin.ui.screens.client.ClientMainScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    // La aplicación arranca obligatoriamente en el Login Universal (currentUser = null)
    var currentUser by remember { mutableStateOf<User?>(null) }

    when (val user = currentUser) {
        null -> {
            LoginScreen(
                onLoginSuccess = { loggedInUser ->
                    currentUser = loggedInUser
                },
                modifier = modifier
            )
        }
        else -> {
            when (user.role) {
                UserRole.CLIENT -> {
                    ClientMainScreen(
                        user = user,
                        onLogout = { currentUser = null },
                        modifier = modifier
                    )
                }
                UserRole.ADMIN -> {
                    AdminPanelScreen(
                        user = user,
                        onLogout = { currentUser = null },
                        modifier = modifier
                    )
                }
            }
        }
    }
}
