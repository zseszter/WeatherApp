package com.example.weatherapphomework

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.room.Embedded
import com.example.weatherapphomework.db.WeatherDao
import com.example.weatherapphomework.db.entities.CityEntity
import com.example.weatherapphomework.db.entities.Forecast
import com.example.weatherapphomework.interactor.CityInteractor
import com.example.weatherapphomework.model.City
import com.example.weatherapphomework.model.CoordinatesResult
import com.example.weatherapphomework.model.WeatherInfoResult
import com.example.weatherapphomework.model.info.*
import com.example.weatherapphomework.network.NetworkConfig
import com.example.weatherapphomework.network.WeatherApi
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CityInteractorTest {

    private lateinit var interactor: CityInteractor
    private lateinit var weatherApi: WeatherApi
    private lateinit var weatherDao: WeatherDao
    private lateinit var context: Context
    private lateinit var connectivityManager: ConnectivityManager


    @Before
    fun init() {
        weatherApi = mockk(relaxed = true)
        weatherDao = mockk(relaxed = true)
        context = mockk(relaxed = true)
        connectivityManager = mockk(relaxed = true)

        interactor = CityInteractor(weatherApi, weatherDao)
        clearAllMocks()
    }

    @Test
    fun getCoordinatesOnlineTest() = runBlocking {
        val cityName = "London"
        val lat = 10.0
        val lon = 10.1
        val desc = listOf(WeatherStringInfo("rain"))
        val main = MainWeatherInfo(12.0, 40)
        val dt = 200

        coEvery {
            weatherApi.getCoordinatesByCity(cityName, NetworkConfig.API_KEY)
        } returns CoordinatesResult(cityName, CoordinateInfo(lat, lon), desc, main, dt)

        coEvery {
            context.getSystemService(any())
        } returns connectivityManager

        coEvery {
            connectivityManager.getNetworkCapabilities(any())
        } returns NetworkCapabilities(null)

        val result = interactor.getCoordinates(context, cityName = cityName)

        coVerify { weatherApi.getCoordinatesByCity(cityName, NetworkConfig.API_KEY) }
        coVerify { context.getSystemService(any()) }
        coVerify { connectivityManager.getNetworkCapabilities(any()) }
        coVerify { connectivityManager.activeNetwork }

        confirmVerified (weatherApi)
        confirmVerified(context)
        confirmVerified(connectivityManager)

        Assert.assertEquals(cityName, result.name)
        Assert.assertEquals(lat, result.coord?.lat)
        Assert.assertEquals(lon, result.coord?.lon)
        Assert.assertEquals(desc.get(0).description, result.weather?.get(0)?.description)
        Assert.assertEquals(dt, result.dt)

    }

    @Test
    fun getCoordinatesNotOnlineTest() = runBlocking {
        val cityName = "London"
        val temperature = 12.1
        val weatherString = "rain"
        val lat = 12.2
        val lon = 10.1
        val forecast = Forecast(listOf(10.1))

        coEvery {
            weatherDao.getCityByName(cityName)
        } returns CityEntity(1, cityName, temperature, weatherString, lat, lon, forecast)

        coEvery {
            context.getSystemService(any())
        } returns connectivityManager

        coEvery {
            connectivityManager.getNetworkCapabilities(any())
        } returns null

        val result = interactor.getCoordinates(context, cityName = cityName)

        coVerify { weatherDao.getCityByName(cityName) }
        coVerify { context.getSystemService(any()) }
        coVerify { connectivityManager.getNetworkCapabilities(any()) }
        coVerify { connectivityManager.activeNetwork }

        confirmVerified (weatherDao)
        confirmVerified(context)
        confirmVerified(connectivityManager)

        Assert.assertEquals(cityName, result.name)
        Assert.assertEquals(temperature, result.main?.temp)
        Assert.assertEquals(weatherString, result.weather?.get(0)?.description)
        Assert.assertEquals(lat, result.coord?.lat)
        Assert.assertEquals(lon, result.coord?.lon)
    }

    @Test
    fun isOnlineTrueTest() = runBlocking {

        coEvery {
            context.getSystemService(any())
        } returns connectivityManager

        coEvery {
            connectivityManager.getNetworkCapabilities(any())
        } returns NetworkCapabilities(null)

        val result = interactor.isOnline(context)

        coVerify { context.getSystemService(any()) }
        coVerify { connectivityManager.getNetworkCapabilities(any()) }
        coVerify { connectivityManager.activeNetwork }

        confirmVerified(context)
        confirmVerified(connectivityManager)

        Assert.assertEquals(true, result)
    }

    @Test
    fun isOnlineFalseTest() = runBlocking {

        coEvery {
            context.getSystemService(any())
        } returns connectivityManager

        coEvery {
            connectivityManager.getNetworkCapabilities(any())
        } returns null

        val result = interactor.isOnline(context)

        coVerify { context.getSystemService(any()) }
        coVerify { connectivityManager.getNetworkCapabilities(any()) }
        coVerify { connectivityManager.activeNetwork }

        confirmVerified(context)
        confirmVerified(connectivityManager)

        Assert.assertEquals(false, result)
    }

    @Test
    fun getCityListTest() = runBlocking {
        val name = "London"
        val temp = 20.4

        coEvery {
            weatherDao.getAllCities()
        } returns listOf(CityEntity(name = name, temperature = temp))

        val result = interactor.getCityList()

        coVerify {  weatherDao.getAllCities() }

        confirmVerified(weatherDao)

        Assert.assertEquals(name, result.get(0).name)
        Assert.assertEquals(temp, result.get(0).temperature)
    }

    @Test
    fun saveCityOnlineTest() = runBlocking {
        val cityName = "London"
        val lat = 10.0
        val lon = 10.1
        val desc = listOf(WeatherStringInfo("rain"))
        val main = MainWeatherInfo(12.0, 40)
        val dt = 200
        val temp = 20.2
        val forecast = listOf(ForecastInfo(100, TemperatureInfo(20.4), WeatherStringInfo("rain")))
        val forecastList = listOf(20.4)

        coEvery {
            weatherApi.getCoordinatesByCity(cityName, NetworkConfig.API_KEY)
        } returns CoordinatesResult(cityName, CoordinateInfo(lat, lon), desc, main, dt)

        coEvery {
            weatherApi.getWeatherByCoordinates(lat, lon, NetworkConfig.API_KEY)
        } returns WeatherInfoResult(lat, lon, WeatherInfo(null, null, temp, null, null, desc), forecast)

        coEvery {
            context.getSystemService(any())
        } returns connectivityManager

        coEvery {
            connectivityManager.getNetworkCapabilities(any())
        } returns NetworkCapabilities(null)

        interactor.saveCity(context, cityName)

        coVerify { weatherApi.getCoordinatesByCity(cityName, NetworkConfig.API_KEY) }
        coVerify { weatherApi.getWeatherByCoordinates(lat, lon, NetworkConfig.API_KEY) }
        coVerify { weatherDao.addCity(CityEntity(0, cityName, temp, desc.get(0).description, lat, lon, Forecast(forecastList))) }
        coVerify { context.getSystemService(any()) }
        coVerify { connectivityManager.getNetworkCapabilities(any()) }
        coVerify { connectivityManager.activeNetwork }

        confirmVerified (weatherApi)
        confirmVerified (weatherDao)
        confirmVerified(context)
        confirmVerified(connectivityManager)

    }

    @Test
    fun saveCityNotOnlineTest() = runBlocking {
        val cityName = "London"

        coEvery {
            context.getSystemService(any())
        } returns connectivityManager

        coEvery {
            connectivityManager.getNetworkCapabilities(any())
        } returns null

        interactor.saveCity(context, cityName)

        coVerify { weatherDao.addCity(CityEntity(name = cityName)) }
        coVerify { context.getSystemService(any()) }
        coVerify { connectivityManager.getNetworkCapabilities(any()) }
        coVerify { connectivityManager.activeNetwork }

        confirmVerified (weatherDao)
        confirmVerified(context)
        confirmVerified(connectivityManager)
    }

    @Test
    fun updateCityTest() = runBlocking {

        val cityName = "London"
        val temp = 20.2
        val desc = "rain"
        val lat = 23.4
        val lon = 44.5
        val forecastList = listOf(20.4)

        interactor.updateCity(CityEntity(1, cityName, temp, desc, lat, lon, Forecast(forecastList)))

        coVerify { weatherDao.updateCity(CityEntity(1, cityName, temp, desc, lat, lon, Forecast(forecastList))) }

        confirmVerified(weatherDao)
    }
}