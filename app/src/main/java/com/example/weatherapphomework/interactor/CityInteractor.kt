package com.example.weatherapphomework.interactor

import android.content.Context
import android.net.ConnectivityManager
import com.example.weatherapphomework.db.WeatherDao
import com.example.weatherapphomework.db.entities.CityEntity
import com.example.weatherapphomework.db.entities.Forecast
import com.example.weatherapphomework.model.CoordinatesResult
import com.example.weatherapphomework.model.info.CoordinateInfo
import com.example.weatherapphomework.model.info.MainWeatherInfo
import com.example.weatherapphomework.model.info.WeatherStringInfo
import com.example.weatherapphomework.network.NetworkConfig
import com.example.weatherapphomework.network.WeatherApi
import javax.inject.Inject

class CityInteractor @Inject constructor(private var weatherApi: WeatherApi, private var weatherDao: WeatherDao) {

    suspend fun getCoordinates(context: Context, cityName: String) : CoordinatesResult {

        val response: CoordinatesResult

        if (isOnline(context)) {
            response = weatherApi.getCoordinatesByCity(cityName, NetworkConfig.API_KEY)

        } else {
            val city = weatherDao.getCityByName(cityName)
            response = CoordinatesResult(cityName, CoordinateInfo(city.lat, city.lon), listOf(WeatherStringInfo(city.weatherString)), MainWeatherInfo(city.temperature))
        }

        return response
    }

    //suspend might be missing
    fun getCityList() : List<CityEntity> {
        return weatherDao.getAllCities().map {
            CityEntity(it.id, it.name, it.temperature)
        }
    }

    suspend fun saveCity(context: Context, cityName: String) {
        try {
            if (isOnline(context = context)) {
                val coordResult = weatherApi.getCoordinatesByCity(cityName, NetworkConfig.API_KEY)
                val weatherResult = weatherApi.getWeatherByCoordinates(coordResult.coord?.lat!!, coordResult.coord?.lon!!, NetworkConfig.API_KEY)

                val forecast = weatherResult.daily?.map {
                    it.temp?.day
                }

                weatherDao.addCity(CityEntity(name = cityName, temperature = weatherResult.current?.temp, weatherString = weatherResult.current?.weather?.get(0)?.description, lat = coordResult.coord?.lat, lon = coordResult.coord?.lon, forecast = Forecast(forecast)))

            } else {
                weatherDao.addCity(CityEntity(name = cityName))
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    //suspend might be missing
    fun updateCity(city: CityEntity) {
        weatherDao.updateCity(city)
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null
    }
}