package com.example.weatherapphomework.interactor.event

data class GetCoordinatesByCityEvent(
        var code: Int = 0,
        var cityId: Long = 0,
        var cityName: String? = null,
        var lat: Double? = null,
        var lon: Double? = null,
        var temperature: Double? = null,
        var throwable: Throwable? = null
)