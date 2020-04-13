package com.ahanafi.id.myweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ahanafi.id.myweatherapp.model.Location
import com.ahanafi.id.myweatherapp.model.WeatherResponse
import com.ahanafi.id.myweatherapp.repository.AppRepository

class DataViewModel : ViewModel() {
    private val repository = AppRepository()
    val locationList : LiveData<List<Location>>
    val detailLocation: LiveData<WeatherResponse>
    val showProgress: LiveData<Boolean>

    init {
        this.showProgress = repository.showProgress
        this.locationList = repository.locationList
        this.detailLocation = repository.detailLocation
    }

    fun changeState() = repository.changeState()

    fun searchLocation(location: String){
        repository.searchLocation(location)
    }

    fun getLocationDetail(locationId: Int?) : LiveData<WeatherResponse> = repository.getWeatherLocation(locationId)
}