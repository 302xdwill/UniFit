package com.example.unifit.domain.usecase.water

import com.example.unifit.domain.model.WaterIntake
import com.example.unifit.domain.repository.WaterIntakeRepository

class DeleteWaterIntakeUseCase(private val repo: WaterIntakeRepository) {
    suspend operator fun invoke(water: WaterIntake) = repo.deleteWaterIntake(water)
}
