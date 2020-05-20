package com.example.weatherapphomework.ui.weather

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.weatherapphomework.R
import com.example.weatherapphomework.injector
import com.example.weatherapphomework.model.info.ForecastInfo
import com.example.weatherapphomework.ui.city.CityActivity
import com.example.weatherapphomework.ui.city.CityActivity.Companion.CITY_NAME_KEY
import com.example.weatherapphomework.ui.city.CityActivity.Companion.LAT_KEY
import com.example.weatherapphomework.ui.city.CityActivity.Companion.LON_KEY
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.math.RoundingMode
import javax.inject.Inject

class WeatherActivity : AppCompatActivity(), WeatherScreen {

    @Inject
    lateinit var weatherPresenter: WeatherPresenter

    var lat: Double? = null
    var lon: Double? = null
    var cityName: String? = null

    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        injector.inject(this)

        weatherPresenter.attachScreen(this)

        if (!isOnline(this)) showNetworkError()

        lat = intent.extras?.getDouble(LAT_KEY)
        lon = intent.extras?.getDouble(LON_KEY)
        cityName = intent.extras?.getString(CITY_NAME_KEY)

        MainScope().launch {
            weatherPresenter.refreshWeatherInfo(context, cityName, lat, lon)
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

    override fun showTemperature(temp: Double?) {
        runOnUiThread {
            if (temp != null) {
                val roundedTemp = temp.plus(CityActivity.KELVIN_CONST).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
                temperature_tv?.text = roundedTemp.toString()
            }
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
        MainScope().launch {
            weatherPresenter.refreshWeatherInfo(context, cityName, lat, lon)
        }
    }

    override fun loadForecast(forecast: List<ForecastInfo>?) {
        runOnUiThread {
            Toast.makeText(this,"Forecast loaded", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showNetworkError() {
        with(AlertDialog.Builder(context)) {
            setTitle("No internet connection")
            setMessage("The most recently retrieved data will be displayed")
            setNegativeButton("Cancel", null)
            setPositiveButton("Ok", null)
            show()
        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null
    }
}
