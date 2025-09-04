package com.example.unifit.ui.water

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.unifit.domain.usecase.water.AddWaterIntakeUseCase
import com.example.unifit.domain.usecase.water.DeleteWaterIntakeUseCase
import com.example.unifit.domain.usecase.water.GetWaterIntakesUseCase
import com.example.unifit.domain.usecase.water.UpdateWaterIntakeUseCase

class WaterViewModelFactory(
    private val addWaterUseCase: AddWaterIntakeUseCase,
    private val getWaterUseCase: GetWaterIntakesUseCase,
    private val updateWaterUseCase: UpdateWaterIntakeUseCase,
    private val deleteWaterUseCase: DeleteWaterIntakeUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaterViewModel::class.java)) {
            return WaterViewModel(
                addWaterUseCase = addWaterUseCase,
                getWaterUseCase = getWaterUseCase,
                updateWaterUseCase = updateWaterUseCase,
                deleteWaterUseCase = deleteWaterUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
