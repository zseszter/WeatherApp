package com.example.weatherapphomework.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weatherInfo")
data class WeatherInfoEntity (
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val cityId: Int,
        val temperature: Double,
        val weatherString: String
)