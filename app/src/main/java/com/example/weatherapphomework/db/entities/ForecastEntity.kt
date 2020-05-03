package com.example.weatherapphomework.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class ForecastEntity (
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val cityId: Int,
        val one: Double,
        val two: Double,
        val three: Double,
        val four: Double,
        val five: Double,
        val six: Double,
        val seven: Double
)