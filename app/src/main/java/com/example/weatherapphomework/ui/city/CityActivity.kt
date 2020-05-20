package com.example.weatherapphomework.ui.city

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapphomework.R
import com.example.weatherapphomework.injector
import com.example.weatherapphomework.model.City
import com.example.weatherapphomework.model.info.CoordinateInfo
import com.example.weatherapphomework.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_city.*
import kotlinx.android.synthetic.main.city_list.*
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
        const val CITY_NAME_KEY = "CITY_NAME_KEY"
    }

    var context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        injector.inject(this)

        cityPresenter.attachScreen(this)

        if (!isOnline(this)) showNetworkError()

        //viewManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
        cityAdapter = CityAdapter(this, this)
        cityList.adapter = cityAdapter

        /*recyclerView = findViewById<RecyclerView>(R.id.cityList).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = cityAdapter
        }*/

        fab.setOnClickListener {v -> fabOnClickListener(fab) }

        //get all cities from DB
        MainScope().launch {
            cityPresenter.refreshCityList(context)
            cityPresenter.getCityList()
        }
    }

    private fun fabOnClickListener(view: View) {
        with(AlertDialog.Builder(view.context)) {
            setTitle("Add a new city")
            setMessage("Please, consider typing an existing city name")
            val layout = layoutInflater.inflate(R.layout.add_city_dialog, null)
            val addCityEditText = layout.findViewById<EditText>(R.id.addCity_ET)
            setView(layout)
            setNegativeButton("Cancel", null)
            setPositiveButton("Add") { _, _ ->
                MainScope().launch {
                    cityPresenter.saveCity(context, addCityEditText.text.toString())
                    cityPresenter.getCityList()
                }
            }
            show()
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

    fun showNetworkError() {
        with(AlertDialog.Builder(context)) {
            setTitle("No internet connection")
            setMessage("The most recently retrieved data will be displayed")
            setNegativeButton("Cancel", null)
            setPositiveButton("Ok", null)
            show()
        }
    }

    override fun loadCities(cityList: List<City>) {
        runOnUiThread {
            cityAdapter.submitList(cityList.distinctBy {
                it.name}
            )
        }
    }

    override fun showDetails(cityName: String, coords: CoordinateInfo) {
        runOnUiThread{
            //Toast.makeText(this, coords.lat.toString(), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, WeatherActivity::class.java).apply {
                putExtra(LAT_KEY, coords.lat)
                putExtra(LON_KEY, coords.lon)
                putExtra(CITY_NAME_KEY, cityName)
            }
            startActivity(intent)
        }
    }

    override fun onItemClicked(cityName: String) {
        MainScope().launch {
            cityPresenter.showWeatherDetails(context, cityName)
        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null
    }
}
