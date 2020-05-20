package com.example.weatherapphomework.ui.weather

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapphomework.interactor.WeatherInteractor
import com.example.weatherapphomework.interactor.event.GetWeatherEvent
import com.example.weatherapphomework.model.WeatherInfoResult
import com.example.weatherapphomework.ui.Presenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.Executor
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