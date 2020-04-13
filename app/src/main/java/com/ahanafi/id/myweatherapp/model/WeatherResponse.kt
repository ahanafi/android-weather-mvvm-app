package com.ahanafi.id.myweatherapp.model


import com.google.gson.annotations.SerializedName

class WeatherResponse(
    @SerializedName("consolidated_weather")
    val consolidatedWeather: List<ConsolidatedWeather>,
    val time: String,
    val title: String,
    val woeid: Int
)