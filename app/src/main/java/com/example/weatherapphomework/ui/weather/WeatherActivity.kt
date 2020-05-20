package com.example.weatherapphomework.ui.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.weatherapphomework.R
import com.example.weatherapphomework.injector
import com.example.weatherapphomework.model.info.ForecastInfo
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.city_list_item.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherActivity : AppCompatActivity(), WeatherScreen {

    @Inject
    lateinit var weatherPresenter: WeatherPresenter

    companion object {
        const val LAT_KEY = "LAT_KEY"
        const val LON_KEY = "LON_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        injector.inject(this)

        weatherPresenter.attachScreen(this)

        val lat: Double? = intent.extras?.getDouble(LAT_KEY)
        val lon: Double? = intent.extras?.getDouble(LON_KEY)

        val context = this

        MainScope().launch {
            weatherPresenter.refreshWeatherInfo(context, lat, lon)
        }
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

    override fun showTemperature(temp: Double?) {
        runOnUiThread {
            if (temp != null) temp_tv?.text = temp.toString()
        }
    }

    override fun showWeatherImage(desc: String?) {
        runOnUiThread {
            val img: Int
            when (desc) {
                "clear sky" -> img = R.drawable.clear
                "few clouds" -> img = R.drawable.cloud
                "scattered clouds" -> img = R.drawable.cloud
                "overcast clouds" -> img = R.drawable.cloud
                "broken clouds" -> img = R.drawable.cloud
                "shower rain" -> img = R.drawable.rain
                "rain" -> img = R.drawable.rain
                "thunderstorm" -> img = R.drawable.rain
                "snow" -> img = R.drawable.cloud
                "mist" -> img = R.drawable.cloud
                else -> img = R.drawable.clear
            }
            weather_img.setImageResource(img)
            weather_info_string_tv.text = desc
        }
    }

    override fun refreshWeatherInfo() {
        TODO("not implemented")
    }

    override fun loadForecast(forecast: List<ForecastInfo>?) {
        runOnUiThread {
            Toast.makeText(this,"Forecast loaded", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showNetworkError(msg: String?) {
        TODO("not implemented")
    }
}
