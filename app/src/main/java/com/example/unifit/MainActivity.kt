package com.example.unifit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.unifit.ui.auth.AuthViewModel
import com.example.unifit.ui.auth.AuthViewModelFactory
import com.example.unifit.ui.auth.LoginScreen
import com.example.unifit.ui.auth.RegisterScreen
import com.example.unifit.ui.components.BottomNavBar
import com.example.unifit.ui.home.HomeScreen
import com.example.unifit.ui.settings.HydrationSettingsScreen
import com.example.unifit.ui.water.WaterScreen
import com.example.unifit.ui.water.WaterStatsScreen
import com.example.unifit.ui.water.WaterViewModel
import com.example.unifit.ui.water.WaterViewModelFactory
import com.example.unifit.ui.welcome.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as UniFitApp

        // UseCases para auth
        val loginUC = com.example.unifit.domain.usecase.user.LoginUserUseCase(app.userRepository)
        val registerUC = com.example.unifit.domain.usecase.user.RegisterUserUseCase(app.userRepository)
        val logoutUC = com.example.unifit.domain.usecase.user.LogoutUserUseCase(app.sessionManager)
        val getUserUC = com.example.unifit.domain.usecase.user.GetUserByIdUseCase(app.userRepository)

        // UseCases para agua
        val addWaterUC = com.example.unifit.domain.usecase.water.AddWaterIntakeUseCase(app.waterRepository)
        val getWaterUC = com.example.unifit.domain.usecase.water.GetWaterIntakesUseCase(app.waterRepository)
        val updateWaterUC = com.example.unifit.domain.usecase.water.UpdateWaterIntakeUseCase(app.waterRepository)
        val deleteWaterUC = com.example.unifit.domain.usecase.water.DeleteWaterIntakeUseCase(app.waterRepository)

        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                // ViewModels
                val authFactory = AuthViewModelFactory(
                    loginUC, registerUC, logoutUC, app.sessionManager, getUserUC
                )
                val authViewModel = ViewModelProvider(this, authFactory)[AuthViewModel::class.java]

                val waterFactory = WaterViewModelFactory(
                    addWaterUC, getWaterUC, updateWaterUC, deleteWaterUC
                )
                val waterViewModel = ViewModelProvider(this, waterFactory)[WaterViewModel::class.java]

                Scaffold(
                    bottomBar = {
                        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                        if (currentRoute !in listOf("login", "register", "welcome")) {
                            BottomNavBar(navController)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = "welcome",
                        Modifier.padding(innerPadding)
                    ) {
                        // Pantalla de bienvenida
                        composable("welcome") {
                            WelcomeScreen(
                                onStartClick = { navController.navigate("login") }
                            )
                        }

                        // Login
                        composable("login") {
                            LoginScreen(
                                viewModel = authViewModel,
                                onNavigateToRegister = { navController.navigate("register") },
                                onLoginSuccess = {
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }

                        // Registro
                        composable("register") {
                            RegisterScreen(
                                viewModel = authViewModel,
                                onRegisterSuccess = {
                                    navController.navigate("home") {
                                        popUpTo("register") { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }

                        // Home
                        composable("home") {
                            HomeScreen(
                                onNavigateWater = { navController.navigate("water") },
                                onNavigateExercises = { /* TODO */ },
                                onNavigateHabits = { /* TODO */ },
                                onNavigateStats = { navController.navigate("stats") }
                            )
                        }

                        // Agua
                        composable("water") {
                            val userId = 1L
                            LaunchedEffect(Unit) { waterViewModel.setUserId(userId) }
                            WaterScreen(viewModel = waterViewModel)
                        }

                        // Estadísticas
                        composable("stats") {
                            val list by waterViewModel.waterList.collectAsState()
                            WaterStatsScreen(list)
                        }

                        // Ajustes
                        composable("settings") {
                            HydrationSettingsScreen { goal, start, end, interval ->
                                println("Guardado: $goal $start $end $interval")
                            }
                        }
                    }
                }
            }
        }
    }
}
