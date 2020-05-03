package com.example.weatherapphomework

import com.example.weatherapphomework.db.DBModule
import com.example.weatherapphomework.interactor.InteractorModule
import com.example.weatherapphomework.network.NetworkModule
import com.example.weatherapphomework.ui.UIModule
import com.example.weatherapphomework.ui.city.CityActivity
import com.example.weatherapphomework.ui.weather.WeatherActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    UIModule::class,
    NetworkModule::class,
    DBModule::class,
    InteractorModule::class])
interface WeatherApplicationComponent {
    fun inject(cityActivity: CityActivity)
    fun inject(weatherActivity: WeatherActivity)
}