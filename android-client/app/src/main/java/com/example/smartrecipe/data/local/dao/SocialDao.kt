package com.example.smartrecipe.data.local.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SocialDao {
    @Query("SELECT 1")
    fun checkSocialExists(): Int
}