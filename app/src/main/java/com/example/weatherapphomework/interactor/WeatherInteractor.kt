package com.example.weatherapphomework.interactor

import android.util.Log
import com.example.weatherapphomework.db.WeatherDao
import com.example.weatherapphomework.db.entities.Forecast
import com.example.weatherapphomework.db.entities.ForecastEntity
import com.example.weatherapphomework.db.entities.WeatherInfoEntity
import com.example.weatherapphomework.interactor.event.GetWeatherEvent
import com.example.weatherapphomework.model.WeatherInfoResult
import com.example.weatherapphomework.network.NetworkConfig
import com.example.weatherapphomework.network.WeatherApi
import javax.inject.Inject
import org.greenrobot.eventbus.EventBus

class WeatherInteractor @Inject constructor(private var weatherApi: WeatherApi, private var weatherDao: WeatherDao){

    suspend fun getWeatherInfo(lat: Double, lon: Double) : WeatherInfoResult {

        return weatherApi.getWeatherByCoordinates(lat, lon, NetworkConfig.API_KEY)

        /*var forecastList = response.daily?.map {
            it.temp?.day
        }

        var cityId = weatherDao.getCityIdByCoordinates(lat, lon)
        weatherDao.addWeatherInfo(WeatherInfoEntity(cityId = cityId, temperature = response.current?.temp, weatherString = response.current?.weather?.description))
        weatherDao.addForecast(ForecastEntity(cityId = cityId, forecast = Forecast(forecastList)))

        return response*/
    }
}