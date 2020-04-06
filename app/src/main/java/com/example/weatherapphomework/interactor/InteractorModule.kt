package com.example.weatherapphomework.interactor

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {
    @Provides
    @Singleton
    fun provideCityInteractor() = CityInteractor()

    @Provides
    @Singleton
    fun provideWeatherInteractor() = WeatherInteractor()
}

