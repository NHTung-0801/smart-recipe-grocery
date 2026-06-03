package com.example.smartrecipe.domain.usecase.grocery

import com.example.smartrecipe.android_client.domain.model.Recipe
import com.example.smartrecipe.domain.repository.IGroceryRepository
import javax.inject.Inject

class GenerateGroceryListUseCase @Inject constructor(
    private val extractIngredientsUseCase: ExtractIngredientsUseCase,
    private val aggregateGroceryUseCase: AggregateGroceryUseCase,
    private val repository: IGroceryRepository
) {
    suspend operator fun invoke(recipes: List<Recipe>) {
        // Bước 1: Bóc tách nguyên liệu
        val rawIngredients = extractIngredientsUseCase(recipes)

        // Bước 2: Gom nhóm và cộng dồn
        val aggregatedItems = aggregateGroceryUseCase(rawIngredients)

        // Bước 3: Lưu vào Room Database
        // (Lưu ý: Bạn có thể gọi repository.clearAll() ở đây nếu muốn xóa list cũ trước khi tạo list mới)
        repository.addGroceries(aggregatedItems)
    }
}