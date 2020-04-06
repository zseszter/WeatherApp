package com.example.weatherapphomework.ui.weather

interface WeatherScreen {

    fun showCityName(name: String)

    fun showTemperature(temp: Double)

    fun showWeatherImage(image: Int)

    fun refreshWeatherInfo()

}