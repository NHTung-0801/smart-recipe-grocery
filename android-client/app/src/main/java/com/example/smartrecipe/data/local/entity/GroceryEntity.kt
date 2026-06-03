package com.example.smartrecipe.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groceries")
data class GroceryEntity(
    @PrimaryKey(autoGenerate = true)
    val groceryId: Long = 0,
    val name: String,     // Tên nguyên liệu (VD: Thịt heo, Cà chua)
    val amount: Double,   // Định lượng (VD: 500, 2)
    val unit: String,     // Đơn vị tính (VD: g, kg, quả, mớ)
    val isChecked: Boolean = false // Trạng thái: false (chưa mua), true (đã bỏ vào giỏ)
)