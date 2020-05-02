package com.example.weatherapphomework.model

data class CoordinatesResult(
        var cityName: String? = null,
        var lat: Double? = null,
        var lon: Double? = null,
        var temperature: Double? = null
)