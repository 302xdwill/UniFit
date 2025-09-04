package com.example.unifit.ui.water

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unifit.domain.model.WaterIntake
import java.text.SimpleDateFormat
import java.util.*

enum class StatsRange { WEEKLY, MONTHLY, YEARLY }

@Composable
fun WaterStatsScreen(waterList: List<WaterIntake>) {
    var selected = StatsRange.WEEKLY
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Estadísticas", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { selected = StatsRange.WEEKLY }) { Text("Semanal") }
            Button(onClick = { selected = StatsRange.MONTHLY }) { Text("Mensual") }
            Button(onClick = { selected = StatsRange.YEARLY }) { Text("Anual") }
        }
        Spacer(Modifier.height(12.dp))
        val data = when (selected) {
            StatsRange.WEEKLY -> processData(waterList, "dd/MM")
            StatsRange.MONTHLY -> processData(waterList, "MM/yyyy")
            StatsRange.YEARLY -> processData(waterList, "yyyy")
        }
        StatsChart(data)
    }
}

@Composable
fun StatsChart(data: Map<String, Int>) {
    if (data.isEmpty()) Text("No hay datos")
    else {
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(data.entries.toList().size) { idx ->
                val entry = data.entries.toList()[idx]
                Column(modifier = Modifier.padding(8.dp).width(70.dp), verticalArrangement = Arrangement.Bottom) {
                    Box(modifier = Modifier.height((entry.value / 10).dp).fillMaxWidth().background(MaterialTheme.colorScheme.primary))
                    Spacer(Modifier.height(6.dp))
                    Text(entry.key, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

fun processData(list: List<WaterIntake>, pattern: String): Map<String, Int> {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return list.groupBy { sdf.format(it.date) }.mapValues { (_, v) -> v.sumOf { it.amountMl } }
}