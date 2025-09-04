package com.example.unifit.data.local.dao

import androidx.room.*
import com.example.unifit.data.local.entity.WaterIntakeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterIntakeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWaterIntake(water: WaterIntakeEntity): Long

    @Query("SELECT * FROM water_intake WHERE userId = :userId ORDER BY date DESC")
    fun getWaterIntakesByUser(userId: Long): Flow<List<WaterIntakeEntity>>

    @Update
    suspend fun updateWaterIntake(water: WaterIntakeEntity)

    @Delete
    suspend fun deleteWaterIntake(water: WaterIntakeEntity)
}