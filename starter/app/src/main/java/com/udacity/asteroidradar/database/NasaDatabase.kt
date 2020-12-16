package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid

@Database(entities = [NasaDailyImage::class, Asteroid::class], version = 5, exportSchema = false)
abstract class NasaDatabase : RoomDatabase() {
    abstract val nasaDatabaseDao: NasaDatabaseDao
}

@Volatile
private var INSTANCE: NasaDatabase? = null

fun getInstance(context: Context): NasaDatabase {
    synchronized(NasaDatabase::class.java) {
        var instance =
            INSTANCE
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                NasaDatabase::class.java,
                "nasa_database"
            )
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
        }
        return instance
    }
}

