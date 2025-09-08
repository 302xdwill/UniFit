package com.example.unifit.ui.habits

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unifit.domain.model.Habit
import com.example.unifit.ui.habits.components.HabitDialog
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HabitsScreen(
    viewModel: HabitsViewModel
) {
    val habits by viewModel.habits.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var habitToEdit by remember { mutableStateOf<Habit?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Mis Hábitos", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        // Lista de hábitos
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(habits) { habit ->
                HabitItem(
                    habit = habit,
                    onEdit = {
                        habitToEdit = habit
                        showDialog = true
                    },
                    onDelete = { viewModel.deleteHabit(habit) },
                    onToggleDone = { updated ->
                        viewModel.updateHabit(updated.copy(done = !updated.done))
                    }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Botón para añadir hábito
        Button(
            onClick = { habitToEdit = null; showDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Añadir Hábito")
        }
    }

    // Diálogo para añadir/editar hábito
    if (showDialog) {
        HabitDialog(
            initialHabit = habitToEdit,
            onDismiss = { showDialog = false },
            onSave = { habit ->
                if (habit.id == 0L) {
                    viewModel.insertHabit(habit)
                } else {
                    viewModel.updateHabit(habit)
                }
                showDialog = false
            }
        )
    }
}

@Composable
fun HabitItem(
    habit: Habit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onToggleDone: (Habit) -> Unit
) {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(habit.name, style = MaterialTheme.typography.titleMedium)
                Text(habit.description, style = MaterialTheme.typography.bodyMedium)
                Text(
                    "Hora: ${sdf.format(habit.time)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = { onToggleDone(habit) }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Marcar",
                        tint = if (habit.done) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
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
