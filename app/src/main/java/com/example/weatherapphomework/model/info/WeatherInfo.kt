package com.example.weatherapphomework.model.info

data class WeatherInfo(
        var sunrise: Int? = null,
        var sunset: Int? = null,
        var temp: Double? = null,
        var humidity: Int? = null,
        var wind_deg: Int? = null,
        var weather: List<WeatherStringInfo>? = null
)