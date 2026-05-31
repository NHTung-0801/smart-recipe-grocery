package com.example.smartrecipe.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val JWT_TOKEN = stringPreferencesKey("jwt_token")
        val DAILY_CALORIE_GOAL = intPreferencesKey("daily_calorie_goal")
    }

    val jwtToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[JWT_TOKEN]
    }

    suspend fun saveJwtToken(token: String) {
        dataStore.edit { preferences ->
            preferences[JWT_TOKEN] = token
        }
    }

    val dailyCalorieGoal: Flow<Int> = dataStore.data.map { preferences ->
        preferences[DAILY_CALORIE_GOAL] ?: 2000 // Mặc định 2000 Calo/ngày
    }

    suspend fun saveDailyCalorieGoal(calories: Int) {
        dataStore.edit { preferences ->
            preferences[DAILY_CALORIE_GOAL] = calories
        }
    }
}