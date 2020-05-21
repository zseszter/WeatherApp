package com.example.weatherapphomework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapphomework.db.converters.DoubleListConverter
import com.example.weatherapphomework.db.entities.CityEntity
import com.example.weatherapphomework.db.entities.ForecastEntity
import com.example.weatherapphomework.db.entities.WeatherInfoEntity

@Database(
        exportSchema = false,
        version = 4,
        entities = [
            CityEntity::class,
            ForecastEntity::class,
            WeatherInfoEntity::class
        ]
)
@TypeConverters(
        DoubleListConverter::class
)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}

