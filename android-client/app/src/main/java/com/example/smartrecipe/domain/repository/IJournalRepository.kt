package com.example.smartrecipe.domain.repository

import com.example.smartrecipe.domain.model.ConsumedMacros
import kotlinx.coroutines.flow.Flow

interface IJournalRepository {
    suspend fun addEntry(recipeName: String, calories: Int, protein: Int, carbs: Int, fat: Int)
    fun getDailyMacros(startOfDay: Long, endOfDay: Long): Flow<ConsumedMacros>
}