package com.example.weatherapphomework.ui.city

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapphomework.R
import com.example.weatherapphomework.injector
import com.example.weatherapphomework.model.City
import com.example.weatherapphomework.model.WeatherInfoResult
import com.example.weatherapphomework.model.info.CoordinateInfo
import com.example.weatherapphomework.ui.weather.WeatherActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CityActivity : AppCompatActivity(), CityScreen, CityAdapter.Listener {

    @Inject
    lateinit var cityPresenter: CityPresenter

    private lateinit var recyclerView: RecyclerView
    private lateinit var cityAdapter: CityAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object {
        const val LAT_KEY = "LAT_KEY"
        const val LON_KEY = "LON_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        injector.inject(this)

        cityPresenter.attachScreen(this)

        viewManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
        cityAdapter = CityAdapter(this, this)
        //cityList.adapter = cityAdapter

        recyclerView = findViewById<RecyclerView>(R.id.cityList).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = cityAdapter
        }

        MainScope().launch {
            cityPresenter.saveCity("Amsterdam")
            cityPresenter.getCityList()
        }
    }

    override fun onStart() {
        super.onStart()
        cityPresenter.attachScreen(this)
    }

    override fun onStop() {
        super.onStop()
        cityPresenter.detachScreen()
    }

    override fun updateCityItem(cityName: String?, lat: Double?, lon: Double?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNetworkError(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadCities(cityList: List<City>) {
        runOnUiThread {
            Toast.makeText(this,"loadCities called", Toast.LENGTH_SHORT).show()
            cityAdapter.submitList(cityList.distinctBy {it.name})
        }
    }

    override fun showDetails(coords: CoordinateInfo) {
        runOnUiThread{
            Toast.makeText(this, coords.lat.toString(), Toast.LENGTH_SHORT).show()
        }

        /*val intent = Intent(this, WeatherActivity::class.java).apply {
            putExtra(LAT_KEY, coords.lat)
            putExtra(LON_KEY, coords.lon)
        }
        startActivity(intent)*/
    }

    override fun onItemClicked(cityName: String) {
        MainScope().launch {
            cityPresenter.refreshCityItem(cityName)
        }
    }
}
