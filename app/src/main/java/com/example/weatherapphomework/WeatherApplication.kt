package com.example.weatherapphomework

import android.app.Application
import com.example.weatherapphomework.ui.UIModule

class WeatherApplication: Application() {
    lateinit var injector: WeatherApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injector = DaggerWeatherApplicationComponent.builder().uIModule(UIModule(this)).build()
    }
}