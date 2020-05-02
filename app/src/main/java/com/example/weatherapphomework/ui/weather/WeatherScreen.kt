package com.example.weatherapphomework.ui.weather

import com.example.weatherapphomework.model.WeatherInfo

interface WeatherScreen {

    fun showCityName(name: String)

    fun showTemperature(temp: Double)

    fun showWeatherImage(image: Int)

    fun showForecast(weatherInfos: List<WeatherInfo>?)

    fun refreshWeatherInfo()

    fun showNetworkError(msg: String?)

}