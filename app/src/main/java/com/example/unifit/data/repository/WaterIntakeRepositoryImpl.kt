package com.example.unifit.data.repository

import com.example.unifit.data.local.dao.WaterIntakeDao
import com.example.unifit.data.local.entity.WaterIntakeEntity
import com.example.unifit.domain.model.WaterIntake
import com.example.unifit.domain.repository.WaterIntakeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WaterIntakeRepositoryImpl(
    private val waterDao: WaterIntakeDao
) : WaterIntakeRepository {

    override suspend fun insertWaterIntake(waterIntake: WaterIntake): Long {
        val entity = WaterIntakeEntity(
            id = waterIntake.id,
            userId = waterIntake.userId,
            amountMl = waterIntake.amountMl,
            date = waterIntake.date
        )
        return waterDao.insertWaterIntake(entity)
    }

    override suspend fun updateWaterIntake(waterIntake: WaterIntake) {
        val entity = WaterIntakeEntity(
            id = waterIntake.id,
            userId = waterIntake.userId,
            amountMl = waterIntake.amountMl,
            date = waterIntake.date
        )
        waterDao.updateWaterIntake(entity)
    }

    override suspend fun deleteWaterIntake(waterIntake: WaterIntake) {
        val entity = WaterIntakeEntity(
            id = waterIntake.id,
            userId = waterIntake.userId,
            amountMl = waterIntake.amountMl,
            date = waterIntake.date
        )
        waterDao.deleteWaterIntake(entity)
    }

    override fun getWaterIntakesByUser(userId: Long): Flow<List<WaterIntake>> {
        return waterDao.getWaterIntakesByUser(userId).map { list ->
            list.map { entity ->
                WaterIntake(
                    id = entity.id,
                    userId = entity.userId,
                    amountMl = entity.amountMl,
                    date = entity.date
                )
            }
        }
    }
}
