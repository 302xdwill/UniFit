package com.example.unifit.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.unifit.domain.model.Habit
import com.example.unifit.ui.habits.components.HabitDialog

@Composable
fun HydrationSettingsScreen(
    dailyGoalInit: Int = 2000,
    startHourInit: Int = 8,
    endHourInit: Int = 22,
    intervalInit: Int = 60,
    habits: List<Habit> = emptyList(),
    onSaveHydration: (Int, Int, Int, Int) -> Unit,
    onSaveHabit: (Habit) -> Unit,
    onUpdateHabit: (Habit) -> Unit,
    onDeleteHabit: (Habit) -> Unit
) {
    var dailyGoal by remember { mutableStateOf(dailyGoalInit.toString()) }
    var startHour by remember { mutableStateOf(startHourInit.toString()) }
    var endHour by remember { mutableStateOf(endHourInit.toString()) }
    var interval by remember { mutableStateOf(intervalInit.toString()) }

    var showDialog by remember { mutableStateOf(false) }
    var habitToEdit by remember { mutableStateOf<Habit?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // --- 💧 Configuración de Agua
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("💧 Configuración de Agua", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))

                    OutlinedTextField(
                        value = dailyGoal,
                        onValueChange = { dailyGoal = it },
                        label = { Text("Meta diaria (ml)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = startHour,
                        onValueChange = { startHour = it },
                        label = { Text("Hora inicio (0-23)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = endHour,
                        onValueChange = { endHour = it },
                        label = { Text("Hora fin (0-23)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = interval,
                        onValueChange = { interval = it },
                        label = { Text("Intervalo recordatorio (min)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            onSaveHydration(
                                dailyGoal.toIntOrNull() ?: 2000,
                                startHour.toIntOrNull() ?: 8,
                                endHour.toIntOrNull() ?: 22,
                                interval.toIntOrNull() ?: 60
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar ajustes de agua")
                    }
                }
            }
        }

        // --- 📅 Configuración de Hábitos
        item {
            Text("📅 Configuración de Hábitos", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
        }

        items(habits) { habit ->
            HabitItemSettings(
                habit = habit,
                onEdit = {
                    habitToEdit = habit
                    showDialog = true
                },
                onDelete = { onDeleteHabit(habit) }
            )
        }

        item {
            ExtendedFloatingActionButton(
                onClick = { habitToEdit = null; showDialog = true },
                icon = { Icon(Icons.Default.Add, contentDescription = "Añadir hábito") },
                text = { Text("Añadir Hábito") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
    }

    // --- Diálogo para crear/editar hábito
    if (showDialog) {
        HabitDialog(
            initialHabit = habitToEdit,
            onDismiss = { showDialog = false },
            onSave = { habit ->
                if (habit.id == 0L) onSaveHabit(habit) else onUpdateHabit(habit)
                showDialog = false
            }
        )
    }
}

@Composable
fun HabitItemSettings(
    habit: Habit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(habit.name, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                if (habit.description.isNotEmpty()) {
                    Text(habit.description, style = MaterialTheme.typography.bodyMedium)
                }
                Text("Día: ${habit.dayOfWeek}", style = MaterialTheme.typography.bodySmall)
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}
