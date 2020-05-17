package com.example.weatherapphomework.ui.city

import com.example.weatherapphomework.db.entities.CityEntity
import com.example.weatherapphomework.model.City
import com.example.weatherapphomework.model.DummyContent

interface CityScreen {

    fun updateCityItem(cityName: String?, lat: Double?, lon: Double?)

    fun getCities(cityList: ArrayList<City>);

    fun showNetworkError(msg: String)
}