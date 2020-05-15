package com.example.weatherapphomework.ui.weather

import com.example.weatherapphomework.interactor.WeatherInteractor
import com.example.weatherapphomework.interactor.event.GetWeatherEvent
import com.example.weatherapphomework.ui.Presenter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.Executor
import javax.inject.Inject

class WeatherPresenter @Inject constructor(private val executor: Executor, private val weatherInteractor: WeatherInteractor) : Presenter<WeatherScreen>() {

    override fun attachScreen(screen: WeatherScreen) {
        super.attachScreen(screen)
        EventBus.getDefault().register(this)
    }

    override fun detachScreen() {
        EventBus.getDefault().unregister(this)
        super.detachScreen()
    }

    fun refreshWeatherInfo(lat: Double, lon: Double) {
        executor.execute {
            weatherInteractor.getWeatherInfo(lat, lon)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
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
    }
}