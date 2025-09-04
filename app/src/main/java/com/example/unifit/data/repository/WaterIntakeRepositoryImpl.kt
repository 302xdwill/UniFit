package com.example.unifit.data.repository

import com.example.unifit.data.local.dao.WaterIntakeDao
import com.example.unifit.data.local.entity.WaterIntakeEntity
import com.example.unifit.domain.model.WaterIntake
import com.example.unifit.domain.repository.WaterIntakeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WaterIntakeRepositoryImpl(private val waterDao: WaterIntakeDao) : WaterIntakeRepository {
    override suspend fun insertWaterIntake(water: WaterIntake): Long {
        val entity = WaterIntakeEntity(id = water.id, userId = water.userId, amountMl = water.amountMl, date = water.date)
        return waterDao.insertWaterIntake(entity)
    }

    override fun getWaterIntakes(userId: Long): Flow<List<WaterIntake>> {
        return waterDao.getWaterIntakesByUser(userId).map { list ->
            list.map { entity ->
                WaterIntake(id = entity.id, userId = entity.userId, amountMl = entity.amountMl, date = entity.date)
            }
        }
    }

    override suspend fun updateWaterIntake(water: WaterIntake) {
        val entity = WaterIntakeEntity(id = water.id, userId = water.userId, amountMl = water.amountMl, date = water.date)
        waterDao.updateWaterIntake(entity)
    }

    override suspend fun deleteWaterIntake(water: WaterIntake) {
        val entity = WaterIntakeEntity(id = water.id, userId = water.userId, amountMl = water.amountMl, date = water.date)
        waterDao.deleteWaterIntake(entity)
    }
}