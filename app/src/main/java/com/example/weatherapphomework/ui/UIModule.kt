package com.example.weatherapphomework.ui

import android.content.Context
import com.example.weatherapphomework.interactor.CityInteractor
import com.example.weatherapphomework.interactor.WeatherInteractor
import com.example.weatherapphomework.ui.city.CityPresenter
import com.example.weatherapphomework.ui.weather.WeatherPresenter
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import javax.inject.Singleton

@Module
class UIModule(private val context: Context) {

    @Provides
    fun context() = context

    @Provides
    @Singleton
    fun cityPresenter(executor: Executor, cityInteractor: CityInteractor) = CityPresenter(executor, cityInteractor)

    @Provides
    @Singleton
    fun weatherPresenter(executor: Executor, weatherInteractor: WeatherInteractor) = WeatherPresenter(executor, weatherInteractor)

}