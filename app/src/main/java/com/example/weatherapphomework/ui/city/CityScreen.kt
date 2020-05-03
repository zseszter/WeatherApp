package com.example.weatherapphomework.ui.city

import com.example.weatherapphomework.model.DummyContent

interface CityScreen {

    //Dummy
    fun showDetails(item: DummyContent)

    fun updateCityList(cityName: String?, lat: Double?, lon: Double?)

    fun showNetworkError(msg: String)
}