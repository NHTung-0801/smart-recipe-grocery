package com.example.smartrecipe.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // Ví dụ bind cho Recipe Repository
    // @Binds
    // @Singleton
    // abstract fun bindRecipeRepository(
    //     recipeRepositoryImpl: RecipeRepositoryImpl
    // ): IRecipeRepository

    // @Binds
    // @Singleton
    // abstract fun bindGroceryRepository(
    //     groceryRepositoryImpl: GroceryRepositoryImpl
    // ): IGroceryRepository

    // Tương tự cho các repository khác...
}