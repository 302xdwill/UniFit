package com.example.unifit.domain.repository

import com.example.unifit.domain.model.WaterIntake
import kotlinx.coroutines.flow.Flow

interface WaterIntakeRepository {
    suspend fun insertWaterIntake(waterIntake: WaterIntake): Long
    suspend fun updateWaterIntake(waterIntake: WaterIntake)
    suspend fun deleteWaterIntake(waterIntake: WaterIntake)
    fun getWaterIntakesByUser(userId: Long): Flow<List<WaterIntake>>
}
