package com.ahanafi.id.myweatherapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ahanafi.id.myweatherapp.model.Location
import com.ahanafi.id.myweatherapp.model.WeatherResponse
import com.ahanafi.id.myweatherapp.network.BASE_URL
import com.ahanafi.id.myweatherapp.network.WeatherNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppRepository {

    val showProgress = MutableLiveData<Boolean>()
    val locationList = MutableLiveData<List<Location>>()
    val detailLocation = MutableLiveData<WeatherResponse>()

    private var lastRequestTime : Long = -1


    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherService = retrofitBuilder.create(WeatherNetwork::class.java)

    fun changeState() {
        showProgress.value = showProgress.value == null
    }

    fun searchLocation(location: String) {
        showProgress.value = true

        weatherService.getLocation(location).enqueue(object : Callback<List<Location>> {
            override fun onFailure(call: Call<List<Location>>, t: Throwable) {
                showProgress.value = false
                Log.d("getLocation", t.message.toString())
            }

            override fun onResponse(
                call: Call<List<Location>>,
                response: Response<List<Location>>
            ) {
                showProgress.value = false
                locationList.value = response.body()
            }

        })
    }

    fun getWeatherLocation(woeid: Int?) : LiveData<WeatherResponse> {
        val locationDetail = MutableLiveData<WeatherResponse>()
        if (System.currentTimeMillis() - lastRequestTime < 10000) {
            return detailLocation
        }
        weatherService.getWeather(woeid).enqueue(object : Callback<WeatherResponse> {
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.d("getWeahter", t.message.toString())
            }

            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful)  {
                    lastRequestTime = System.currentTimeMillis()
                    locationDetail.postValue(response.body())
                    detailLocation.postValue(response.body())
                }
            }
        })

        return locationDetail
    }


}