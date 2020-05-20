package com.example.weatherapphomework.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.weatherapphomework.db.entities.CityEntity

@Dao
interface WeatherDao {

    @Query("SELECT * FROM cities")
    fun getAllCities(): List<CityEntity>

    @Query("SELECT * FROM cities WHERE name=:name")
    fun getCityByName(name: String?): CityEntity

    @Insert(onConflict = REPLACE)
    fun addCity(cityEntity: CityEntity): Long

    @Query("SELECT * FROM cities WHERE id=:id")
    fun getCity(id: Long): CityEntity

    @Query("SELECT * FROM cities WHERE id=:id")
    fun getForecastByCityId(id: Long): CityEntity

    @Update(onConflict = REPLACE)
    fun updateCity(cityEntity: CityEntity)

    @Query("SELECT * FROM cities WHERE lat=:lat AND lon=:lon")
    fun getCityByCoordinates(lat: Double, lon: Double): List<CityEntity>?


}