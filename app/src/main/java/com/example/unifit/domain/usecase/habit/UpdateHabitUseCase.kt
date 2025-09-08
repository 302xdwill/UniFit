package com.example.unifit.domain.usecase.habit

import com.example.unifit.domain.model.Habit
import com.example.unifit.domain.repository.HabitRepository

class UpdateHabitUseCase(private val repo: HabitRepository) {
    suspend operator fun invoke(habit: Habit) {
        repo.updateHabit(habit)
    }
}
