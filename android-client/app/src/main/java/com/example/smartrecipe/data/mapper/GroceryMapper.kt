package com.example.smartrecipe.data.mapper

import com.example.smartrecipe.data.local.entity.GroceryEntity
import com.example.smartrecipe.domain.model.GroceryItem
import javax.inject.Inject

class GroceryMapper @Inject constructor() {
    fun mapToDomain(entity: GroceryEntity): GroceryItem {
        return GroceryItem(
            id = entity.groceryId,
            name = entity.name,
            amount = entity.amount,
            unit = entity.unit,
            isChecked = entity.isChecked
        )
    }

    fun mapToEntity(domain: GroceryItem): GroceryEntity {
        return GroceryEntity(
            groceryId = domain.id,
            name = domain.name,
            amount = domain.amount,
            unit = domain.unit,
            isChecked = domain.isChecked
        )
    }
}