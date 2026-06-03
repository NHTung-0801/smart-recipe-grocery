package com.example.smartrecipe.android_client.data.repository

import com.example.smartrecipe.android_client.domain.model.Ingredient
import com.example.smartrecipe.android_client.domain.model.Recipe
import com.example.smartrecipe.android_client.domain.repository.IRecipeRepository
import com.example.smartrecipe.data.local.dao.RecipeDao
import com.example.smartrecipe.data.local.entity.IngredientEntity
import com.example.smartrecipe.data.local.entity.RecipeEntity
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

    // Bạn cần đảm bảo Interface IRecipeRepository cũng đã được cập nhật tham số ingredients
    override suspend fun addRecipe(title: String, time: Int, calories: Int, ingredients: List<Ingredient>) {
        // 1. Lưu Recipe và lấy ID
        // Chú ý: Map đúng biến 'prepTime' và truyền giá trị rỗng cho các cột chưa dùng tới
        val recipeEntity = RecipeEntity(
            title = title,
            prepTime = time,
            calories = calories,
            defaultServings = 1,
            description = "",
            imageUrl = "",
            instructions = ""
        )
        val generatedRecipeId = recipeDao.insertRecipe(recipeEntity)

        // 2. Gắn ID vừa tạo vào từng nguyên liệu và lưu xuống bảng Ingredients
        if (ingredients.isNotEmpty()) {
            val ingredientEntities = ingredients.map {
                IngredientEntity(
                    recipeId = generatedRecipeId,
                    name = it.name,
                    amount = it.amount,
                    unit = it.unit
                )
            }
            recipeDao.insertIngredients(ingredientEntities)
        }
    }
}