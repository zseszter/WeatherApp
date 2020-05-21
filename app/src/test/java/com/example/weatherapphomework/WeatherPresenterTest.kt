package com.example.weatherapphomework

import android.content.Context
import com.example.weatherapphomework.interactor.WeatherInteractor
import com.example.weatherapphomework.model.WeatherInfoResult
import com.example.weatherapphomework.model.info.ForecastInfo
import com.example.weatherapphomework.model.info.TemperatureInfo
import com.example.weatherapphomework.model.info.WeatherInfo
import com.example.weatherapphomework.model.info.WeatherStringInfo
import com.example.weatherapphomework.ui.weather.WeatherPresenter
import com.example.weatherapphomework.ui.weather.WeatherScreen
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class WeatherPresenterTest {

    private lateinit var presenter: WeatherPresenter
    private lateinit var weatherInteractor: WeatherInteractor
    private lateinit var screen: WeatherScreen
    private lateinit var context: Context

    @Before
    fun init() {
        weatherInteractor = mockk(relaxed = true)
        screen = mockk(relaxed = true)
        context = mockk(relaxed = true)

        presenter = WeatherPresenter(weatherInteractor)
        presenter.attachScreen(screen)

        clearAllMocks()
    }

    @Test
    fun refreshWeatherInfoTest() = runBlocking {

        val cityName = "London"
        val temperature = 12.1
        val weatherString = "rain"
        val lat = 12.2
        val lon = 10.1
        val current = WeatherInfo(null, null, temperature, null, null, listOf(WeatherStringInfo("rain")))
        val daily = listOf(ForecastInfo(null, TemperatureInfo(20.4), WeatherStringInfo("rain")))

        coEvery {
            weatherInteractor.getWeatherInfo(context, cityName, lat, lon)
        } returns WeatherInfoResult(lat, lon, current, daily)

        presenter.refreshWeatherInfo(context, cityName, lat, lon)

        coVerify { weatherInteractor.getWeatherInfo(context, cityName, lat, lon) }
        coVerify { screen.showTemperature(temperature) }
        coVerify { screen.showWeatherImage(weatherString) }
        coVerify { screen.loadForecast(daily) }

        confirmVerified(weatherInteractor)
        confirmVerified(screen)
    }

}