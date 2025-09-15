package com.example.unifit.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unifit.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _dailyGoal = MutableStateFlow(2000) // ml por defecto
    val dailyGoal: StateFlow<Int> = _dailyGoal

    private val _startHour = MutableStateFlow("08:00")
    val startHour: StateFlow<String> = _startHour

    private val _endHour = MutableStateFlow("22:00")
    val endHour: StateFlow<String> = _endHour

    private val _interval = MutableStateFlow(60)
    val interval: StateFlow<Int> = _interval

    init {
        // Cargar ajustes guardados desde DataStore
        viewModelScope.launch {
            sessionManager.dailyGoal.collect { _dailyGoal.value = it ?: 2000 }
        }
        viewModelScope.launch {
            sessionManager.startHour.collect { _startHour.value = it ?: "08:00" }
        }
        viewModelScope.launch {
            sessionManager.endHour.collect { _endHour.value = it ?: "22:00" }
        }
        viewModelScope.launch {
            sessionManager.interval.collect { _interval.value = it ?: 60 }
        }
    }

    fun saveHydrationSettings(dailyGoal: Int, startHour: String, endHour: String, interval: Int) {
        viewModelScope.launch {
            sessionManager.saveDailyGoal(dailyGoal)
            sessionManager.saveStartHour(startHour)
            sessionManager.saveEndHour(endHour)
            sessionManager.saveInterval(interval)
        }
    }
}
