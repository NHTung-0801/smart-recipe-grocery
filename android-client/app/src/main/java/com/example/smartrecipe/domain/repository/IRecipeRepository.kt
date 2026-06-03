package com.example.smartrecipe.android_client.domain.repository

import com.example.smartrecipe.android_client.domain.model.Ingredient
import com.example.smartrecipe.android_client.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface IRecipeRepository {
    fun getAllRecipes(): Flow<List<Recipe>>
    suspend fun getRecipeById(id: Long): Recipe?
    suspend fun saveRecipe(recipe: Recipe): Long
    suspend fun deleteRecipe(recipe: Recipe)

    // Thêm dòng này vào trong interface
    suspend fun addRecipe(title: String, time: Int, calories: Int, ingredients: List<Ingredient>)
}