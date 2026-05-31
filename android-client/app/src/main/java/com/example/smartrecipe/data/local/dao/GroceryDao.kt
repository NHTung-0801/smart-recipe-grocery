package com.example.smartrecipe.data.local.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface GroceryDao {
    // Khai báo tạm để setup Hilt, sau này sẽ bổ sung logic Swipe-to-delete và Gộp nguyên liệu
    @Query("SELECT 1")
    fun checkGroceryExists(): Int
}
