package com.example.weatherapphomework.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity (
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val cityName: String,
        val lat: Double,
        val lon: Double
)