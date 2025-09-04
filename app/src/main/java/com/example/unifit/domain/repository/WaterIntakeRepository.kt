package com.example.unifit.domain.repository

import com.example.unifit.domain.model.WaterIntake
import kotlinx.coroutines.flow.Flow

interface WaterIntakeRepository {
    suspend fun insertWaterIntake(water: WaterIntake): Long
    fun getWaterIntakes(userId: Long): Flow<List<WaterIntake>>
    suspend fun updateWaterIntake(water: WaterIntake)
    suspend fun deleteWaterIntake(water: WaterIntake)
}