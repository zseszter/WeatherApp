package com.example.weatherapphomework.db

import com.example.weatherapphomework.db.entities.CityEntity
import com.example.weatherapphomework.db.entities.ForecastEntity
import com.example.weatherapphomework.db.entities.WeatherInfoEntity

class MockWeatherDao: WeatherDao {
    override fun getCityIdByCoordinates(lat: Double, lon: Double): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllCities(): List<CityEntity> {
        TODO("not implemented")
    }

    override fun getCityIdByName(name: String?): Long {
        TODO("not implemented")
    }

    override fun addCity(cityEntity: CityEntity): Long {
        TODO("not implemented")
    }

    override fun getWeatherInfoByCityId(id: Int): WeatherInfoEntity {
        TODO("not implemented")
    }

    override fun addWeatherInfo(weatherInfoEntity: WeatherInfoEntity): Long {
        TODO("not implemented")
    }

    override fun getForecastByCityId(id: Int): ForecastEntity {
        TODO("not implemented")
    }

    override fun addForecast(forecastEntity: ForecastEntity): Long {
        TODO("not implemented")
    }
}