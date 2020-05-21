package com.example.weatherapphomework.ui.weather

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

        if (!isOnline(this)) showNetworkState()

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
                temperature_tv?.text = roundedTemp.toString() + "°"
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
                "mist" -> img = R.drawable.mist
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
        /*runOnUiThread {
            Toast.makeText(this,"Forecast loaded", Toast.LENGTH_SHORT).show()
        }*/

        val tempList = forecast?.map {
            it.temp?.day!!.plus(CityActivity.KELVIN_CONST).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        }
        runOnUiThread {
            forecast01.setText(tempList?.get(0).toString())
            forecast02.setText(tempList?.get(1).toString())
            forecast03.setText(tempList?.get(2).toString())
            forecast04.setText(tempList?.get(3).toString())
            forecast05.setText(tempList?.get(4).toString())
            forecast06.setText(tempList?.get(5).toString())
            forecast07.setText(tempList?.get(6).toString())
        }
    }

    private fun showNetworkState() {
        if (!isOnline(context)) {
            network_img.visibility = View.VISIBLE
        } else network_img.visibility = View.GONE

    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null
    }

    override fun onResume() {
        super.onResume()
        showNetworkState()
    }
}
