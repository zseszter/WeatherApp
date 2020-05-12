package com.example.weatherapphomework.interactor.event

import com.example.weatherapphomework.model.City

data class GetCities (
        var cityList: ArrayList<City>? = null,
        var throwable: Throwable? = null
)