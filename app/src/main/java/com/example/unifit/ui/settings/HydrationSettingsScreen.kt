package com.example.unifit.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun HydrationSettingsScreen(onSave: (Int, String, String, Int) -> Unit) {
    var dailyText by remember { mutableStateOf("2000") }
    var startHour by remember { mutableStateOf("08:00") }
    var endHour by remember { mutableStateOf("22:00") }
    var intervalText by remember { mutableStateOf("120") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Ajustes de Hidratación", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = dailyText, onValueChange = { dailyText = it.filter { ch->ch.isDigit() } }, label = { Text("Meta diaria (ml)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = startHour, onValueChange = { startHour = it }, label = { Text("Hora inicio (HH:mm)") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = endHour, onValueChange = { endHour = it }, label = { Text("Hora fin (HH:mm)") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = intervalText, onValueChange = { intervalText = it.filter { ch->ch.isDigit() } }, label = { Text("Intervalo (minutos)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Spacer(Modifier.height(12.dp))
        Button(onClick = {
            val g = dailyText.toIntOrNull() ?: -1
            val itv = intervalText.toIntOrNull() ?: -1
            when {
                g <= 0 -> error = "Meta inválida"
                itv <= 0 -> error = "Intervalo inválido"
                startHour.isBlank() || endHour.isBlank() -> error = "Completa horas"
                else -> {
                    error = null
                    onSave(g, startHour, endHour, itv)
                }
            }
        }, modifier = Modifier.fillMaxWidth()) { Text("Guardar") }
    }
}