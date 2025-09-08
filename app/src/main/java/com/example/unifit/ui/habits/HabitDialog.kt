package com.example.unifit.ui.habits.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.unifit.domain.model.Habit
import java.util.*

@Composable
fun HabitDialog(
    initialHabit: Habit?,
    onDismiss: () -> Unit,
    onSave: (Habit) -> Unit
) {
    var name by remember { mutableStateOf(initialHabit?.name ?: "") }
    var description by remember { mutableStateOf(initialHabit?.description ?: "") }
    var dayOfWeek by remember { mutableStateOf(initialHabit?.dayOfWeek ?: 1) }
    var hour by remember { mutableStateOf(8) }
    var minute by remember { mutableStateOf(0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                }
                onSave(
                    Habit(
                        id = initialHabit?.id ?: 0,
                        userId = initialHabit?.userId ?: 1L,
                        name = name,
                        description = description,
                        dayOfWeek = dayOfWeek,
                        time = calendar.time,
                        done = initialHabit?.done ?: false
                    )
                )
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        },
        title = { Text(if (initialHabit == null) "Nuevo Hábito" else "Editar Hábito") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") })

                // Selección de día (simplificada)
                OutlinedTextField(
                    value = dayOfWeek.toString(),
                    onValueChange = { dayOfWeek = it.toIntOrNull() ?: 1 },
                    label = { Text("Día de la semana (1=Lunes)") }
                )

                // Selección de hora
                OutlinedTextField(
                    value = "$hour:$minute",
                    onValueChange = {
                        val parts = it.split(":")
                        if (parts.size == 2) {
                            hour = parts[0].toIntOrNull() ?: 8
                            minute = parts[1].toIntOrNull() ?: 0
                        }
                    },
                    label = { Text("Hora (HH:mm)") }
                )
            }
        }
    )
}
