package com.example.weatherapphomework.ui.weather

import android.content.Context
import com.example.weatherapphomework.interactor.WeatherInteractor
import com.example.weatherapphomework.ui.Presenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherPresenter @Inject constructor(private val weatherInteractor: WeatherInteractor) : Presenter<WeatherScreen>() {

    override fun attachScreen(screen: WeatherScreen) {
        super.attachScreen(screen)
    }

    override fun detachScreen() {
        super.detachScreen()
    }

    suspend fun refreshWeatherInfo(context: Context, name: String?, lat: Double?, lon: Double?) = withContext(Dispatchers.IO) {

        if (lat != null && lon != null && name != null) {
            val response = weatherInteractor.getWeatherInfo(context, name, lat, lon)
            screen?.showTemperature(response.current?.temp)
            screen?.showWeatherImage(response.current?.weather?.get(0)?.description)
            screen?.loadForecast(response.daily)
        }
    }
}