package com.example.weatherapphomework.interactor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import com.example.weatherapphomework.db.WeatherDao
import com.example.weatherapphomework.db.entities.CityEntity
import com.example.weatherapphomework.db.entities.Forecast
import com.example.weatherapphomework.model.WeatherInfoResult
import com.example.weatherapphomework.model.info.ForecastInfo
import com.example.weatherapphomework.model.info.TemperatureInfo
import com.example.weatherapphomework.model.info.WeatherInfo
import com.example.weatherapphomework.model.info.WeatherStringInfo
import com.example.weatherapphomework.network.NetworkConfig
import com.example.weatherapphomework.network.WeatherApi
import javax.inject.Inject

class WeatherInteractor @Inject constructor(private var weatherApi: WeatherApi, private var weatherDao: WeatherDao){

    suspend fun getWeatherInfo(context: Context, cityName: String, lat: Double, lon: Double) : WeatherInfoResult {

        val response: WeatherInfoResult
        //Log.d("ASDASD", "coordinates: $lat, $lon")
        val cityResult = weatherDao.getCityByName(cityName)
        //Log.d("ASDASD", "result: " + cityResult.toString())

        if (isOnline(context)) {
            response = weatherApi.getWeatherByCoordinates(lat, lon, NetworkConfig.API_KEY)

            val forecast = response.daily?.map {
                it.temp?.day
            }

            weatherDao.updateCity(CityEntity(cityResult.id, cityResult.name, response.current?.temp, response.current?.weather?.get(0)?.description, lat, lon, forecast = Forecast(forecast)))

        } else {

            val forecastTempList: ArrayList<ForecastInfo> = ArrayList()
            cityResult.forecast?.forecastList?.forEach {
                forecastTempList.add(ForecastInfo(null, TemperatureInfo(it), WeatherStringInfo(cityResult.weatherString)))
            }

            response = WeatherInfoResult(lat, lon, WeatherInfo(temp = cityResult.temperature, weather = listOf(WeatherStringInfo(cityResult.weatherString))), daily = forecastTempList)
        }

        return response
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null
    }
}