package com.example.weatherapphomework.ui.weather

import com.example.weatherapphomework.interactor.WeatherInteractor
import com.example.weatherapphomework.ui.Presenter
import javax.inject.Inject

class WeatherPresenter @Inject constructor(private val weatherInteractor: WeatherInteractor) : Presenter<WeatherScreen>() {

    override fun attachScreen(screen: WeatherScreen) {
        super.attachScreen(screen)
    }

    override fun detachScreen() {
        super.detachScreen()
    }

    fun refreshTriggered() {
        TODO()
    }

}