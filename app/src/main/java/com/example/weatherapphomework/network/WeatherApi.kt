package com.example.weatherapphomework.network

import com.example.weatherapphomework.model.CoordinatesResult
import com.example.weatherapphomework.model.WeatherInfoResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    fun getCoordinatesByCity(@Query("q") cityName: String,
                             @Query("appid") appid: String): Call<CoordinatesResult>

    @GET("onecall")
    fun getWeatherByCoordinates(@Query("lat") latitudes: Double,
                                @Query("lon") longitudes: Double,
                                @Query("appid") appid: String): Call<WeatherInfoResult>
}