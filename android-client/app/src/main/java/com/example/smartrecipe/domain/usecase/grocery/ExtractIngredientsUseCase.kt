package com.example.smartrecipe.domain.usecase.grocery

import com.example.smartrecipe.android_client.domain.model.Ingredient
import com.example.smartrecipe.android_client.domain.model.Recipe
import javax.inject.Inject

class ExtractIngredientsUseCase @Inject constructor() {
    // Rút toàn bộ nguyên liệu từ các món ăn được chọn
    operator fun invoke(recipes: List<Recipe>): List<Ingredient> {
        val allIngredients = mutableListOf<Ingredient>()
        recipes.forEach { recipe ->
            allIngredients.addAll(recipe.ingredients)
        }
        return allIngredients
    }
}