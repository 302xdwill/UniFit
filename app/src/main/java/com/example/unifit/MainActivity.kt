package com.example.unifit

import android.annotation.SuppressLint
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
import com.example.unifit.ui.profile.ProfileScreen
import com.example.unifit.ui.settings.HydrationSettingsScreen
import com.example.unifit.ui.water.WaterScreen
import com.example.unifit.ui.stats.StatsScreen
import com.example.unifit.ui.water.WaterViewModel
import com.example.unifit.ui.water.WaterViewModelFactory
import com.example.unifit.ui.habits.HabitsScreen
import com.example.unifit.ui.habits.HabitsViewModel
import com.example.unifit.ui.habits.HabitsViewModelFactory
import com.example.unifit.ui.welcome.WelcomeScreen

class MainActivity : ComponentActivity() {
    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as UniFitApp

        // ✅ UseCases para auth
        val loginUC = com.example.unifit.domain.usecase.user.LoginUserUseCase(app.userRepository)
        val registerUC = com.example.unifit.domain.usecase.user.RegisterUserUseCase(app.userRepository)
        val logoutUC = com.example.unifit.domain.usecase.user.LogoutUserUseCase(app.sessionManager)
        val getUserUC = com.example.unifit.domain.usecase.user.GetUserByIdUseCase(app.userRepository)

        // ✅ UseCases para agua
        val addWaterUC = com.example.unifit.domain.usecase.water.AddWaterIntakeUseCase(app.waterRepository)
        val getWaterUC = com.example.unifit.domain.usecase.water.GetWaterIntakesUseCase(app.waterRepository)
        val updateWaterUC = com.example.unifit.domain.usecase.water.UpdateWaterIntakeUseCase(app.waterRepository)
        val deleteWaterUC = com.example.unifit.domain.usecase.water.DeleteWaterIntakeUseCase(app.waterRepository)

        // ✅ UseCases para hábitos
        val addHabitUC = com.example.unifit.domain.usecase.habit.AddHabitUseCase(app.habitRepository)
        val getHabitsUC = com.example.unifit.domain.usecase.habit.GetHabitsUseCase(app.habitRepository)
        val updateHabitUC = com.example.unifit.domain.usecase.habit.UpdateHabitUseCase(app.habitRepository)
        val deleteHabitUC = com.example.unifit.domain.usecase.habit.DeleteHabitUseCase(app.habitRepository)

        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                // ✅ ViewModels
                val authFactory = AuthViewModelFactory(
                    loginUC, registerUC, logoutUC, app.sessionManager, getUserUC
                )
                val authViewModel = ViewModelProvider(this, authFactory)[AuthViewModel::class.java]

                val waterFactory = WaterViewModelFactory(
                    addWaterUC, getWaterUC, updateWaterUC, deleteWaterUC
                )
                val waterViewModel = ViewModelProvider(this, waterFactory)[WaterViewModel::class.java]

                val habitsFactory = HabitsViewModelFactory(
                    addHabitUC, getHabitsUC, updateHabitUC, deleteHabitUC
                )
                val habitsViewModel = ViewModelProvider(this, habitsFactory)[HabitsViewModel::class.java]

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
                                userName = "Usuario", // ⚡ Reemplazar con authViewModel.user?.name
                                onNavigateWater = { navController.navigate("water") },
                                onNavigateHabits = { navController.navigate("habits") },
                                onNavigateStats = { navController.navigate("stats") },
                                onNavigateSettings = { navController.navigate("settings") },
                                onNavigateProfile = { navController.navigate("profile") }
                            )
                        }

                        // Agua
                        composable("water") {
                            val userId = 1L
                            LaunchedEffect(Unit) { waterViewModel.setUserId(userId) }
                            WaterScreen(viewModel = waterViewModel)
                        }

                        // Hábitos
                        composable("habits") {
                            HabitsScreen(viewModel = habitsViewModel)
                        }

                        // Estadísticas
                        composable("stats") {
                            val waterList by waterViewModel.waterList.collectAsState()
                            val habits by habitsViewModel.habits.collectAsState()
                            StatsScreen(waterList, habits)
                        }

                        // Ajustes
                        composable("settings") {
                            HydrationSettingsScreen(
                                onSaveHydration = { goal, start, end, interval ->
                                    println("Guardado: $goal $start $end $interval")
                                },
                                habits = habitsViewModel.habits.value,
                                onSaveHabit = { habitsViewModel.insertHabit(it) },
                                onUpdateHabit = { habitsViewModel.updateHabit(it) },
                                onDeleteHabit = { habitsViewModel.deleteHabit(it) }
                            )
                        }

                        // Perfil
                        composable("profile") {
                            ProfileScreen(
                                userName = "Usuario Demo",
                                userEmail = "demo@email.com",
                                onEditProfile = { /* TODO abrir pantalla editar perfil */ },
                                onLogout = {
                                    authViewModel.logout()
                                    navController.navigate("login") {
                                        popUpTo("home") { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
