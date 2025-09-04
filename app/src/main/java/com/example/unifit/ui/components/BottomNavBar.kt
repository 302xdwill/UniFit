package com.example.unifit.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(val label: String, val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", "home", Icons.Default.Home),
        BottomNavItem("Agua", "water", Icons.Default.WaterDrop),
        BottomNavItem("Stats", "stats", Icons.Default.BarChart),
        BottomNavItem("Ajustes", "settings", Icons.Default.Settings)
    )

    NavigationBar {
        val navBack by navController.currentBackStackEntryAsState()
        val current = navBack?.destination?.route
        items.forEach { itItem ->
            NavigationBarItem(icon = { Icon(itItem.icon, contentDescription = itItem.label) },
                label = { Text(itItem.label) },
                selected = current == itItem.route,
                onClick = {
                    if (current != itItem.route) navController.navigate(itItem.route) {
                        popUpTo("home") { inclusive = false }
                        launchSingleTop = true
                    }
                })
        }
    }
}