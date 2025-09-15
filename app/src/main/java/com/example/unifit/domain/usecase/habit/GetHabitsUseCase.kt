package com.example.unifit.domain.usecase.habit

import com.example.unifit.domain.repository.HabitRepository

class GetHabitsUseCase(private val repository: HabitRepository) {
    operator fun invoke(userId: Long) = repository.getHabitsByUser(userId)
}
