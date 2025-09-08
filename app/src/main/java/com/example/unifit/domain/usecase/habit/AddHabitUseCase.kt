package com.example.unifit.domain.usecase.habit

import com.example.unifit.domain.model.Habit
import com.example.unifit.domain.repository.HabitRepository

class AddHabitUseCase(private val repo: HabitRepository) {
    suspend operator fun invoke(habit: Habit): Long {
        return repo.insertHabit(habit)
    }
}
