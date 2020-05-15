package com.example.weatherapphomework.model

import com.example.weatherapphomework.model.info.ForecastInfo
import com.example.weatherapphomework.model.info.WeatherInfo

data class WeatherInfoResult(
        var lat: Double? = null,
        var lon: Double? = null,
        var current: WeatherInfo? = null,
        var daily: List<ForecastInfo>? = null
)