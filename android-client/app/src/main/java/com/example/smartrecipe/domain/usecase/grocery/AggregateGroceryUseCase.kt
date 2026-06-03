package com.example.smartrecipe.domain.usecase.grocery

import com.example.smartrecipe.android_client.domain.model.Ingredient
import com.example.smartrecipe.domain.model.GroceryItem
import javax.inject.Inject

class AggregateGroceryUseCase @Inject constructor() {
    operator fun invoke(ingredients: List<Ingredient>): List<GroceryItem> {
        // 1. Gom nhóm theo Tên và Đơn vị tính (không phân biệt hoa thường)
        val groupedMap = ingredients.groupBy { ingredient ->
            Pair(ingredient.name.trim().lowercase(), ingredient.unit.trim().lowercase())
        }

        // 2. Tính tổng số lượng
        return groupedMap.map { (_, list) ->
            val totalAmount = list.sumOf { it.amount }
            val originalName = list.first().name.trim()
            val originalUnit = list.first().unit.trim()

            GroceryItem(
                name = originalName,
                amount = totalAmount,
                unit = originalUnit
            )
        }
    }
}