package com.example.unifit.data.local.dao

import androidx.room.*
import com.example.unifit.data.local.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Insert suspend fun insertHabit(habit: HabitEntity): Long

    @Update suspend fun updateHabit(habit: HabitEntity)

    @Delete suspend fun deleteHabit(habit: HabitEntity)

    @Query("SELECT * FROM habits WHERE userId = :userId ORDER BY dayOfWeek ASC, time ASC")
    fun getHabitsByUser(userId: Long): Flow<List<HabitEntity>>
}
