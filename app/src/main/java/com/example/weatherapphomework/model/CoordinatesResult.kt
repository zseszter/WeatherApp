package com.example.weatherapphomework.model

import com.example.weatherapphomework.model.info.CoordinateInfo
import com.example.weatherapphomework.model.info.MainWeatherInfo
import com.example.weatherapphomework.model.info.WeatherStringInfo

data class CoordinatesResult(
        var name: String? = null,
        var coord: CoordinateInfo? = null,
        var weather: List<WeatherStringInfo>? = null,
        var main: MainWeatherInfo? = null,
        var dt: Integer? = null
)