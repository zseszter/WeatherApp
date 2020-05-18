package com.example.weatherapphomework.ui.city

import android.content.Context
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

    suspend fun refreshCityItem(cityName: String) = withContext(Dispatchers.IO) {
        var coordResult = cityInteractor.getCoordinates(cityName)
        /*if (coordResult.lat == null || coordResult.lon == null) {
            //TODO
        } else {
            var weatherInfoResult = weatherInteractor.getWeatherInfo(coordResult.lat!!, coordResult.lon!!)
            screen.showDetails(weatherInfoResult)
        }*/

        screen?.showDetails(CoordinateInfo(coordResult.lat, coordResult.lon))

    }

    suspend fun getCityList() = withContext(Dispatchers.IO) {
        var cityList = cityInteractor.getCityList()
        screen?.loadCities(cityList)

    }

    suspend fun saveCity(cityName: String) = withContext(Dispatchers.IO) {
        cityInteractor.saveCity(cityName)
    }
}