package com.example.weatherapphomework.interactor

import android.util.Log
import com.example.weatherapphomework.db.WeatherDao
import com.example.weatherapphomework.db.entities.Forecast
import com.example.weatherapphomework.db.entities.ForecastEntity
import com.example.weatherapphomework.db.entities.WeatherInfoEntity
import com.example.weatherapphomework.interactor.event.GetWeatherEvent
import com.example.weatherapphomework.model.DummyContent
import com.example.weatherapphomework.network.NetworkConfig
import com.example.weatherapphomework.network.WeatherApi
import javax.inject.Inject
import org.greenrobot.eventbus.EventBus

class WeatherInteractor @Inject constructor(private var weatherApi: WeatherApi, private var weatherDao: WeatherDao){

    //Dummy
    fun getDummyWeatherInfo(dummyItem: DummyContent): DummyContent {
        return dummyItem
    }

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
            event.currentWeatherInfo = response.body().currentWeatherInfo
            event.forecast = response.body()?.forecast

            val cityId = weatherDao.getCityIdByName(response.body()?.currentWeatherInfo?.cityName)
            val temp = response.body()?.currentWeatherInfo?.temperature
            val weatherString = response.body()?.currentWeatherInfo?.weatherString

            weatherDao.addWeatherInfo(WeatherInfoEntity(cityId = cityId, temperature = temp, weatherString = weatherString))
            weatherDao.addForecast(ForecastEntity(cityId = cityId, forecast = Forecast(response.body()?.forecast)))

            EventBus.getDefault().post(event)
        } catch (e: Exception) {
            event.throwable = e
            EventBus.getDefault().post(event)
        }
    }
}