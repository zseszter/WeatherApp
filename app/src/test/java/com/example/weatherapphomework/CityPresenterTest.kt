package com.example.weatherapphomework

import android.content.Context
import com.example.weatherapphomework.db.entities.CityEntity
import com.example.weatherapphomework.db.entities.Forecast
import com.example.weatherapphomework.interactor.CityInteractor
import com.example.weatherapphomework.interactor.WeatherInteractor
import com.example.weatherapphomework.model.City
import com.example.weatherapphomework.model.CoordinatesResult
import com.example.weatherapphomework.model.WeatherInfoResult
import com.example.weatherapphomework.model.info.*
import com.example.weatherapphomework.ui.city.CityPresenter
import com.example.weatherapphomework.ui.city.CityScreen
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CityPresenterTest {

    private lateinit var presenter: CityPresenter
    private lateinit var cityInteractor: CityInteractor
    private lateinit var weatherInteractor: WeatherInteractor
    private lateinit var screen: CityScreen
    private lateinit var context: Context

    @Before
    fun init() {
        cityInteractor = mockk(relaxed = true)
        weatherInteractor = mockk(relaxed = true)
        screen = mockk(relaxed = true)
        context = mockk(relaxed = true)

        presenter = CityPresenter(cityInteractor, weatherInteractor)
        presenter.attachScreen(screen)

        clearAllMocks()
    }

    @Test
    fun showWeatherDetailsTest() = runBlocking {
        val cityName = "London"
        val coord = CoordinateInfo(12.3, 34.5)

        coEvery {
            cityInteractor.getCoordinates(context, cityName)
        } returns CoordinatesResult(cityName, coord)

        presenter.showWeatherDetails(context, cityName)

        coVerify { cityInteractor.getCoordinates(context, cityName) }
        coVerify { screen.showDetails(any(), any()) }

        confirmVerified(cityInteractor)
        confirmVerified(screen)
    }

    @Test
    fun refreshCityList() = runBlocking {

        val cityName = "London"
        val temperature = 20.4
        val weatherString = "rain"
        val lat = 12.2
        val lon = 10.1
        val forecast = Forecast(listOf(10.1))

        coEvery {
            cityInteractor.getCityList()
        } returns listOf(CityEntity(1, cityName, temperature, weatherString, lat, lon, forecast))

        coEvery {
            cityInteractor.getCoordinates(context, cityName)
        } returns CoordinatesResult(cityName, CoordinateInfo(lat, lon))

        coEvery {
            weatherInteractor.getWeatherInfo(context, cityName, lat, lon)
        } returns WeatherInfoResult(lat, lon)

        presenter.refreshCityList(context)

        coVerify { cityInteractor.getCityList() }
        coVerify { cityInteractor.getCoordinates(context, cityName) }
        coVerify { weatherInteractor.getWeatherInfo(context, cityName, lat, lon) }
        coVerify { cityInteractor.updateCity(any()) }

        confirmVerified(cityInteractor)
        confirmVerified(weatherInteractor)
    }

    @Test
    fun getCitiesTest() = runBlocking {

        val cityName = "London"
        val temperature = 20.4
        val weatherString = "rain"
        val lat = 12.2
        val lon = 10.1
        val forecast = Forecast(listOf(10.1))
        val cities = listOf(City(cityName, temperature, weatherString, lat, lon, forecast))


        coEvery {
            cityInteractor.getCityList()
        } returns listOf(CityEntity(1, cityName, temperature, weatherString, lat, lon, forecast))

        presenter.getCities()

        coVerify { cityInteractor.getCityList() }
        coVerify { screen.loadCities(cities) }

        confirmVerified(cityInteractor)
        confirmVerified(screen)
    }

    @Test
    fun saveCityTest() = runBlocking {

        val cityName = "London"

        presenter.saveCity(context, cityName)

        coVerify { cityInteractor.saveCity(context, cityName) }

        confirmVerified(cityInteractor)
    }
}