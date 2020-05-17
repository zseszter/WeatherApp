package com.example.weatherapphomework.ui.city

import com.example.weatherapphomework.interactor.CityInteractor
import com.example.weatherapphomework.interactor.event.GetCities
import com.example.weatherapphomework.interactor.event.GetCoordinatesByCityEvent
import com.example.weatherapphomework.model.City
import com.example.weatherapphomework.ui.Presenter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.Executor
import javax.inject.Inject

class CityPresenter @Inject constructor(private val executor: Executor, private val cityInteractor: CityInteractor) : Presenter<CityScreen>() {

    override fun attachScreen(screen: CityScreen) {
        super.attachScreen(screen)
        if (!EventBus.getDefault().isRegistered(this)) {
                    EventBus.getDefault().register(this)
                }
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

    fun getCityList() {
        executor.execute{
            cityInteractor.getCityList()
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
                    screen?.updateCityItem(event.cityName, event.lat, event.lon)
                }
            }
        }
    }

    /*@Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: GetCities) {
        if (event.throwable != null) {
            event.throwable?.printStackTrace()
            if (screen != null) {
                screen?.showNetworkError(event.throwable?.message.orEmpty())
            }
        } else {
            if (screen != null) {
                if (event.cityList != null) {
                    var list: ArrayList<City>? = event.cityList
                    screen?.getCities(list)
                }
            }
        }
    }*/
}