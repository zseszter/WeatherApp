package com.example.weatherapphomework.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weatherInfo")
data class WeatherInfoEntity (
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        val cityId: Long?,
        val temperature: Double?,
        val weatherString: String?
)