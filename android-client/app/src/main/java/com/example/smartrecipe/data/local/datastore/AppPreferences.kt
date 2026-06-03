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
        // Thêm các Key mới cho Macro
        val DAILY_PROTEIN_GOAL = intPreferencesKey("daily_protein_goal")
        val DAILY_CARBS_GOAL = intPreferencesKey("daily_carbs_goal")
        val DAILY_FAT_GOAL = intPreferencesKey("daily_fat_goal")
    }

    val jwtToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[JWT_TOKEN]
    }

    // Đọc dữ liệu Macro (Thiết lập giá trị mặc định cho một người trưởng thành)
    val dailyCalorieGoal: Flow<Int> = dataStore.data.map { it[DAILY_CALORIE_GOAL] ?: 2000 }
    val dailyProteinGoal: Flow<Int> = dataStore.data.map { it[DAILY_PROTEIN_GOAL] ?: 150 } // 150g Protein
    val dailyCarbsGoal: Flow<Int> = dataStore.data.map { it[DAILY_CARBS_GOAL] ?: 200 }   // 200g Carbs
    val dailyFatGoal: Flow<Int> = dataStore.data.map { it[DAILY_FAT_GOAL] ?: 65 }        // 65g Fat

    // Gộp chung vào 1 hàm lưu để tối ưu hóa hiệu năng ghi file
    suspend fun saveNutritionGoals(calories: Int, protein: Int, carbs: Int, fat: Int) {
        dataStore.edit { preferences ->
            preferences[DAILY_CALORIE_GOAL] = calories
            preferences[DAILY_PROTEIN_GOAL] = protein
            preferences[DAILY_CARBS_GOAL] = carbs
            preferences[DAILY_FAT_GOAL] = fat
        }
    }
}