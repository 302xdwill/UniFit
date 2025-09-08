package com.example.unifit.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HydrationSettingsScreen(
    onSave: (goal: Int, start: Int, end: Int, interval: Int) -> Unit
) {
    var dailyGoal by remember { mutableStateOf("") }
    var startHour by remember { mutableStateOf("") }
    var endHour by remember { mutableStateOf("") }
    var interval by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = dailyGoal,
            onValueChange = { dailyGoal = it },
            label = { Text("Meta diaria (ml)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = startHour,
            onValueChange = { startHour = it },
            label = { Text("Hora inicio (ej: 8)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = endHour,
            onValueChange = { endHour = it },
            label = { Text("Hora fin (ej: 22)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = interval,
            onValueChange = { interval = it },
            label = { Text("Intervalo (minutos)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                val goal = dailyGoal.toIntOrNull() ?: 2000
                val start = startHour.toIntOrNull() ?: 8
                val end = endHour.toIntOrNull() ?: 22
                val intv = interval.toIntOrNull() ?: 60
                onSave(goal, start, end, intv)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}
