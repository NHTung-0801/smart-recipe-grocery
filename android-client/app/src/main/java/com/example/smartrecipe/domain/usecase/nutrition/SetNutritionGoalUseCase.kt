package com.example.smartrecipe.android_client.domain.usecase.nutrition

import com.example.smartrecipe.data.local.datastore.AppPreferences
import javax.inject.Inject

class SetNutritionGoalUseCase @Inject constructor(
    private val appPreferences: AppPreferences
) {
    suspend operator fun invoke(calories: Int, protein: Int, carbs: Int, fat: Int) {
        appPreferences.saveNutritionGoals(calories, protein, carbs, fat)
    }
}