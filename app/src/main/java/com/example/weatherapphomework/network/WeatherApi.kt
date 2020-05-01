package com.example.weatherapphomework.network

import com.example.weatherapphomework.model.WeatherInfoResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("")
    fun getWeatherByCity(@Query("q") cityName: String): Call<WeatherInfoResult>

}