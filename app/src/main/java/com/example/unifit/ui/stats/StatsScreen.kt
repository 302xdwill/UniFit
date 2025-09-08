package com.example.unifit.ui.stats

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unifit.domain.model.Habit
import com.example.unifit.domain.model.WaterIntake

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    waterList: List<WaterIntake>,
    habitList: List<Habit>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Estadísticas") }) }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 📊 Agua
            Text("Consumo de agua", style = MaterialTheme.typography.titleMedium)
            if (waterList.isEmpty()) {
                Text("Aún no registras consumo 💧")
            } else {
                val total = waterList.sumOf { it.amountMl }
                Text("Total de agua: $total ml")
            }

            Divider()

            // 📅 Hábitos
            Text("Hábitos completados", style = MaterialTheme.typography.titleMedium)
            if (habitList.isEmpty()) {
                Text("No tienes hábitos registrados 📭")
            } else {
                val doneCount = habitList.count { it.done }
                Text("Completados: $doneCount de ${habitList.size}")
            }
        }
    }
}
