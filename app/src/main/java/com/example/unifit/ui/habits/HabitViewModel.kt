package com.example.unifit.ui.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unifit.domain.model.Habit
import com.example.unifit.domain.usecase.habit.AddHabitUseCase
import com.example.unifit.domain.usecase.habit.DeleteHabitUseCase
import com.example.unifit.domain.usecase.habit.GetHabitsByUserUseCase
import com.example.unifit.domain.usecase.habit.UpdateHabitUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HabitsViewModel(
    private val addHabitUseCase: AddHabitUseCase,
    private val getHabitsUseCase: GetHabitsByUserUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase
) : ViewModel() {

    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits: StateFlow<List<Habit>> = _habits

    init {
        loadHabits(1L) // 🔹 por ahora userId = 1 (luego leer de sesión)
    }

    fun loadHabits(userId: Long) {
        viewModelScope.launch {
            getHabitsUseCase(userId).collect { list ->
                _habits.value = list
            }
        }
    }

    fun insertHabit(habit: Habit) {
        viewModelScope.launch { addHabitUseCase(habit) }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch { updateHabitUseCase(habit) }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch { deleteHabitUseCase(habit) }
    }
}
