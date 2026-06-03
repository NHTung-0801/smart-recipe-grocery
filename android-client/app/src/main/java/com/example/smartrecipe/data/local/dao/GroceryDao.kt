package com.example.smartrecipe.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.smartrecipe.data.local.entity.GroceryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GroceryDao {
    // Sắp xếp: Những món chưa mua (isChecked = 0) sẽ nằm ở trên cùng
    @Query("SELECT * FROM groceries ORDER BY isChecked ASC, name ASC")
    fun getAllGroceries(): Flow<List<GroceryEntity>>

    // Thêm một danh sách nguyên liệu đã được thuật toán gom nhóm vào DB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroceries(groceries: List<GroceryEntity>)

    // Cập nhật trạng thái khi người dùng bấm tick (đã bỏ vào giỏ)
    @Update
    suspend fun updateGrocery(grocery: GroceryEntity)

    // Xóa toàn bộ danh sách khi bắt đầu một tuần đi chợ mới
    @Query("DELETE FROM groceries")
    suspend fun clearAllGroceries()

    // Chỉ xóa những món đã mua xong
    @Query("DELETE FROM groceries WHERE isChecked = 1")
    suspend fun clearCheckedGroceries()
}