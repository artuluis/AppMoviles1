package com.example.hotelvalentin.ui.screens.client

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hotelvalentin.model.User
import com.example.hotelvalentin.ui.theme.HotelGold
import com.example.hotelvalentin.ui.theme.HotelNavy
import com.example.hotelvalentin.ui.theme.HotelSlate
import kotlinx.coroutines.launch

@Composable
fun ClientMainScreen(
    user: User,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedNavTab by remember { mutableIntStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val showMessage: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                val navItems = listOf(
                    Triple("Inicio", Icons.Default.Home, 0),
                    Triple("Reservas", Icons.Outlined.BookmarkBorder, 1),
                    Triple("Servicios", Icons.Default.Spa, 2),
                    Triple("Perfil", Icons.Default.Person, 3)
                )

                navItems.forEach { item ->
                    val isSelected = selectedNavTab == item.third
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedNavTab = item.third },
                        icon = { Icon(item.second, contentDescription = item.first) },
                        label = { Text(item.first, fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = HotelGold,
                            selectedTextColor = HotelNavy,
                            indicatorColor = HotelGold.copy(alpha = 0.18f),
                            unselectedIconColor = HotelSlate,
                            unselectedTextColor = HotelSlate
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            when (selectedNavTab) {
                0 -> CatalogTab(user = user, onShowMessage = showMessage)
                1 -> ReservationsTab(user = user, onShowMessage = showMessage)
                2 -> ServicesTab(onShowMessage = showMessage)
                3 -> ProfileTab(user = user, onLogout = onLogout)
            }
        }
    }
}
