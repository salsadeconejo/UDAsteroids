package com.udacity.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import java.lang.Exception

class RefreshAsteroidsWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshAsteroids"
    }

    override suspend fun doWork(): Result {
        val repository = NasaRepository(context = applicationContext)

        return try {
            repository.refreshAsteroids()
            repository.clearYesterdayAsteroid()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

}