package com.example.weatherapphomework.interactor

import android.util.Log
import com.example.weatherapphomework.db.WeatherDao
import com.example.weatherapphomework.db.entities.Forecast
import com.example.weatherapphomework.db.entities.ForecastEntity
import com.example.weatherapphomework.db.entities.WeatherInfoEntity
import com.example.weatherapphomework.interactor.event.GetWeatherEvent
import com.example.weatherapphomework.network.NetworkConfig
import com.example.weatherapphomework.network.WeatherApi
import javax.inject.Inject
import org.greenrobot.eventbus.EventBus

class WeatherInteractor @Inject constructor(private var weatherApi: WeatherApi, private var weatherDao: WeatherDao){

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
            event.currentWeatherInfo = response.body().current

            var forecastList: ArrayList<Double?>? = null

            response.body()?.daily?.forEach {
                forecastList?.add(it.temp?.day)
            }

            event.forecast = forecastList

            var cityId = weatherDao.getCityIdByCoordinates(lat, lon)
            weatherDao.addWeatherInfo(WeatherInfoEntity(cityId = cityId, temperature = response.body().current?.temp, weatherString = response.body().current?.weather?.description))
            weatherDao.addForecast(ForecastEntity(cityId = cityId, forecast = Forecast(forecastList)))

            EventBus.getDefault().post(event)

        } catch (e: Exception) {
            event.throwable = e
            EventBus.getDefault().post(event)
        }
    }
}