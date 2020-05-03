package com.example.weatherapphomework.interactor

import android.util.Log
import com.example.weatherapphomework.db.WeatherDao
import com.example.weatherapphomework.db.entities.CityEntity
import com.example.weatherapphomework.interactor.event.GetCoordinatesByCityEvent
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

            event.cityId = weatherDao.addCity(CityEntity(
                    cityName = response.body()?.cityName,
                    lat = response.body()?.lat,
                    lon = response.body()?.lon))

            EventBus.getDefault().post(event)
        } catch (e: Exception) {
            event.throwable = e
            EventBus.getDefault().post(event)
        }
    }
}