package com.example.unifit.ui.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unifit.R

@Composable
fun HomeScreen(
    onNavigateWater: () -> Unit,
    onNavigateHabits: () -> Unit,
    onNavigateStats: () -> Unit,
    username: String = "Usuario",
    completedGoals: Int = 0, // metas cumplidas
    onNavigateExercises: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // 🔹 Encabezado con logo, fuego y usuario
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Logo app
                Image(
                    painter = painterResource(id = R.drawable.logo_unifit), // coloca tu logo en res/drawable
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // 🔥 Contador de metas
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground), // icono fuego en drawable
                        contentDescription = "Metas cumplidas",
                        tint = Color.Red,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.width(4.dp))

                    // Animación contador
                    AnimatedContent(
                        targetState = completedGoals,
                        transitionSpec = {
                            // usamos fade + slide como ejemplo
                            slideInVertically(animationSpec = tween(600)) togetherWith
                                    slideOutVertically(animationSpec = tween(600))
                        },
                        label = "counter"
                    ) { target ->
                        Text(
                            text = target.toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }

                    Spacer(Modifier.width(16.dp))

                    // Usuario
                    Text(
                        text = username,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // 🔹 Tarjetas principales
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Agua
                Card(
                    onClick = onNavigateWater,
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF90CAF9)) // azul suave
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.LocalDrink, contentDescription = "Agua", tint = Color.White)
                        Spacer(Modifier.width(16.dp))
                        Text("Controlar Agua", fontSize = 18.sp, color = Color.White)
                    }
                }

                // Hábitos
                Card(
                    onClick = onNavigateHabits,
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFA5D6A7)) // verde suave
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = "Hábitos", tint = Color.White)
                        Spacer(Modifier.width(16.dp))
                        Text("Mis Hábitos", fontSize = 18.sp, color = Color.White)
                    }
                }

                // Estadísticas
                Card(
                    onClick = onNavigateStats,
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFCC80)) // naranja suave
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.BarChart, contentDescription = "Estadísticas", tint = Color.White)
                        Spacer(Modifier.width(16.dp))
                        Text("Estadísticas", fontSize = 18.sp, color = Color.White)
                    }
                }
            }
        }
    }
}
