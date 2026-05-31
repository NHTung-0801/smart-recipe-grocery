package com.example.smartrecipe.android_client.domain.model

data class Recipe(
    val id: Long = 0,
    val title: String,
    val description: String,
    val instructions: String,
    val prepTime: Int,
    val defaultServings: Int,
    val calories: Int,
    val imageUrl: String?,
    val isSynced: Boolean = false,
    val ingredients: List<Ingredient> = emptyList() // Chứa danh sách nguyên liệu đi kèm
)

data class Ingredient(
    val id: Long = 0,
    val name: String,
    val amount: Double,
    val unit: String
)