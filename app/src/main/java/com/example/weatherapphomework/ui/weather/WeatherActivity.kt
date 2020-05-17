package com.example.weatherapphomework.ui.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapphomework.R
import com.example.weatherapphomework.injector
import javax.inject.Inject

class WeatherActivity : AppCompatActivity(), WeatherScreen {

    @Inject
    lateinit var weatherPresenter: WeatherPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        injector.inject(this)
        weatherPresenter.attachScreen(this)
    }

    override fun onStart() {
        super.onStart()
        weatherPresenter.attachScreen(this)
    }

    override fun onStop() {
        super.onStop()
        weatherPresenter.detachScreen()
    }

    override fun showCityName(name: String) {
        TODO("not implemented")
    }

    override fun showTemperature(temp: Double) {
        TODO("not implemented")
    }

    override fun showWeatherImage(image: Int) {
        TODO("not implemented")
    }

    override fun refreshWeatherInfo() {
        TODO("not implemented")
    }

    override fun showForecast(forcast: List<Double>?) {
        TODO("not implemented")
    }

    override fun showNetworkError(msg: String?) {
        TODO("not implemented")
    }
}
