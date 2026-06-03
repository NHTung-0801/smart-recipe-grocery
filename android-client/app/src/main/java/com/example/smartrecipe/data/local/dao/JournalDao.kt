package com.example.smartrecipe.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.smartrecipe.data.local.entity.JournalEntity
import kotlinx.coroutines.flow.Flow

// Class trung gian để hứng kết quả SUM từ Room
data class DailyMacroSum(
    val totalCalories: Int?,
    val totalProtein: Int?,
    val totalCarbs: Int?,
    val totalFat: Int?
)

@Dao
interface JournalDao {
    @Insert
    suspend fun insertJournal(entry: JournalEntity)

    // Lấy tổng dinh dưỡng của các món được nấu trong khoảng thời gian (1 ngày)
    @Query("""
        SELECT 
            SUM(calories) as totalCalories, 
            SUM(protein) as totalProtein, 
            SUM(carbs) as totalCarbs, 
            SUM(fat) as totalFat 
        FROM journal 
        WHERE timestamp BETWEEN :startOfDay AND :endOfDay
    """)
    fun getDailyMacros(startOfDay: Long, endOfDay: Long): Flow<DailyMacroSum>
}