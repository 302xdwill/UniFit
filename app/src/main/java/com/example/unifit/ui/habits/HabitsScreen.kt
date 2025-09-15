package com.example.unifit.ui.habits

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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

        if (habits.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Aún no tienes hábitos registrados 📝")
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                habits.forEach { habit ->
                    HabitCard(
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
        }

        Spacer(Modifier.height(16.dp))

        // Botón para añadir hábito
        Button(
            onClick = { habitToEdit = null; showDialog = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Añadir Hábito ➕")
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
fun HabitCard(
    habit: Habit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onToggleDone: (Habit) -> Unit
) {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())

    val scale by animateFloatAsState(if (habit.done) 1.2f else 1f, label = "")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (habit.done) Color(0xFFD0F0C0) else Color(0xFFE3F2FD)
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
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
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Marcar como hecho",
                        tint = if (habit.done) Color(0xFF2E7D32) else Color.Gray,
                        modifier = Modifier.scale(scale)
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
