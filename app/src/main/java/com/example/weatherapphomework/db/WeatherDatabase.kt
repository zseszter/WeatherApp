package com.example.weatherapphomework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapphomework.db.entities.CityEntity
import com.example.weatherapphomework.db.entities.ForecastEntity
import com.example.weatherapphomework.db.entities.WeatherInfoEntity

@Database(
        exportSchema = false,
        version = 1,
        entities = [
            CityEntity::class,
            ForecastEntity::class,
            WeatherInfoEntity::class
        ]
)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}

