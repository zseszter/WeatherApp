package com.example.weatherapphomework.interactor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapphomework.db.WeatherDao
import com.example.weatherapphomework.db.entities.Forecast
import com.example.weatherapphomework.db.entities.ForecastEntity
import com.example.weatherapphomework.db.entities.WeatherInfoEntity
import com.example.weatherapphomework.interactor.event.GetWeatherEvent
import com.example.weatherapphomework.model.CoordinatesResult
import com.example.weatherapphomework.model.WeatherInfoResult
import com.example.weatherapphomework.model.info.ForecastInfo
import com.example.weatherapphomework.model.info.TemperatureInfo
import com.example.weatherapphomework.model.info.WeatherInfo
import com.example.weatherapphomework.model.info.WeatherStringInfo
import com.example.weatherapphomework.network.NetworkConfig
import com.example.weatherapphomework.network.WeatherApi
import javax.inject.Inject
import org.greenrobot.eventbus.EventBus

class WeatherInteractor @Inject constructor(private var weatherApi: WeatherApi, private var weatherDao: WeatherDao){

    suspend fun getWeatherInfo(context: Context, lat: Double, lon: Double) : WeatherInfoResult {

        val response: WeatherInfoResult

        if (isOnline(context)) {
            response = weatherApi.getWeatherByCoordinates(lat, lon, NetworkConfig.API_KEY)
        } else {
            Toast.makeText(context,"No internet", Toast.LENGTH_SHORT).show()

            val cityId = weatherDao.getCityIdByCoordinates(lat, lon)
            val cityResult = weatherDao.getCity(cityId)

            val forecastList = cityResult.forecast?.forecastList
            val forecastTempList: ArrayList<ForecastInfo> = ArrayList()
            forecastList?.forEach {
                forecastTempList.add(ForecastInfo(null, TemperatureInfo(it), WeatherStringInfo(cityResult.weatherString)))
            }

            response = WeatherInfoResult(lat, lon, WeatherInfo(temp = cityResult.temperature, weather = listOf(WeatherStringInfo(cityResult.weatherString))), daily = forecastTempList)
        }

        return response


        /*var forecastList = response.daily?.map {
            it.temp?.day
        }

        var cityId = weatherDao.getCityIdByCoordinates(lat, lon)
        weatherDao.addWeatherInfo(WeatherInfoEntity(cityId = cityId, temperature = response.current?.temp, weatherString = response.current?.weather?.description))
        weatherDao.addForecast(ForecastEntity(cityId = cityId, forecast = Forecast(forecastList)))

        return response*/
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //if (connectivityManager != null) {
            val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        //}
        return false
    }
}