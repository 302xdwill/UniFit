package com.example.unifit.domain.usecase.water

import com.example.unifit.domain.model.WaterIntake
import com.example.unifit.domain.repository.WaterIntakeRepository

class UpdateWaterIntakeUseCase(private val repository: WaterIntakeRepository) {
    suspend operator fun invoke(water: WaterIntake) = repository.updateWaterIntake(water)
}