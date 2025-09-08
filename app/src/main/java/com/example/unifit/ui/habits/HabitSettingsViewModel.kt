package com.example.unifit.ui.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unifit.domain.model.Habit
import com.example.unifit.domain.usecase.habit.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HabitSettingsViewModel(
    private val addHabit: AddHabitUseCase,
    private val updateHabit: UpdateHabitUseCase,
    private val deleteHabit: DeleteHabitUseCase,
    private val getHabitsByUser: GetHabitsByUserUseCase
) : ViewModel() {

    private val _userId = MutableStateFlow<Long?>(null)

    val habitsAll: StateFlow<List<Habit>> =
        _userId.filterNotNull()
            .flatMapLatest { userId -> getHabitsByUser(userId) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setUser(userId: Long) {
        _userId.value = userId
    }

    fun add(habit: Habit) {
        viewModelScope.launch { addHabit(habit) }
    }

    fun update(habit: Habit) {
        viewModelScope.launch { updateHabit(habit) }
    }

    fun delete(habit: Habit) {
        viewModelScope.launch { deleteHabit(habit) }
    }
}
