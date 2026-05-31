package com.example.smartrecipe.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val recipeId: Long = 0,
    val title: String,
    val description: String,
    val instructions: String, // Có thể lưu dưới dạng JSON String cho các bước
    val prepTime: Int, // Tính bằng phút
    val defaultServings: Int, // Khẩu phần ăn mặc định
    val calories: Int,
    val imageUrl: String?, // Đường dẫn ảnh local hoặc URL mạng
    val isSynced: Boolean = false // Cờ đánh dấu để Worker đồng bộ lên Backend sau
)