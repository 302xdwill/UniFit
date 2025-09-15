package com.example.unifit.ui.stats

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.unifit.domain.model.Habit
import com.example.unifit.domain.model.WaterIntake
import com.example.unifit.utils.DateUtils
import java.util.*

@Composable
fun StatsScreen(
    waterList: List<WaterIntake>,
    habits: List<Habit>
) {
    var range by remember { mutableStateOf("Semana") }
    val ranges = listOf("Semana", "Mes")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("📊 Estadísticas", style = MaterialTheme.typography.headlineSmall)

        // Selector de rango
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ranges.forEach { r ->
                FilterChip(
                    selected = range == r,
                    onClick = { range = r },
                    label = { Text(r) }
                )
            }
        }

        Divider()

        // Agua
        Text("💧 Consumo de Agua", style = MaterialTheme.typography.titleMedium)
        SimpleBarChart(
            data = waterList.groupBy { DateUtils.formatDay(it.date) }
                .mapValues { entry -> entry.value.sumOf { it.amountMl } }
        )

        Divider()

        // Hábitos
        Text("✅ Hábitos Cumplidos", style = MaterialTheme.typography.titleMedium)
        SimpleLineChart(
            data = habits.filter { it.done }
                .groupBy { DateUtils.formatDay(it.time) }
                .mapValues { entry -> entry.value.size }
        )
    }
}

/**
 * Dibujar gráfico de barras simple
 */
@Composable
fun SimpleBarChart(data: Map<String, Int>, modifier: Modifier = Modifier) {
    val labels = data.keys.toList()
    val values = data.values.toList()
    val max = (values.maxOrNull() ?: 1).toFloat()

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
    ) {
        val barWidth = size.width / (values.size * 2f)
        values.forEachIndexed { index, value ->
            val barHeight = (value / max) * size.height
            drawRoundRect(
                color = Color(0xFF42A5F5),
                topLeft = Offset(
                    x = index * 2f * barWidth,
                    y = size.height - barHeight
                ),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(8f, 8f)
            )
        }
    }
}

/**
 * Dibujar gráfico de líneas simple
 */
@Composable
fun SimpleLineChart(data: Map<String, Int>, modifier: Modifier = Modifier) {
    val labels = data.keys.toList()
    val values = data.values.toList()
    val max = (values.maxOrNull() ?: 1).toFloat()

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
    ) {
        if (values.isNotEmpty()) {
            val stepX = size.width / (values.size - 1).coerceAtLeast(1)
            values.forEachIndexed { index, value ->
                val x = index * stepX
                val y = size.height - (value / max) * size.height
                drawCircle(Color(0xFF66BB6A), radius = 8f, center = Offset(x, y))
                if (index > 0) {
                    val prevX = (index - 1) * stepX
                    val prevY = size.height - (values[index - 1] / max) * size.height
                    drawLine(
                        color = Color(0xFF66BB6A),
                        start = Offset(prevX, prevY),
                        end = Offset(x, y),
                        strokeWidth = 4f
                    )
                }
            }
        }
    }
}
