package com.udacity.asteroidradar

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.getInstance
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.util.*
import kotlinx.coroutines.*
import org.json.JSONObject


enum class Status {
    LOADING, ERROR, DONE
}

enum class AsteroidFilter {
    ALL, TODAY, FAVORITE, NONE
}

class NasaRepository(context: Context) {

    private var filter: AsteroidFilter = AsteroidFilter.NONE

    private val database = getInstance(context)
    val nasaImage = database.nasaDatabaseDao.getDailyImage()

    val asteroidList = MutableLiveData<List<Asteroid>>()

    val listStatus: LiveData<Status>
        get() = _listStatus
    private val _listStatus = MutableLiveData<Status>()

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    init {

        scope.launch {
            refreshAsteroids()
        }
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                _listStatus.postValue(Status.LOADING)
                val stringResponse = NasaApi.retrofitService.getAsteroids(
                    getCurrentDay(),
                    sumDaysToCurrent(),
                    Constants.API_KEY
                )
                if (stringResponse.isNotEmpty()) {
                    val asteroids = parseAsteroidsJsonResult(JSONObject(stringResponse))
                    database.nasaDatabaseDao.insertAllAsteroids(asteroids)
                }
                _listStatus.postValue(Status.DONE)
            } catch (exception: Exception) {
                _listStatus.postValue(Status.ERROR)
            }
        }
        updateFilter(AsteroidFilter.ALL)
    }

    suspend fun getImageOfTheDay() {
        withContext(Dispatchers.IO) {
            if (nasaImage.value == null && nasaImage.value?.date != getCurrentDay()) {
                try {
                    val newImage = NasaApi.retrofitService.getImageOfTheDay(Constants.API_KEY)
                    if (newImage.type == "image") {
                        database.nasaDatabaseDao.insertImage(newImage)
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
        }
    }

    suspend fun refreshList() {
        updateFilter(filter)
    }

    suspend fun updateFilter(asteroidFilter: AsteroidFilter) {

        if (asteroidFilter != filter || asteroidFilter == AsteroidFilter.FAVORITE) {
            withContext(Dispatchers.IO) {
                when (asteroidFilter) {
                    AsteroidFilter.TODAY ->
                        asteroidList.postValue(
                            database.nasaDatabaseDao.getTodayAsteroids(getCurrentDay())
                        )
                    AsteroidFilter.FAVORITE ->
                        asteroidList.postValue(
                            database.nasaDatabaseDao.getFavoriteAsteroids()
                        )
                    else ->
                        asteroidList.postValue(
                            database.nasaDatabaseDao.getWeekAsteroids(getNextSevenDays())
                        )
                }
            }
        }
        filter = asteroidFilter

    }

    suspend fun clearYesterdayAsteroid() {
        withContext(Dispatchers.IO) {
            database.nasaDatabaseDao.clearYesterdayAsteroids(getYesterday())
        }
    }
}
