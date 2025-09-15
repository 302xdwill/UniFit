package com.example.unifit.ui.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.unifit.domain.usecase.habit.AddHabitUseCase
import com.example.unifit.domain.usecase.habit.DeleteHabitUseCase
import com.example.unifit.domain.usecase.habit.GetHabitsUseCase
import com.example.unifit.domain.usecase.habit.UpdateHabitUseCase

class HabitsViewModelFactory(
    private val addHabitUC: AddHabitUseCase,
    private val getHabitsUC: GetHabitsUseCase,
    private val updateHabitUC: UpdateHabitUseCase,
    private val deleteHabitUC: DeleteHabitUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitsViewModel::class.java)) {
            return HabitsViewModel(
                addHabitUC,
                getHabitsUC,
                updateHabitUC,
                deleteHabitUC
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
