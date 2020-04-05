package com.example.weatherapphomework.city

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import com.example.weatherapphomework.R

class CityActivity : AppCompatActivity(), CityScreen {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        CityPresenter.attachScreen(this)
    }

    override fun onStop() {
        super.onStop()
        CityPresenter.detachScreen()
    }

    override fun updateCityList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
