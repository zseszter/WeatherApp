package com.example.weatherapphomework.interactor.event

import com.example.weatherapphomework.model.WeatherInfo

data class GetWeatherEvent(
        var code: Int = 0,
        var weatherInfos: List<WeatherInfo>? = null,
        var throwable: Throwable? = null
)