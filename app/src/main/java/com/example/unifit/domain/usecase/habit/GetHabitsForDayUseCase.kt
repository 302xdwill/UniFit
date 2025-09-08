package com.example.unifit.domain.usecase.habit

import com.example.unifit.domain.model.Habit
import com.example.unifit.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow

class GetHabitsForDayUseCase(private val repo: HabitRepository) {
    operator fun invoke(userId: Long, day: Int): Flow<List<Habit>> {
        return repo.getHabitsForDay(userId, day)
    }
}
