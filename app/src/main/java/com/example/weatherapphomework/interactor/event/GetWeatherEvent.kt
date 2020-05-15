package com.example.weatherapphomework.interactor.event

import com.example.weatherapphomework.model.info.WeatherInfo

data class GetWeatherEvent(
        var code: Int = 0,
        var currentWeatherInfo: WeatherInfo? = null,
        var forecast: List<Double>? = null,
        var throwable: Throwable? = null
)