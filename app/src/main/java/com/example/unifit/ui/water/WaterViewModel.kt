package com.example.unifit.ui.water

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unifit.domain.model.WaterIntake
import com.example.unifit.domain.usecase.water.AddWaterIntakeUseCase
import com.example.unifit.domain.usecase.water.GetWaterIntakesUseCase
import com.example.unifit.domain.usecase.water.UpdateWaterIntakeUseCase
import com.example.unifit.domain.usecase.water.DeleteWaterIntakeUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date

class WaterViewModel(
    private val addWaterUseCase: AddWaterIntakeUseCase,
    private val getWaterUseCase: GetWaterIntakesUseCase,
    private val updateWaterUseCase: UpdateWaterIntakeUseCase,
    private val deleteWaterUseCase: DeleteWaterIntakeUseCase
) : ViewModel() {

    private val _currentUserId = MutableStateFlow<Long?>(null)

    val waterList: StateFlow<List<WaterIntake>> =
        _currentUserId.filterNotNull()
            .flatMapLatest { id -> getWaterUseCase(id) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    fun setUserId(userId: Long) { _currentUserId.value = userId }

    fun addWater(amountMl: Int) {
        val userId = _currentUserId.value ?: return
        viewModelScope.launch {
            try {
                val w = WaterIntake(id = 0, userId = userId, amountMl = amountMl, date = Date())
                addWaterUseCase(w)
                _message.value = "Consumo agregado"
            } catch (e: Exception) {
                _message.value = "Error: ${e.message}"
            }
        }
    }

    fun updateWater(water: WaterIntake) {
        viewModelScope.launch {
            try {
                updateWaterUseCase(water)
                _message.value = "Registro actualizado"
            } catch (e: Exception) {
                _message.value = "Error al actualizar"
            }
        }
    }

    fun deleteWater(water: WaterIntake) {
        viewModelScope.launch {
            try {
                deleteWaterUseCase(water)
                _message.value = "Registro eliminado"
            } catch (e: Exception) {
                _message.value = "Error al eliminar"
            }
        }
    }

    fun clearMessage() { _message.value = null }
}