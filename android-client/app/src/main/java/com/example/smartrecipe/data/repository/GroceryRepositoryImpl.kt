package com.example.smartrecipe.data.repository

import com.example.smartrecipe.data.local.dao.GroceryDao
import com.example.smartrecipe.data.mapper.GroceryMapper
import com.example.smartrecipe.domain.model.GroceryItem
import com.example.smartrecipe.domain.repository.IGroceryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GroceryRepositoryImpl @Inject constructor(
    private val groceryDao: GroceryDao,
    private val mapper: GroceryMapper
) : IGroceryRepository {

    override fun getAllGroceries(): Flow<List<GroceryItem>> {
        return groceryDao.getAllGroceries().map { entities ->
            entities.map { mapper.mapToDomain(it) }
        }
    }

    override suspend fun addGroceries(items: List<GroceryItem>) {
        val entities = items.map { mapper.mapToEntity(it) }
        groceryDao.insertGroceries(entities)
    }

    override suspend fun toggleCheckStatus(item: GroceryItem) {
        // Đảo ngược trạng thái isChecked (từ false thành true hoặc ngược lại)
        val updatedItem = item.copy(isChecked = !item.isChecked)
        groceryDao.updateGrocery(mapper.mapToEntity(updatedItem))
    }

    override suspend fun clearAll() {
        groceryDao.clearAllGroceries()
    }

    override suspend fun clearChecked() {
        groceryDao.clearCheckedGroceries()
    }
}