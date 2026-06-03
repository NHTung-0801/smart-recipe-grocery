package com.example.smartrecipe.android_client.data.repository

import com.example.smartrecipe.android_client.domain.model.Recipe
import com.example.smartrecipe.android_client.domain.repository.IRecipeRepository
import com.example.smartrecipe.data.local.dao.RecipeDao
import com.example.smartrecipe.data.mapper.RecipeMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao,
    private val mapper: RecipeMapper
) : IRecipeRepository {

    override fun getAllRecipes(): Flow<List<Recipe>> {
        // Sử dụng hàm truy vấn có Transaction mới
        return recipeDao.getAllRecipesWithIngredients().map { relations ->
            relations.map { mapper.mapToDomain(it) }
        }
    }

    override suspend fun getRecipeById(id: Long): Recipe? {
        // Sử dụng hàm truy vấn có Transaction mới
        val relation = recipeDao.getRecipeWithIngredientsById(id)
        return relation?.let { mapper.mapToDomain(it) }
    }

    override suspend fun saveRecipe(recipe: Recipe): Long {
        val entity = mapper.mapToEntity(recipe)
        return recipeDao.insertRecipe(entity)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        val entity = mapper.mapToEntity(recipe)
        recipeDao.deleteRecipe(entity)
    }
}