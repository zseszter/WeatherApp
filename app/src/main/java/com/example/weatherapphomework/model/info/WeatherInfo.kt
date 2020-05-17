package com.example.weatherapphomework.model.info

data class WeatherInfo(
        var sunrise: Integer? = null,
        var sunset: Integer? = null,
        var temp: Double? = null,
        var humidity: Integer? = null,
        var wind_deg: Integer? = null,
        var weather: WeatherStringInfo? = null
)