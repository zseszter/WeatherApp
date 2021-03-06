package com.example.weatherapphomework.ui.city

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import com.example.weatherapphomework.R
import com.example.weatherapphomework.cityInjector
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

    override fun updateCityList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
