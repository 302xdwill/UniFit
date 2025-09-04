package com.example.unifit.domain.usecase.water

import com.example.unifit.domain.model.WaterIntake
import com.example.unifit.domain.repository.WaterIntakeRepository

class DeleteWaterIntakeUseCase(private val repository: WaterIntakeRepository) {
    suspend operator fun invoke(water: WaterIntake) = repository.deleteWaterIntake(water)
}