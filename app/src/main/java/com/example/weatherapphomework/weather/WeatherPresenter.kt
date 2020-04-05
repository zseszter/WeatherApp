package com.example.weatherapphomework.weather

import com.example.weatherapphomework.Presenter

object WeatherPresenter : Presenter<WeatherScreen>() {

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