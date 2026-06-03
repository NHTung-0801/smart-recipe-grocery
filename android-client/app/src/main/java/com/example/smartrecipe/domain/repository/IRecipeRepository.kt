package com.example.smartrecipe.android_client.domain.repository

import com.example.smartrecipe.android_client.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface IRecipeRepository {
    fun getAllRecipes(): Flow<List<Recipe>>
    suspend fun getRecipeById(id: Long): Recipe?
    suspend fun saveRecipe(recipe: Recipe): Long
    suspend fun deleteRecipe(recipe: Recipe)
}