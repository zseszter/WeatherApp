package com.example.weatherapphomework.model

data class WeatherInfoResult(
        var currentWeatherInfo: WeatherInfo? = null,
        var forecast: List<Double>? = null
)