package com.example.smartrecipe.ui.recipe.nutrition

import androidx.lifecycle.viewModelScope
import com.example.smartrecipe.android_client.core.utils.NutritionCalculator
import com.example.smartrecipe.android_client.domain.usecase.nutrition.SetNutritionGoalUseCase
import com.example.smartrecipe.android_client.domain.usecase.nutrition.TrackDailyMacroUseCase
import com.example.smartrecipe.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutritionViewModel @Inject constructor(
    private val trackDailyMacroUseCase: TrackDailyMacroUseCase,
    private val setNutritionGoalUseCase: SetNutritionGoalUseCase
) : BaseViewModel() {

    // Dữ liệu tiêu thụ hôm nay
    private val _consumedCalories = MutableStateFlow(0)
    val consumedCalories = _consumedCalories.asStateFlow()

    private val _consumedProtein = MutableStateFlow(0)
    val consumedProtein = _consumedProtein.asStateFlow()

    private val _consumedCarbs = MutableStateFlow(0)
    val consumedCarbs = _consumedCarbs.asStateFlow()

    private val _consumedFat = MutableStateFlow(0)
    val consumedFat = _consumedFat.asStateFlow()

    // Các luồng dữ liệu mục tiêu trực tiếp từ DataStore
    val dailyCalorieGoal = trackDailyMacroUseCase.getDailyCalorieGoal()
    val dailyProteinGoal = trackDailyMacroUseCase.getDailyProteinGoal()
    val dailyCarbsGoal = trackDailyMacroUseCase.getDailyCarbsGoal()
    val dailyFatGoal = trackDailyMacroUseCase.getDailyFatGoal()

    init {
        loadNutritionData()
    }

    private fun loadNutritionData() {
        // Lấy số liệu Macro mô phỏng của ngày hôm nay
        val macros = trackDailyMacroUseCase.getConsumedMacrosToday()
        _consumedCalories.value = macros.calories
        _consumedProtein.value = macros.protein
        _consumedCarbs.value = macros.carbs
        _consumedFat.value = macros.fat
    }

    // Hàm cập nhật mục tiêu thông minh: Tính TDEE và tự động chia Macro chuẩn (30-40-30)
    fun updateGoalsFromTDEE(weightKg: Double, heightCm: Double, age: Int, isMale: Boolean, activityMultiplier: Double) {
        viewModelScope.launch(exceptionHandler) {
            val tdee = NutritionCalculator.calculateTDEE(weightKg, heightCm, age, isMale, activityMultiplier).toInt()
            val (protein, carbs, fat) = NutritionCalculator.calculateStandardMacros(tdee)

            setNutritionGoalUseCase(tdee, protein, carbs, fat)
        }
    }
}