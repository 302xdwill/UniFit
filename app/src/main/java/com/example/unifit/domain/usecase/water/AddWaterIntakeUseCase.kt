package com.example.unifit.domain.usecase.water

import com.example.unifit.domain.model.WaterIntake
import com.example.unifit.domain.repository.WaterIntakeRepository

class AddWaterIntakeUseCase(
    private val repository: WaterIntakeRepository
) {
    suspend operator fun invoke(waterIntake: WaterIntake): Long {
        return repository.insertWaterIntake(waterIntake)
    }
}
