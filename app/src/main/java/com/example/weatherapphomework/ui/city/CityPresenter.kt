package com.example.weatherapphomework.ui.city

import com.example.weatherapphomework.interactor.CityInteractor
import com.example.weatherapphomework.interactor.event.GetCoordinatesByCityEvent
import com.example.weatherapphomework.ui.Presenter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.Executor
import javax.inject.Inject

class CityPresenter @Inject constructor(private val executor: Executor, private val cityInteractor: CityInteractor) : Presenter<CityScreen>() {

    override fun attachScreen(screen: CityScreen) {
        super.attachScreen(screen)
        EventBus.getDefault().register(this)
    }

    override fun detachScreen() {
        EventBus.getDefault().unregister(this)
        super.detachScreen()
    }

    fun refreshCityItem(cityName: String) {
        executor.execute {
            cityInteractor.getCoordinates(cityName)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: GetCoordinatesByCityEvent) {
        if (event.throwable != null) {
            event.throwable?.printStackTrace()
            if (screen != null) {
                screen?.showNetworkError(event.throwable?.message.orEmpty())
            }
        } else {
            if (screen != null) {
                if (event.lat != null && event.lon != null) {
                    screen?.updateCityList(event.cityName, event.lat, event.lon)
                }
            }
        }
    }
}