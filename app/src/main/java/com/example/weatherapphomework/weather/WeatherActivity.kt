package com.example.weatherapphomework.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapphomework.R

class WeatherActivity : AppCompatActivity(), WeatherScreen {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
    }

    override fun onStart() {
        super.onStart()
        WeatherPresenter.attachScreen(this)
    }

    override fun onStop() {
        super.onStop()
        WeatherPresenter.detachScreen()
    }

    override fun showCityName(name: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTemperature(temp: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showWeatherImage(image: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshWeatherInfo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
