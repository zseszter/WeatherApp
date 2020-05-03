package com.example.weatherapphomework.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule {
    companion object {
        private const val DB_NAME = "weather-info"
    }

    @Provides
    @Singleton
    fun provideWeatherInfoDatabase(context: Context): WeatherDatabase {
        return Room.databaseBuilder(context, WeatherDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}