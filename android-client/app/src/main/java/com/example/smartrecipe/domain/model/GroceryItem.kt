package com.example.smartrecipe.domain.model

data class GroceryItem(
    val id: Long = 0,
    val name: String,
    val amount: Double,
    val unit: String,
    val isChecked: Boolean = false
)