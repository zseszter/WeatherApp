package com.example.weatherapphomework.interactor

import android.content.Context
import com.example.weatherapphomework.db.WeatherDao
import com.example.weatherapphomework.network.WeatherApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {
    @Provides
    @Singleton
    fun provideCityInteractor(weatherApi: WeatherApi, weatherDao: WeatherDao) = CityInteractor(weatherApi, weatherDao)

    @Provides
    @Singleton
    fun provideWeatherInteractor(weatherApi: WeatherApi, weatherDao: WeatherDao) = WeatherInteractor(weatherApi, weatherDao)
}

