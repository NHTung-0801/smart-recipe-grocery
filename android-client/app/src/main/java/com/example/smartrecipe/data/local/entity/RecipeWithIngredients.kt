package com.example.smartrecipe.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithIngredients(
    @Embedded val recipe: RecipeEntity,

    @Relation(
        parentColumn = "recipeId", // Khóa chính trong RecipeEntity
        entityColumn = "recipeId"  // Khóa ngoại trong IngredientEntity
    )
    val ingredients: List<IngredientEntity>
)