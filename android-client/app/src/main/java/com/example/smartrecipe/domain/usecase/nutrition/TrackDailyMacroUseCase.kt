package com.example.smartrecipe.android_client.domain.usecase.nutrition

import com.example.smartrecipe.data.local.datastore.AppPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrackDailyMacroUseCase @Inject constructor(
    private val appPreferences: AppPreferences
) {
    // Lấy các mục tiêu đã thiết lập
    fun getDailyCalorieGoal(): Flow<Int> = appPreferences.dailyCalorieGoal
    fun getDailyProteinGoal(): Flow<Int> = appPreferences.dailyProteinGoal
    fun getDailyCarbsGoal(): Flow<Int> = appPreferences.dailyCarbsGoal
    fun getDailyFatGoal(): Flow<Int> = appPreferences.dailyFatGoal

    // Dữ liệu mô phỏng lượng đã nạp vào hôm nay (Sẽ nối với Room DB ở Phase sau)
    fun getConsumedMacrosToday(): ConsumedMacros {
        return ConsumedMacros(
            calories = 1450,
            protein = 110,
            carbs = 160,
            fat = 45
        )
    }
}

// Data class nhỏ để gói gọn dữ liệu trả về cho ViewModel
data class ConsumedMacros(
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int
)