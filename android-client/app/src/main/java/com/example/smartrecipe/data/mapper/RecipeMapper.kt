package com.example.smartrecipe.data.mapper

import com.example.smartrecipe.android_client.domain.model.Ingredient
import com.example.smartrecipe.android_client.domain.model.Recipe
import com.example.smartrecipe.data.local.entity.RecipeEntity
import com.example.smartrecipe.data.local.entity.RecipeWithIngredients
import javax.inject.Inject

class RecipeMapper @Inject constructor() {

    // Nâng cấp: Nhận vào RecipeWithIngredients thay vì RecipeEntity đơn thuần
    fun mapToDomain(relation: RecipeWithIngredients): Recipe {
        return Recipe(
            id = relation.recipe.recipeId,
            title = relation.recipe.title,
            description = relation.recipe.description,
            instructions = relation.recipe.instructions,
            prepTime = relation.recipe.prepTime,
            defaultServings = relation.recipe.defaultServings,
            calories = relation.recipe.calories,
            imageUrl = relation.recipe.imageUrl,
            isSynced = relation.recipe.isSynced,
            // Ánh xạ danh sách nguyên liệu
            ingredients = relation.ingredients.map { entity ->
                Ingredient(
                    id = entity.ingredientId,
                    name = entity.name,
                    amount = entity.amount,
                    unit = entity.unit
                )
            }
        )
    }

    // Hàm map ngược để lưu (Giữ nguyên như cũ)
    fun mapToEntity(domain: Recipe): RecipeEntity {
        return RecipeEntity(
            recipeId = domain.id,
            title = domain.title,
            description = domain.description,
            instructions = domain.instructions,
            prepTime = domain.prepTime,
            defaultServings = domain.defaultServings,
            calories = domain.calories,
            imageUrl = domain.imageUrl,
            isSynced = domain.isSynced
        )
    }
}