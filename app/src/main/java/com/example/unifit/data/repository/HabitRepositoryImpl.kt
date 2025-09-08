package com.example.unifit.data.repository

import com.example.unifit.data.local.dao.HabitDao
import com.example.unifit.data.local.entity.HabitEntity
import com.example.unifit.domain.model.Habit
import com.example.unifit.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HabitRepositoryImpl(
    private val habitDao: HabitDao
) : HabitRepository {

    override suspend fun insertHabit(habit: Habit): Long {
        val entity = habit.toEntity()
        return habitDao.insertHabit(entity)
    }

    override suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit.toEntity())
    }

    override suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit.toEntity())
    }

    override fun getHabitsByUser(userId: Long): Flow<List<Habit>> {
        return habitDao.getHabitsByUser(userId).map { list -> list.map { it.toDomain() } }
    }

    override fun getHabitsForDay(
        userId: Long,
        day: Int
    ): Flow<List<Habit>> {
        TODO("Not yet implemented")
    }
}

// 🔄 Mapeos de extensión
private fun Habit.toEntity() = HabitEntity(
    id = id,
    userId = userId,
    name = name,
    description = description,
    dayOfWeek = dayOfWeek,
    time = time,   // ahora es Long
    done = done
)

private fun HabitEntity.toDomain() = Habit(
    id = id,
    userId = userId,
    name = name,
    description = description,
    dayOfWeek = dayOfWeek,
    time = time,   // también Long
    done = done
)
