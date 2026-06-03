package com.example.smartrecipe.domain.repository

import com.example.smartrecipe.domain.model.GroceryItem
import kotlinx.coroutines.flow.Flow

interface IGroceryRepository {
    fun getAllGroceries(): Flow<List<GroceryItem>>
    suspend fun addGroceries(items: List<GroceryItem>)
    suspend fun toggleCheckStatus(item: GroceryItem)
    suspend fun clearAll()
    suspend fun clearChecked()
}