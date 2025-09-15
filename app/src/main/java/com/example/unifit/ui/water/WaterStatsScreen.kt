package com.example.unifit.ui.water

import android.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.unifit.domain.model.Habit
import com.example.unifit.domain.model.WaterIntake
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WaterStatsScreen(
    waterList: List<WaterIntake>,
    habits: List<Habit>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("📊 Estadísticas", style = MaterialTheme.typography.headlineSmall)

        // --- Agua ---
        WaterStatsChart(waterList)

        // --- Hábitos ---
        HabitStatsChart(habits)
    }
}

@Composable
fun WaterStatsChart(waterList: List<WaterIntake>) {
    Text("💧 Consumo de Agua (últimos 7 días)", style = MaterialTheme.typography.titleMedium)

    val sdf = SimpleDateFormat("dd/MM", Locale.getDefault())
    val grouped = waterList.groupBy { sdf.format(it.date) }
        .mapValues { it.value.sumOf { intake -> intake.amountMl } }

    val entries = grouped.entries.mapIndexed { index, (day, total) ->
        BarEntry(index.toFloat(), total.toFloat())
    }

    AndroidView(
        factory = { context ->
            BarChart(context).apply {
                val dataSet = BarDataSet(entries, "Agua (ml)").apply {
                    color = Color.parseColor("#2196F3")
                    valueTextColor = Color.BLACK
                    valueTextSize = 12f
                }
                this.data = BarData(dataSet)
                this.description = Description().apply { text = "Consumo diario" }
                this.animateY(1000)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}

@Composable
fun HabitStatsChart(habits: List<Habit>) {
    Text("✅ Cumplimiento de Hábitos", style = MaterialTheme.typography.titleMedium)

    val sdf = SimpleDateFormat("dd/MM", Locale.getDefault())
    val grouped = habits.groupBy { sdf.format(it.time) }
        .mapValues { it.value.count { habit -> habit.done } }

    val entries = grouped.entries.mapIndexed { index, (day, doneCount) ->
        Entry(index.toFloat(), doneCount.toFloat())
    }

    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                val dataSet = LineDataSet(entries, "Hábitos cumplidos").apply {
                    color = Color.parseColor("#4CAF50")
                    circleRadius = 5f
                    setCircleColor(Color.parseColor("#388E3C"))
                    lineWidth = 2f
                    valueTextColor = Color.BLACK
                }
                this.data = LineData(dataSet)
                this.description = Description().apply { text = "Progreso de hábitos" }
                this.animateX(1000)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}
