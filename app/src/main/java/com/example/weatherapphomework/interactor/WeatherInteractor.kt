package com.example.weatherapphomework.interactor

import android.util.Log
import com.example.weatherapphomework.interactor.event.GetWeatherEvent
import com.example.weatherapphomework.network.NetworkConfig
import com.example.weatherapphomework.network.WeatherApi
import javax.inject.Inject
import org.greenrobot.eventbus.EventBus

class WeatherInteractor @Inject constructor(private var weatherApi: WeatherApi){

    fun getWeatherInfo(lat: Double, lon: Double) {

        val event = GetWeatherEvent()

        try {
            val weatherQueryCall = weatherApi.getWeatherByCoordinates(lat, lon, NetworkConfig.API_KEY)
            val response = weatherQueryCall.execute()
            Log.d("Response", response.body().toString())

            if (response.code() != 200) {
                throw Exception("Result code is not 200")
            }

            event.code = response.code()
            event.weatherInfos = response.body()?.weatherInfoList
            EventBus.getDefault().post(event)
        } catch (e: Exception) {
            event.throwable = e
            EventBus.getDefault().post(event)
        }
    }

}