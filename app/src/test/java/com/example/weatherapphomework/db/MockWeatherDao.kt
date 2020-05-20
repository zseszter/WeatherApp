package com.example.weatherapphomework.db

import com.example.weatherapphomework.db.entities.CityEntity
import com.example.weatherapphomework.db.entities.ForecastEntity
import com.example.weatherapphomework.db.entities.WeatherInfoEntity

class MockWeatherDao: WeatherDao {
    override fun getCityIdByName(name: String?): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addCity(cityEntity: CityEntity): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCity(id: Long): CityEntity {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getForecastByCityId(id: Long): CityEntity {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateCity(cityEntity: CityEntity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCityIdByCoordinates(lat: Double, lon: Double): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllCities(): List<CityEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}