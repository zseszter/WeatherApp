package com.example.weatherapphomework.ui.city

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import com.example.weatherapphomework.R
import com.example.weatherapphomework.cityInjector
import com.example.weatherapphomework.model.DummyContent
import javax.inject.Inject

class CityActivity : AppCompatActivity(), CityScreen {

    @Inject
    lateinit var cityPresenter: CityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cityInjector.inject(this)
        cityPresenter.attachScreen(this)
    }

    override fun onStart() {
        super.onStart()
        cityPresenter.attachScreen(this)
    }

    override fun onStop() {
        super.onStop()
        cityPresenter.detachScreen()
    }

    //Dummy
    override fun showDetails(item: DummyContent) {
        TODO("not implemented")
    }

    override fun updateCityList(cityName: String?, lat: Double?, lon: Double?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNetworkError(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
