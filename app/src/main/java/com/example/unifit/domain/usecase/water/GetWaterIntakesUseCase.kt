package com.example.unifit.domain.usecase.water

import com.example.unifit.domain.model.WaterIntake
import com.example.unifit.domain.repository.WaterIntakeRepository
import kotlinx.coroutines.flow.Flow

class GetWaterIntakesUseCase(
    private val repository: WaterIntakeRepository
) {
    operator fun invoke(userId: Long): Flow<List<WaterIntake>> {
        return repository.getWaterIntakesByUser(userId)
    }
}
