package com.example.weatherapphomework.ui.city

import com.example.weatherapphomework.model.DummyContent

interface CityScreen {

    fun updateCityList(cityName: String?, lat: Double?, lon: Double?)

    fun showNetworkError(msg: String)
}