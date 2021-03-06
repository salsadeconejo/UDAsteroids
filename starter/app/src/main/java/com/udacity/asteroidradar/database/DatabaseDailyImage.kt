package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

//@Entity(tableName = "daily_image_table")
data class DatabaseDailyImage(
//    @PrimaryKey(autoGenerate = true)
    val dayId: Long = 0L,
    @Json(name = "date")
    val date: String,
    @Json(name = "hdurl")
    val hdUrl: String
)
