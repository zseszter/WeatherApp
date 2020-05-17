package com.example.weatherapphomework.ui.city

import com.example.weatherapphomework.db.entities.CityEntity
import com.example.weatherapphomework.model.City
import com.example.weatherapphomework.model.DummyContent
import com.example.weatherapphomework.model.WeatherInfoResult
import com.example.weatherapphomework.model.info.CoordinateInfo

interface CityScreen {

    fun updateCityItem(cityName: String?, lat: Double?, lon: Double?)

    fun loadCities(cityList: List<City>)

    fun showNetworkError(msg: String)

    fun showDetails(coords: CoordinateInfo)
}