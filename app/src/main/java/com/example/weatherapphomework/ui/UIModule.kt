package com.example.weatherapphomework.ui

import android.content.Context
import com.example.weatherapphomework.interactor.CityInteractor
import com.example.weatherapphomework.interactor.WeatherInteractor
import com.example.weatherapphomework.ui.city.CityPresenter
import com.example.weatherapphomework.ui.weather.WeatherPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UIModule(private val context: Context) {

    @Provides
    fun context() = context

    @Provides
    @Singleton
    fun cityPresenter(cityInteractor: CityInteractor) = CityPresenter(cityInteractor)

    @Provides
    @Singleton
    fun weatherPresenter(weatherInteractor: WeatherInteractor) = WeatherPresenter(weatherInteractor)

}