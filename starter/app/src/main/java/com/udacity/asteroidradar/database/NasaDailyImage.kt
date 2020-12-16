package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "daily_image_table")
data class NasaDailyImage(
    @PrimaryKey(autoGenerate = true)
    val dayId: Long = 0L,
    @Json(name = "date")
    val date: String,
    @Json(name = "url")
    val hdurl: String,
    @Json(name = "media_type")
    val type: String
)
