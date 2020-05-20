package com.example.weatherapphomework.ui.city

import android.content.Context
import android.net.ConnectivityManager
import com.example.weatherapphomework.db.entities.CityEntity
import com.example.weatherapphomework.db.entities.Forecast
import com.example.weatherapphomework.interactor.CityInteractor
import com.example.weatherapphomework.interactor.WeatherInteractor
import com.example.weatherapphomework.interactor.event.GetCities
import com.example.weatherapphomework.interactor.event.GetCoordinatesByCityEvent
import com.example.weatherapphomework.model.City
import com.example.weatherapphomework.model.info.CoordinateInfo
import com.example.weatherapphomework.ui.Presenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.Executor
import javax.inject.Inject

class CityPresenter @Inject constructor(private val cityInteractor: CityInteractor,
                                        private val weatherInteractor: WeatherInteractor) : Presenter<CityScreen>() {

    override fun attachScreen(screen: CityScreen) {
        super.attachScreen(screen)
    }

    override fun detachScreen() {
        super.detachScreen()
    }

    suspend fun showWeatherDetails(context: Context, cityName: String) = withContext(Dispatchers.IO) {

        val coordResult = cityInteractor.getCoordinates(context, cityName)
        screen?.showDetails(cityName, CoordinateInfo(coordResult.coord?.lat, coordResult.coord?.lon))
    }

    suspend fun refreshCityList(context: Context) = withContext(Dispatchers.IO) {

        val cityList = cityInteractor.getCityList()
        cityList.forEach {
            val coordResult = cityInteractor.getCoordinates(context, it.name!!)
            val weatherResult = weatherInteractor.getWeatherInfo(context, it.name, coordResult.coord?.lat!!, coordResult.coord?.lon!!)

            val forecast = weatherResult.daily?.map {
                it.temp?.day
            }

            cityInteractor.updateCity(CityEntity(it.id, it.name, weatherResult.current?.temp, weatherResult.current?.weather?.get(0)?.description, coordResult.coord?.lat, coordResult.coord?.lon, forecast = Forecast(forecast)))
        }
    }

    suspend fun getCityList() = withContext(Dispatchers.IO) {

        val cityEntityList = cityInteractor.getCityList()
        val cities = ArrayList<City>()
        cityEntityList.forEach {
            cities.add(City(it.name, it.temperature, it.weatherString, it.lat, it.lon, it.forecast))
        }

        screen?.loadCities(cities)
    }

    suspend fun saveCity(context: Context, cityName: String) = withContext(Dispatchers.IO) {
        cityInteractor.saveCity(context, cityName)
    }
}