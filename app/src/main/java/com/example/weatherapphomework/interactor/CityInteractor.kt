package com.example.weatherapphomework.interactor

import android.util.Log
import com.example.weatherapphomework.db.WeatherDao
import com.example.weatherapphomework.db.entities.CityEntity
import com.example.weatherapphomework.interactor.event.GetCities
import com.example.weatherapphomework.interactor.event.GetCoordinatesByCityEvent
import com.example.weatherapphomework.model.City
import com.example.weatherapphomework.network.NetworkConfig
import com.example.weatherapphomework.network.WeatherApi
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class CityInteractor @Inject constructor(private var weatherApi: WeatherApi, private var weatherDao: WeatherDao) {

    suspend fun getCoordinates(cityName: String) : GetCoordinatesByCityEvent {

        val event = GetCoordinatesByCityEvent()

        val response = weatherApi.getCoordinatesByCity(cityName, NetworkConfig.API_KEY)

        event.cityName = response.name
        event.lat = response.coord?.lat
        event.lon = response.coord?.lon
        event.temperature = response.main?.temp

        event.cityId = weatherDao.getCityIdByName(cityName)

        return event
    }

    suspend fun getCityList() : List<City> {

        return weatherDao.getAllCities().map {
            City(it.name, it.temperature)
        }
    }

    suspend fun saveCity(cityName: String) {
        try {
            weatherDao.addCity(CityEntity(name=cityName, temperature = 10.2, lat=null, lon=null))
            //Log.d("ASDASD", weatherDao.getAllCities().toString())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}