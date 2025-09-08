package com.example.unifit.domain.repository

import com.example.unifit.domain.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun insertHabit(habit: Habit): Long
    suspend fun updateHabit(habit: Habit)
    suspend fun deleteHabit(habit: Habit)

    fun getHabitsByUser(userId: Long): Flow<List<Habit>> // toda la semana
    fun getHabitsForDay(userId: Long, day: Int): Flow<List<Habit>> // solo un día
}
