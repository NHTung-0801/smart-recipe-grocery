package com.example.smartrecipe.domain.usecase.journal

import com.example.smartrecipe.domain.repository.IJournalRepository
import javax.inject.Inject

class AddJournalEntryUseCase @Inject constructor(
    private val journalRepository: IJournalRepository
) {
    suspend operator fun invoke(recipeName: String, calories: Int) {
        val protein = (calories * 0.3 / 4).toInt()
        val carbs = (calories * 0.4 / 4).toInt()
        val fat = (calories * 0.3 / 9).toInt()

        journalRepository.addEntry(recipeName, calories, protein, carbs, fat)
    }
}