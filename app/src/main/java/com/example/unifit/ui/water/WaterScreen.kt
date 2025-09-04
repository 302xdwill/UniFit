package com.example.unifit.ui.water

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.unifit.domain.model.WaterIntake
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min

@Composable
fun WaterScreen(viewModel: WaterViewModel) {
    val waterList by viewModel.waterList.collectAsState()
    val message by viewModel.message.collectAsState()

    var inputAmount by remember { mutableStateOf("") }

    // Meta diaria (puedes luego hacerlo configurable desde ajustes)
    val dailyGoal = 2000
    val totalToday = waterList
        .filter { isToday(it.date) }
        .sumOf { it.amountMl }

    val progress = (totalToday.toFloat() / dailyGoal).coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(targetValue = progress, label = "")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Animación del vaso
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Canvas(modifier = Modifier.fillMaxSize(0.5f)) {
                val width = size.width
                val height = size.height
                val waterHeight = height * animatedProgress

                // Contorno vaso
                drawRoundRect(
                    color = Color.Gray,
                    cornerRadius = CornerRadius(30f, 30f),
                    size = size
                )

                // Agua
                drawRoundRect(
                    color = Color(0xFF2196F3),
                    cornerRadius = CornerRadius(30f, 30f),
                    size = androidx.compose.ui.geometry.Size(width, waterHeight),
                    topLeft = androidx.compose.ui.geometry.Offset(0f, height - waterHeight)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Hoy: $totalToday ml / $dailyGoal ml",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(Modifier.height(16.dp))

        // Input manual
        OutlinedTextField(
            value = inputAmount,
            onValueChange = { inputAmount = it },
            label = { Text("Cantidad (ml)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    inputAmount.toIntOrNull()?.let {
                        viewModel.addWater(it)
                        inputAmount = ""
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Agregar")
            }
            Button(
                onClick = { viewModel.addWater(250) },
                modifier = Modifier.weight(1f)
            ) { Text("+250ml") }
            Button(
                onClick = { viewModel.addWater(500) },
                modifier = Modifier.weight(1f)
            ) { Text("+500ml") }
        }

        Spacer(Modifier.height(16.dp))

        // Historial agrupado por fecha
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val grouped = waterList.groupBy { formatDate(it.date) }

            grouped.forEach { (date, items) ->
                item {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                items(items) { water ->
                    WaterItem(
                        water = water,
                        onEdit = { updated ->
                            viewModel.updateWater(updated)
                        },
                        onDelete = { viewModel.deleteWater(water) }
                    )
                }
            }
        }

        message?.let {
            Text(it, color = Color.Red)
            LaunchedEffect(it) { viewModel.clearMessage() }
        }
    }
}

@Composable
fun WaterItem(water: WaterIntake, onEdit: (WaterIntake) -> Unit, onDelete: () -> Unit) {
    val time = remember(water.date) {
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(water.date)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("${water.amountMl} ml", fontWeight = FontWeight.Bold)
                Text(time, style = MaterialTheme.typography.bodySmall)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = {
                    val new = water.copy(amountMl = water.amountMl + 50)
                    onEdit(new)
                }) { Text("Editar") }

                OutlinedButton(onClick = onDelete) { Text("Eliminar") }
            }
        }
    }
}

private fun isToday(date: Date): Boolean {
    val cal1 = Calendar.getInstance().apply { time = date }
    val cal2 = Calendar.getInstance()
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

private fun formatDate(date: Date): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(date)
}
