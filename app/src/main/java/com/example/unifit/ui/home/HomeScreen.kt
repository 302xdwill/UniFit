package com.example.unifit.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.unifit.R
import java.util.*

@Composable
fun HomeScreen(
    userName: String,
    onNavigateWater: () -> Unit,
    onNavigateHabits: () -> Unit,
    onNavigateStats: () -> Unit,
    onNavigateSettings: () -> Unit,
    onNavigateProfile: () -> Unit
) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)

    val motivationalMessage = when (hour) {
        in 5..11 -> "¡Buenos días $userName! Hoy es un gran día para hidratarte 💧"
        in 12..18 -> "¡Vamos $userName! La tarde es perfecta para cumplir tus metas 🚀"
        else -> "¡Buenas noches $userName! Termina tu día con disciplina ✨"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header con logo y usuario
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_unifit), // 🔹 tu logo en drawable
                contentDescription = "Logo",
                modifier = Modifier.size(48.dp)
            )

            TextButton(onClick = onNavigateProfile) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1565C0)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mensaje motivador
        Text(
            text = motivationalMessage,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botones principales
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            HomeButton("Registrar Agua", Icons.Default.LocalDrink, Color(0xFF42A5F5)) {
                onNavigateWater()
            }
            HomeButton("Mis Hábitos", Icons.Default.Today, Color(0xFF66BB6A)) {
                onNavigateHabits()
            }
            HomeButton("Estadísticas", Icons.Default.TrendingUp, Color(0xFFFFA726)) {
                onNavigateStats()
            }
            HomeButton("Ajustes", Icons.Default.Settings, Color(0xFFAB47BC)) {
                onNavigateSettings()
            }
        }
    }
}

@Composable
fun HomeButton(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(icon, contentDescription = title, tint = Color.White)
        Spacer(modifier = Modifier.width(12.dp))
        Text(title, color = Color.White, style = MaterialTheme.typography.bodyLarge)
    }
}
