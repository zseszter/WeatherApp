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

    @Insert(onConflict = REPLACE)
    fun addCity(cityEntity: CityEntity)

    @Query("SELECT * FROM weatherInfo WHERE id=:id")
    fun getWeatherInfoByCityId(id: Int): WeatherInfoEntity

    @Insert(onConflict = REPLACE)
    fun addWeatherInfo(weatherInfoEntity: WeatherInfoEntity)

    @Query("SELECT * FROM forecast WHERE id=:id")
    fun getForecastByCityId(id: Int): ForecastEntity

    @Insert(onConflict = REPLACE)
    fun addForecast(forecastEntity: ForecastEntity)

}