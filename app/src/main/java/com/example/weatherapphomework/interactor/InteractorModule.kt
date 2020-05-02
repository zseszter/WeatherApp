package com.example.weatherapphomework.interactor

import com.example.weatherapphomework.network.WeatherApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {
    @Provides
    @Singleton
    fun provideCityInteractor(weatherApi: WeatherApi) = CityInteractor(weatherApi)

    @Provides
    @Singleton
    fun provideWeatherInteractor(weatherApi: WeatherApi) = WeatherInteractor(weatherApi)
}

