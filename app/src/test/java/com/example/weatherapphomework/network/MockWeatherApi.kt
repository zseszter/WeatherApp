package com.example.weatherapphomework.network

import com.example.weatherapphomework.model.CoordinatesResult
import com.example.weatherapphomework.model.WeatherInfoResult
import retrofit2.Call

class MockWeatherApi: WeatherApi {
    override fun getCoordinatesByCity(cityName: String, appid: String): Call<CoordinatesResult> {
        TODO("not implemented")
    }

    override fun getWeatherByCoordinates(latitudes: Double, longitudes: Double, appid: String): Call<WeatherInfoResult> {
        TODO("not implemented")
    }
}