package com.example.weatherapphomework.db

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
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

    @Query("SELECT * FROM cities WHERE id=:id")
    fun getCity(id: Long): CityEntity

    @Query("SELECT * FROM cities WHERE id=:id")
    fun getForecastByCityId(id: Long): CityEntity

    @Update(onConflict = IGNORE)
    fun updateCity(cityEntity: CityEntity)

    /*Insert(onConflict = REPLACE)
    fun addForecast(forecastEntity: ForecastEntity): Long*/

    @Query("SELECT * FROM cities WHERE lat=:lat AND lon=:lon")
    fun getCityIdByCoordinates(lat: Double, lon: Double): Long

}