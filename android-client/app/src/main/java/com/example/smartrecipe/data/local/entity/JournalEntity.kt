package com.example.smartrecipe.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal")
data class JournalEntity(
    @PrimaryKey(autoGenerate = true)
    val journalId: Long = 0,
    val recipeName: String,
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int,
    val timestamp: Long // Lưu thời gian nấu xong
)