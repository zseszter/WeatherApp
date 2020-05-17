package com.example.weatherapphomework.ui.city

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapphomework.R
import com.example.weatherapphomework.injector
import com.example.weatherapphomework.model.City
import javax.inject.Inject

class CityActivity : AppCompatActivity(), CityScreen {

    @Inject
    lateinit var cityPresenter: CityPresenter

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var cities: ArrayList<City> = arrayListOf(City("London", 14.5))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        injector.inject(this)
        cityPresenter.attachScreen(this)
        cityPresenter.getCityList()

        viewManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
        viewAdapter = CityAdapter(this, cities)

        recyclerView = findViewById<RecyclerView>(R.id.cityList).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
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

    override fun getCities(cityList: ArrayList<City>) {
        cities = cityList
    }
}
