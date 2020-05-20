package com.example.weatherapphomework.ui.weather

import com.example.weatherapphomework.model.info.ForecastInfo

interface WeatherScreen {

    fun showCityName(name: String)

    fun showTemperature(temp: Double?)

    fun showWeatherImage(desc: String?)

    fun loadForecast(forecast: List<ForecastInfo>?)

    fun refreshWeatherInfo()

    fun showNetworkError(msg: String?)

}