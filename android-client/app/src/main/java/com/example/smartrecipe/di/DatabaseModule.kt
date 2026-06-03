package com.example.smartrecipe.di

import android.content.Context
import androidx.room.Room
import com.example.smartrecipe.data.local.AppDatabase
import com.example.smartrecipe.data.local.dao.GroceryDao
import com.example.smartrecipe.data.local.dao.JournalDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "smart_recipe_db"

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration() // Dùng cho giai đoạn dev, xoá data cũ khi đổi version DB
            .build()
    }

    // --- Cung cấp các DAO đồng bộ Singleton ---

    @Provides
    @Singleton
    fun provideRecipeDao(database: AppDatabase) = database.recipeDao()

    @Provides
    @Singleton
    fun provideGroceryDao(database: AppDatabase): GroceryDao {
        return database.groceryDao()
    }

    @Provides
    @Singleton
    fun provideJournalDao(database: AppDatabase): JournalDao {
        return database.journalDao()
    }

    @Provides
    @Singleton
    fun provideSocialDao(database: AppDatabase) = database.socialDao()
}