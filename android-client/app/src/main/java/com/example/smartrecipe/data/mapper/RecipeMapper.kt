package com.example.smartrecipe.data.mapper

import com.example.smartrecipe.android_client.domain.model.Recipe
import com.example.smartrecipe.data.local.entity.RecipeEntity
import javax.inject.Inject

class RecipeMapper @Inject constructor() {

    // Chuyển từ DB Entity sang Domain Model
    fun mapToDomain(entity: RecipeEntity): Recipe {
        return Recipe(
            id = entity.recipeId,
            title = entity.title,
            description = entity.description,
            instructions = entity.instructions,
            prepTime = entity.prepTime,
            defaultServings = entity.defaultServings,
            calories = entity.calories,
            imageUrl = entity.imageUrl,
            isSynced = entity.isSynced
        )
    }

    // Chuyển từ Domain Model xuống DB Entity để lưu
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