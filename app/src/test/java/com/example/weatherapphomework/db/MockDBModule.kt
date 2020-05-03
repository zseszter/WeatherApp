package com.example.weatherapphomework.db

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MockDBModule {
    @Provides
    @Singleton
    fun provideTodoDao(): WeatherDao = MockWeatherDao()
}