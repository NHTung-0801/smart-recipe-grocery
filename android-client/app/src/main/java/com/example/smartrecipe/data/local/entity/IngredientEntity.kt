package com.example.smartrecipe.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ingredients",
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = ["recipeId"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE // Xóa công thức thì xóa luôn nguyên liệu
        )
    ],
    indices = [Index("recipeId")] // Đánh index để truy vấn nhanh hơn
)
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true)
    val ingredientId: Long = 0,
    val recipeId: Long,
    val name: String,
    val amount: Double,
    val unit: String // Ví dụ: gram, ml, muỗng...
)