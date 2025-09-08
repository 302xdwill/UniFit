package com.example.unifit.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", "home", Icons.Default.Home),
        BottomNavItem("Ajustes", "settings", Icons.Default.Settings),
        BottomNavItem("Atrás", "back", Icons.Default.ArrowBack)
    )

    NavigationBar {
        val navBack by navController.currentBackStackEntryAsState()
        val current = navBack?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = current == item.route,
                onClick = {
                    when (item.route) {
                        "back" -> navController.popBackStack()
                        else -> {
                            if (current != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo("home") { inclusive = false }
                                    launchSingleTop = true
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}
