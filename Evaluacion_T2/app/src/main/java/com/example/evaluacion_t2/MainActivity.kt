package com.example.evaluacion_t2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.evaluacion_t2.data.local.AppDatabase
import com.example.evaluacion_t2.data.remote.RetrofitClient
import com.example.evaluacion_t2.data.repository.UserRepository
import com.example.evaluacion_t2.ui.screens.EditUserScreen
import com.example.evaluacion_t2.ui.screens.LoginScreen
import com.example.evaluacion_t2.ui.screens.MainScreen
import com.example.evaluacion_t2.ui.screens.NewUserScreen
import com.example.evaluacion_t2.ui.screens.RegisterScreen
import com.example.evaluacion_t2.ui.theme.Evaluacion_T2Theme
import com.example.evaluacion_t2.ui.viewmodel.UserViewModel
import com.example.evaluacion_t2.ui.viewmodel.UserViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Evaluacion_T2Theme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val repository = UserRepository(database.userDao(), RetrofitClient.apiService)
    val factory = UserViewModelFactory(repository)
    val userViewModel: UserViewModel = viewModel(factory = factory)

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                viewModel = userViewModel,
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                viewModel = userViewModel,
                onRegisterSuccess = {
                    navController.navigate("main") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable("main") {
            MainScreen(
                viewModel = userViewModel,
                onNavigateToNewUser = {
                    navController.navigate("new_user")
                },
                onNavigateToEditUser = { user ->
                    userViewModel.selectUser(user)
                    navController.navigate("edit_user")
                }
            )
        }

        composable("new_user") {
            NewUserScreen(
                viewModel = userViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("edit_user") {
            EditUserScreen(
                viewModel = userViewModel,
                onBack = {
                    userViewModel.selectUser(null)
                    navController.popBackStack()
                }
            )
        }
    }
}