package com.example.weatherapphomework.ui.weather

interface WeatherScreen {

    fun showCityName(name: String)

    fun showTemperature(temp: Double)

    fun showWeatherImage(image: Int)

    fun showForecast(forecast: List<Double>?)

    fun refreshWeatherInfo()

    fun showNetworkError(msg: String?)

}