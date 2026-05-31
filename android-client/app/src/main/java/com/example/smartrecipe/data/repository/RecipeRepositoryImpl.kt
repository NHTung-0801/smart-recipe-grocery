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
        // Lấy danh sách Entity từ DB và map từng phần tử sang Domain Model
        return recipeDao.getAllRecipes().map { entities ->
            entities.map { mapper.mapToDomain(it) }
        }
    }

    override suspend fun getRecipeById(id: Long): Recipe? {
        val entity = recipeDao.getRecipeById(id)
        return entity?.let { mapper.mapToDomain(it) }
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