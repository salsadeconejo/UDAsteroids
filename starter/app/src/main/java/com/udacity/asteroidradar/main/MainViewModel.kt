package com.udacity.asteroidradar.main

import android.content.Context
import androidx.lifecycle.*
import com.udacity.asteroidradar.*
import kotlinx.coroutines.launch

class MainViewModel(applicationContext: Context) : ViewModel() {

    private val nasaRepository = NasaRepository(applicationContext)

    val imageOfTheDay = nasaRepository.nasaImage
    val asteroidListStatus = nasaRepository.listStatus

    val asteroidList : LiveData<List<Asteroid>>
        get() = _asteroidList
    private val _asteroidList = nasaRepository.asteroidList
    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetails: LiveData<Asteroid>
        get() = _navigateToAsteroidDetails

    fun refreshImageOfTheDay() {
        viewModelScope.launch {
            nasaRepository.getImageOfTheDay()
        }
    }

    fun refreshFilter(filter: AsteroidFilter) {
        viewModelScope.launch {
            nasaRepository.updateFilter(filter)
        }
    }

    fun displayDetailScreen(asteroid: Asteroid) {
        _navigateToAsteroidDetails.value = asteroid
    }

    fun doneNavigatingToDetail() {
        _navigateToAsteroidDetails.value = null
    }

    fun refreshList() {
        viewModelScope.launch {
            nasaRepository.refreshList()
        }
    }
}