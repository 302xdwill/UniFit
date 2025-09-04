package com.example.unifit.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.animation.animateContentSize
import com.example.unifit.R

@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // permite deslizar
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo animado
        var expanded by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) { expanded = true }

        Image(
            painter = painterResource(id = R.drawable.logo_unifit), // 👈 pon tu logo en res/drawable/logo_unifit.png
            contentDescription = "UniFit Logo",
            modifier = Modifier
                .size(if (expanded) 220.dp else 100.dp)
                .animateContentSize()
                .padding(bottom = 32.dp)
        )

        Text(
            text = "Bienvenido a UniFit",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tu compañero para mantenerte hidratado, saludable y activo 💧💪",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onStartClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Comenzar")
            Spacer(Modifier.width(8.dp))
            Text("Comenzar")
        }

        Spacer(modifier = Modifier.height(64.dp))

        // 👇 Opciones adicionales (relleno visual)
        Text(
            text = "✨ Funciones principales:",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(Modifier.height(8.dp))

        FeatureItem("Seguimiento de agua", Icons.Default.WaterDrop)
        FeatureItem("Tus hábitos saludables", Icons.Default.Favorite)
        FeatureItem("Progreso y estadísticas", Icons.Default.BarChart)
    }
}

@Composable
fun FeatureItem(title: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Icon(icon, contentDescription = title, tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.width(12.dp))
        Text(title, style = MaterialTheme.typography.bodyLarge)
    }
}
