package com.example.smartrecipe.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.smartrecipe.data.local.entity.IngredientEntity
import com.example.smartrecipe.data.local.entity.RecipeEntity
import com.example.smartrecipe.data.local.entity.RecipeWithIngredients
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    // Truy vấn lấy toàn bộ Công thức kèm danh sách Nguyên liệu
    @Transaction
    @Query("SELECT * FROM recipes ORDER BY recipeId DESC")
    fun getAllRecipesWithIngredients(): Flow<List<RecipeWithIngredients>>

    // Truy vấn lấy 1 Công thức cụ thể kèm danh sách Nguyên liệu
    @Transaction
    @Query("SELECT * FROM recipes WHERE recipeId = :id")
    suspend fun getRecipeWithIngredientsById(id: Long): RecipeWithIngredients?

    // Các hàm thêm/sửa/xóa bảng RecipeEntity
    @Query("SELECT * FROM recipes ORDER BY recipeId DESC")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE recipeId = :id")
    suspend fun getRecipeById(id: Long): RecipeEntity?

    // Lệnh lưu món ăn sẽ trả về ID (Long) vừa được tạo ra
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity): Long

    // Lệnh lưu một danh sách các nguyên liệu
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(ingredients: List<IngredientEntity>)

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)
}