package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidUpdate

@Dao
interface NasaDatabaseDao {
    @Insert
    suspend fun insertImage(dailyImage: NasaDailyImage)

    @Query("SELECT * FROM daily_image_table ORDER BY dayId DESC LIMIT 1;")
    fun getDailyImage(): LiveData<NasaDailyImage>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllAsteroids(asteroids: ArrayList<Asteroid>)

    @Update(entity = Asteroid::class)
    fun updateFavorite(obj: AsteroidUpdate)

    @Query("SELECT * FROM asteroid_table WHERE id = :asteroidId")
    fun getAsteroid(asteroidId: Long): LiveData<Asteroid>

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate IN (:list) ORDER BY closeApproachDate")
    suspend fun getWeekAsteroids(list: List<String>): List<Asteroid>

    @Query("SELECT * FROM asteroid_table WHERE isFavorite = 1 ORDER BY closeApproachDate")
    suspend fun getFavoriteAsteroids(): List<Asteroid>

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate = :today")
    suspend fun getTodayAsteroids(today: String): List<Asteroid>

    @Query("DELETE FROM asteroid_table WHERE closeApproachDate = :yesterday")
    fun clearYesterdayAsteroids(yesterday: String)
}
