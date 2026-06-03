package com.example.smartrecipe.android_client.domain.usecase.nutrition

import com.example.smartrecipe.data.local.datastore.AppPreferences
import com.example.smartrecipe.domain.model.ConsumedMacros
import com.example.smartrecipe.domain.repository.IJournalRepository
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject

class TrackDailyMacroUseCase @Inject constructor(
    private val appPreferences: AppPreferences,
    private val journalRepository: IJournalRepository
) {
    // Lấy các mục tiêu đã thiết lập
    fun getDailyCalorieGoal(): Flow<Int> = appPreferences.dailyCalorieGoal
    fun getDailyProteinGoal(): Flow<Int> = appPreferences.dailyProteinGoal
    fun getDailyCarbsGoal(): Flow<Int> = appPreferences.dailyCarbsGoal
    fun getDailyFatGoal(): Flow<Int> = appPreferences.dailyFatGoal

    // Lấy dữ liệu lượng đã nạp vào hôm nay từ Repository
    fun getConsumedMacrosToday(): Flow<ConsumedMacros> {
        val calendar = Calendar.getInstance()

        // Thiết lập thời điểm bắt đầu ngày (00:00:00)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis

        // Thiết lập thời điểm kết thúc ngày (23:59:59)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfDay = calendar.timeInMillis

        return journalRepository.getDailyMacros(startOfDay, endOfDay)
    }
}
