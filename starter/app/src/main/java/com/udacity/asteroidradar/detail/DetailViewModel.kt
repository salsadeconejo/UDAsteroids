package com.udacity.asteroidradar.detail

import android.content.Context
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidUpdate
import com.udacity.asteroidradar.NasaRepository
import com.udacity.asteroidradar.database.NasaDatabase
import com.udacity.asteroidradar.database.getInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class DetailViewModel(applicationContext: Context) : ViewModel() {

    val database = getInstance(context = applicationContext)

    lateinit var asteroid: LiveData<Asteroid>

    private val _favoriteStatus = MutableLiveData<Boolean?>()
    val favoriteStatus: LiveData<Boolean?>
        get() = _favoriteStatus

    //Since this is a quick access to DB I don't think a full repository is needed.
    fun updateFavoriteAsteroid(asteroid: Asteroid) {
        asteroid.isFavorite = !asteroid.isFavorite
        viewModelScope.launch(Dispatchers.IO) {
            val tempUpdate = AsteroidUpdate(asteroid.isFavorite, asteroid.id)
            database.nasaDatabaseDao.updateFavorite(tempUpdate)
            _favoriteStatus.postValue(asteroid.isFavorite)
        }
    }

    fun refreshAsteroid(id: Long) {
        asteroid = database.nasaDatabaseDao.getAsteroid(id)
    }
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val appContext: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(appContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}