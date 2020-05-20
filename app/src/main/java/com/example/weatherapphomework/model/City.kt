package com.example.weatherapphomework.model

import com.example.weatherapphomework.db.entities.Forecast

data class City(
        val name: String? = null,
        val temperature: Double? = null,
        val weatherString: String? = null,
        val lat: Double? = null,
        val lon: Double? = null,
        val forecast: Forecast? = null
)