package com.example.weatherapphomework.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity (
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        val name: String? = null,
        val temperature: Double? = null,
        val weatherString: String? = null,
        val lat: Double? = null,
        val lon: Double? = null,

        @Embedded
        val forecast: Forecast? = null
)