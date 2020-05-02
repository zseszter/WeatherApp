package com.example.weatherapphomework.ui.city

interface CityScreen {

    fun updateCityList(cityName: String?, lat: Double?, lon: Double?)

    fun showNetworkError(msg: String)
}