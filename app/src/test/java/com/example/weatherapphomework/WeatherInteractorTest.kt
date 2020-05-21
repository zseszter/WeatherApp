package com.example.weatherapphomework

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.weatherapphomework.db.WeatherDao
import com.example.weatherapphomework.db.entities.CityEntity
import com.example.weatherapphomework.db.entities.Forecast
import com.example.weatherapphomework.interactor.WeatherInteractor
import com.example.weatherapphomework.model.WeatherInfoResult
import com.example.weatherapphomework.model.info.ForecastInfo
import com.example.weatherapphomework.model.info.TemperatureInfo
import com.example.weatherapphomework.model.info.WeatherInfo
import com.example.weatherapphomework.model.info.WeatherStringInfo
import com.example.weatherapphomework.network.NetworkConfig
import com.example.weatherapphomework.network.WeatherApi
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WeatherInteractorTest {

    private lateinit var interactor: WeatherInteractor
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

        interactor = WeatherInteractor(weatherApi, weatherDao)
        clearAllMocks()
    }

    @Test
    fun getWeatherInfoOnlineTest() = runBlocking {

        val cityName = "London"
        val temperature = 12.1
        val weatherString = "rain"
        val lat = 12.2
        val lon = 10.1
        val forecast = Forecast(listOf(10.1))
        val current = WeatherInfo(null, null, temperature, null, null, listOf(WeatherStringInfo("rain")))
        val daily = listOf(ForecastInfo(null, TemperatureInfo(20.4), WeatherStringInfo("rain")))

        coEvery {
            weatherDao.getCityByName(cityName)
        } returns CityEntity(1, cityName, temperature, weatherString, lat, lon, forecast)

        coEvery {
            weatherApi.getWeatherByCoordinates(lat, lon, NetworkConfig.API_KEY)
        } returns WeatherInfoResult(lat, lon, current, daily)

        coEvery {
            context.getSystemService(any())
        } returns connectivityManager

        coEvery {
            connectivityManager.getNetworkCapabilities(any())
        } returns NetworkCapabilities(null)

        val result = interactor.getWeatherInfo(context, cityName, lat, lon)

        coVerify { weatherDao.getCityByName(cityName) }
        coVerify { weatherDao.updateCity(any()) }
        coVerify { weatherApi.getWeatherByCoordinates(lat, lon, NetworkConfig.API_KEY) }
        coVerify { context.getSystemService(any()) }
        coVerify { connectivityManager.getNetworkCapabilities(any()) }
        coVerify { connectivityManager.activeNetwork }

        confirmVerified(weatherDao)
        confirmVerified(weatherApi)
        confirmVerified(context)
        confirmVerified(connectivityManager)

        Assert.assertEquals(lat, result.lat)
        Assert.assertEquals(lon, result.lon)
        Assert.assertEquals(current, result.current)
        Assert.assertEquals(daily, result.daily)
    }

    @Test
    fun getWeatherInfoNotOnlineTest() = runBlocking {

        val cityName = "London"
        val temperature = 12.1
        val weatherString = "rain"
        val lat = 12.2
        val lon = 10.1
        val forecast = Forecast(listOf(10.1))
        val current = WeatherInfo(null, null, temperature, null, null, listOf(WeatherStringInfo("rain")))
        val daily = listOf(ForecastInfo(null, TemperatureInfo(10.1), WeatherStringInfo("rain")))

        coEvery {
            weatherDao.getCityByName(cityName)
        } returns CityEntity(1, cityName, temperature, weatherString, lat, lon, forecast)

        coEvery {
            context.getSystemService(any())
        } returns connectivityManager

        coEvery {
            connectivityManager.getNetworkCapabilities(any())
        } returns null

        val result = interactor.getWeatherInfo(context, cityName, lat, lon)

        coVerify { weatherDao.getCityByName(cityName) }
        coVerify { context.getSystemService(any()) }
        coVerify { connectivityManager.getNetworkCapabilities(any()) }
        coVerify { connectivityManager.activeNetwork }

        confirmVerified(weatherDao)
        confirmVerified(context)
        confirmVerified(connectivityManager)

        Assert.assertEquals(lat, result.lat)
        Assert.assertEquals(lon, result.lon)
        Assert.assertEquals(current, result.current)
        Assert.assertEquals(daily, result.daily)
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
}