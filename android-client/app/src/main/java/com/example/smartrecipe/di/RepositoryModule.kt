package com.example.smartrecipe.di

import com.example.smartrecipe.android_client.data.repository.RecipeRepositoryImpl
import com.example.smartrecipe.android_client.domain.repository.IRecipeRepository
import com.example.smartrecipe.data.repository.GroceryRepositoryImpl
import com.example.smartrecipe.data.repository.JournalRepositoryImpl
import com.example.smartrecipe.domain.repository.IGroceryRepository
import com.example.smartrecipe.domain.repository.IJournalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // bind cho Recipe Repository
    @Binds
    @Singleton
    abstract fun bindRecipeRepository(
         recipeRepositoryImpl: RecipeRepositoryImpl
    ): IRecipeRepository


    @Binds
    @Singleton
    abstract fun bindJournalRepository(
        journalRepositoryImpl: JournalRepositoryImpl
    ): IJournalRepository


    @Binds
    @Singleton
    abstract fun bindGroceryRepository(
        groceryRepositoryImpl: GroceryRepositoryImpl
    ): IGroceryRepository
}