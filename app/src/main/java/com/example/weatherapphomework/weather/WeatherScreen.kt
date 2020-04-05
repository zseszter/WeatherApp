package com.example.weatherapphomework.weather

interface WeatherScreen {

    fun showCityName(name: String)

    fun showTemperature(temp: Double)

    fun showWeatherImage(image: Int)

    fun refreshWeatherInfo()

}