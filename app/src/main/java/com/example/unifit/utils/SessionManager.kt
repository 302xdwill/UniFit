package com.example.unifit.utils

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("session_prefs")

class SessionManager(private val context: Context) {

    companion object {
        private val USER_ID_KEY = longPreferencesKey("user_id")

        // 🔹 Nuevas claves para hidratación
        private val DAILY_GOAL_KEY = intPreferencesKey("daily_goal")
        private val START_HOUR_KEY = stringPreferencesKey("start_hour")
        private val END_HOUR_KEY = stringPreferencesKey("end_hour")
        private val INTERVAL_KEY = intPreferencesKey("interval")
    }

    // -----------------------------
    // ✅ Sesión de Usuario
    // -----------------------------
    suspend fun saveUserId(userId: Long) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userId
        }
    }

    fun getUserId(): Flow<Long?> {
        return context.dataStore.data.map { prefs -> prefs[USER_ID_KEY] }
    }

    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID_KEY)
        }
    }

    // -----------------------------
    // ✅ Ajustes de Hidratación
    // -----------------------------
    suspend fun saveDailyGoal(dailyGoal: Int) {
        context.dataStore.edit { prefs -> prefs[DAILY_GOAL_KEY] = dailyGoal }
    }

    val dailyGoal: Flow<Int?> = context.dataStore.data.map { prefs ->
        prefs[DAILY_GOAL_KEY]
    }

    suspend fun saveStartHour(hour: String) {
        context.dataStore.edit { prefs -> prefs[START_HOUR_KEY] = hour }
    }

    val startHour: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[START_HOUR_KEY]
    }

    suspend fun saveEndHour(hour: String) {
        context.dataStore.edit { prefs -> prefs[END_HOUR_KEY] = hour }
    }

    val endHour: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[END_HOUR_KEY]
    }

    suspend fun saveInterval(minutes: Int) {
        context.dataStore.edit { prefs -> prefs[INTERVAL_KEY] = minutes }
    }

    val interval: Flow<Int?> = context.dataStore.data.map { prefs ->
        prefs[INTERVAL_KEY]
    }
}
