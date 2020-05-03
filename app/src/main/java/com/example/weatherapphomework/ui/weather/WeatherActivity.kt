package com.example.weatherapphomework.ui.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapphomework.R
import com.example.weatherapphomework.model.DummyContent
import com.example.weatherapphomework.model.WeatherInfo
import com.example.weatherapphomework.weatherInjector
import javax.inject.Inject

class WeatherActivity : AppCompatActivity(), WeatherScreen {

    @Inject
    lateinit var weatherPresenter: WeatherPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        weatherInjector.inject(this)
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

    override fun showForecast(weatherInfos: List<WeatherInfo>?) {
        TODO("not implemented")
    }

    override fun showNetworkError(msg: String?) {
        TODO("not implemented")
    }

    //Dummy
    override fun showDetails(item: DummyContent) {
        TODO("not implemented")
    }
}
