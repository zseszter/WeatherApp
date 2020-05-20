package com.example.weatherapphomework.ui.weather

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapphomework.interactor.WeatherInteractor
import com.example.weatherapphomework.interactor.event.GetWeatherEvent
import com.example.weatherapphomework.model.WeatherInfoResult
import com.example.weatherapphomework.ui.Presenter
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

    suspend fun refreshWeatherInfo(context: Context, lat: Double?, lon: Double?) {
            if (lat != null && lon != null) {
                var response = weatherInteractor.getWeatherInfo(context, lat, lon)
                screen?.showTemperature(response.current?.temp)
                screen?.showWeatherImage(response.current?.weather?.get(0)?.description)
                screen?.loadForecast(response.daily)
            }
    }

    /*@Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: GetWeatherEvent) {
        if (event.throwable != null) {
            event.throwable?.printStackTrace()
            if (screen != null) {
                screen?.showNetworkError(event.throwable?.message.orEmpty())
            }
        } else {
            if (screen != null) {
                if (event.forecast != null) {
                    screen?.showForecast(event.forecast as MutableList<Double>)
                }
            }
        }
    }*/
}