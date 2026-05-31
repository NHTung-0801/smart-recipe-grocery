package com.example.smartrecipe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.smartrecipe.data.local.dao.GroceryDao
import com.example.smartrecipe.data.local.dao.JournalDao
import com.example.smartrecipe.data.local.dao.RecipeDao
import com.example.smartrecipe.data.local.dao.SocialDao
import com.example.smartrecipe.data.local.entity.IngredientEntity
import com.example.smartrecipe.data.local.entity.RecipeEntity
import com.example.smartrecipe.data.local.entity.TagEntity

@Database(
    entities = [
        RecipeEntity::class,
        IngredientEntity::class,
        TagEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun groceryDao(): GroceryDao
    abstract fun journalDao(): JournalDao
    abstract fun socialDao(): SocialDao
}