package com.example.weatherapphomework.interactor

import android.util.Log
import com.example.weatherapphomework.db.WeatherDao
import com.example.weatherapphomework.db.entities.CityEntity
import com.example.weatherapphomework.interactor.event.GetCities
import com.example.weatherapphomework.interactor.event.GetCoordinatesByCityEvent
import com.example.weatherapphomework.model.City
import com.example.weatherapphomework.model.DummyContent
import com.example.weatherapphomework.network.NetworkConfig
import com.example.weatherapphomework.network.WeatherApi
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class CityInteractor @Inject constructor(private var weatherApi: WeatherApi, private var weatherDao: WeatherDao) {

    fun getCoordinates(cityName: String) {

        val event = GetCoordinatesByCityEvent()

        try {
            val coordinatesQueryCall = weatherApi.getCoordinatesByCity(cityName, NetworkConfig.API_KEY)
            val response = coordinatesQueryCall.execute()
            Log.d("Response", response.body().toString())

            if (response.code() != 200) {
                throw Exception("Result code is not 200")
            }

            event.code = response.code()
            event.cityName = response.body()?.cityName
            event.lat = response.body()?.lat
            event.lon = response.body()?.lon
            event.temperature = response.body()?.temperature

            event.cityId = weatherDao.getCityIdByName(cityName)

            EventBus.getDefault().post(event)
        } catch (e: Exception) {
            event.throwable = e
            EventBus.getDefault().post(event)
        }
    }

    fun getCityList() {

        val event = GetCities()

        try {
            var cityList: ArrayList<City>? = null
            var cityEntityList = weatherDao.getAllCities()

            cityEntityList.forEach {
                cityList?.add(City(it.cityName, it.temperature))
            }

            event.cityList = cityList

            EventBus.getDefault().post(event)
        } catch (e: Exception) {
            event.throwable = e
            EventBus.getDefault().post((event))
        }
    }
}