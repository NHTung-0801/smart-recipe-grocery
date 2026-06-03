package com.example.smartrecipe.data.repository

import com.example.smartrecipe.data.local.dao.JournalDao
import com.example.smartrecipe.data.local.entity.JournalEntity
import com.example.smartrecipe.domain.model.ConsumedMacros
import com.example.smartrecipe.domain.repository.IJournalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class JournalRepositoryImpl @Inject constructor(
    private val journalDao: JournalDao
) : IJournalRepository {

    override suspend fun addEntry(recipeName: String, calories: Int, protein: Int, carbs: Int, fat: Int) {
        val entry = JournalEntity(
            recipeName = recipeName, calories = calories,
            protein = protein, carbs = carbs, fat = fat,
            timestamp = System.currentTimeMillis() // Lấy giờ hiện tại
        )
        journalDao.insertJournal(entry)
    }

    override fun getDailyMacros(startOfDay: Long, endOfDay: Long): Flow<ConsumedMacros> {
        return journalDao.getDailyMacros(startOfDay, endOfDay).map { sum ->
            ConsumedMacros(
                calories = sum?.totalCalories ?: 0,
                protein = sum?.totalProtein ?: 0,
                carbs = sum?.totalCarbs ?: 0,
                fat = sum?.totalFat ?: 0
            )
        }
    }
}