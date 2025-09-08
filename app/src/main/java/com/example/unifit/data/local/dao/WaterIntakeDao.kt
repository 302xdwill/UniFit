package com.example.unifit.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.unifit.data.local.entity.WaterIntakeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterIntakeDao {
    @Insert suspend fun insertWaterIntake(water: WaterIntakeEntity): Long
    @Query("SELECT * FROM water_intakes WHERE userId = :userId ORDER BY date DESC")
    fun getWaterIntakesByUser(userId: Long): kotlinx.coroutines.flow.Flow<List<WaterIntakeEntity>>
    @Update suspend fun updateWaterIntake(water: WaterIntakeEntity)
    @Delete suspend fun deleteWaterIntake(water: WaterIntakeEntity)
}

