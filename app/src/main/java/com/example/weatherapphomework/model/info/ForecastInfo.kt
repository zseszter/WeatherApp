package com.example.weatherapphomework.model.info

data class ForecastInfo (
        var dt: Int? = null,
        var temp: TemperatureInfo? = null,
        var weatherInfo: WeatherStringInfo? = null
)