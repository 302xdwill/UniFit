package com.example.unifit.ui.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unifit.domain.model.Habit
import com.example.unifit.domain.usecase.habit.AddHabitUseCase
import com.example.unifit.domain.usecase.habit.DeleteHabitUseCase
import com.example.unifit.domain.usecase.habit.GetHabitsUseCase
import com.example.unifit.domain.usecase.habit.UpdateHabitUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HabitsViewModel(
    private val addHabitUC: AddHabitUseCase,
    private val getHabitsUC: GetHabitsUseCase,
    private val updateHabitUC: UpdateHabitUseCase,
    private val deleteHabitUC: DeleteHabitUseCase
) : ViewModel() {

    private val userId = 1L // ⚡ Puedes obtenerlo del SessionManager después

    val habits = getHabitsUC(userId).stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    fun insertHabit(habit: Habit) {
        viewModelScope.launch { addHabitUC(habit) }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch { updateHabitUC(habit) }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch { deleteHabitUC(habit) }
    }
}
