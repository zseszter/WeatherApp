package com.example.weatherapphomework.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class ForecastEntity (
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        val cityId: Long?,

        @Embedded
        val forecast: Forecast
)