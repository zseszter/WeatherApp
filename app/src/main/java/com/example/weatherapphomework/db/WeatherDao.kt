package com.example.weatherapphomework.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.weatherapphomework.db.entities.CityEntity
import com.example.weatherapphomework.db.entities.ForecastEntity
import com.example.weatherapphomework.db.entities.WeatherInfoEntity

@Dao
interface WeatherDao {

    @Query("SELECT * FROM cities")
    fun getAllCities(): List<CityEntity>

    @Query("SELECT * FROM cities WHERE name=:name")
    fun getCityIdByName(name: String?): Long

    @Insert(onConflict = REPLACE)
    fun addCity(cityEntity: CityEntity): Long

    @Query("SELECT * FROM weatherInfo WHERE id=:id")
    fun getWeatherInfoByCityId(id: Int): WeatherInfoEntity

    @Insert(onConflict = REPLACE)
    fun addWeatherInfo(weatherInfoEntity: WeatherInfoEntity): Long

    @Query("SELECT * FROM forecast WHERE id=:id")
    fun getForecastByCityId(id: Int): ForecastEntity

    @Insert(onConflict = REPLACE)
    fun addForecast(forecastEntity: ForecastEntity): Long

    @Query("SELECT * FROM cities WHERE lat=:lat AND lon=:lon")
    fun getCityIdByCoordinates(lat: Double, lon: Double): Long

}