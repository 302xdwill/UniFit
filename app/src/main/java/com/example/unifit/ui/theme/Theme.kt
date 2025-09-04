package com.example.unifit.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 🎨 Colores principales de UniFit
private val BluePrimary = Color(0xFF4FC3F7) // Agua
private val GreenSecondary = Color(0xFF81C784) // Hábitos saludables
private val OrangeAccent = Color(0xFFFFB74D) // Energía
private val LightBackground = Color(0xFFF5F5F5) // Fondo

private val LightColors = lightColorScheme(
    primary = BluePrimary,
    secondary = GreenSecondary,
    tertiary = OrangeAccent,
    background = LightBackground,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun UniFitTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),
        content = content
    )
}
