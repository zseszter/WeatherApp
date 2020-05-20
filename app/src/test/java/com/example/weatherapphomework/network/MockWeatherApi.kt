package com.example.weatherapphomework.network

import com.example.weatherapphomework.model.CoordinatesResult
import com.example.weatherapphomework.model.WeatherInfoResult
import retrofit2.Call

class MockWeatherApi: WeatherApi {
    override suspend fun getCoordinatesByCity(cityName: String, appid: String): CoordinatesResult {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getWeatherByCoordinates(latitudes: Double, longitudes: Double, appid: String): WeatherInfoResult {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}