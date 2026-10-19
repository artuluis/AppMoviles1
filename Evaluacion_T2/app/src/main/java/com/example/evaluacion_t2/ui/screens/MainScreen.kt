package com.example.evaluacion_t2.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.evaluacion_t2.data.local.UserEntity
import com.example.evaluacion_t2.ui.viewmodel.UserViewModel

@Composable
fun MainScreen(
    viewModel: UserViewModel,
    onNavigateToNewUser: () -> Unit,
    onNavigateToEditUser: (UserEntity) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    label = { Text("Home") },
                    icon = { Text("🌐") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    label = { Text("Locales") },
                    icon = { Text("👥") }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (selectedTab == 0) {
                HomeScreen(viewModel = viewModel)
            } else {
                LocalesScreen(
                    viewModel = viewModel,
                    onNavigateToNewUser = onNavigateToNewUser,
                    onNavigateToEditUser = onNavigateToEditUser
                )
            }
        }
    }
}