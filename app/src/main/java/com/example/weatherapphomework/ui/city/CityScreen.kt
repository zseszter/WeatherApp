package com.example.weatherapphomework.ui.city

import com.example.weatherapphomework.model.City
import com.example.weatherapphomework.model.info.CoordinateInfo

interface CityScreen {

    fun loadCities(cityList: List<City>)

    fun showDetails(cityName: String, coords: CoordinateInfo)
}