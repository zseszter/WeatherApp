package com.example.weatherapphomework.ui.city

import com.example.weatherapphomework.interactor.CityInteractor
import com.example.weatherapphomework.ui.Presenter
import javax.inject.Inject

class CityPresenter @Inject constructor(private val cityInteractor: CityInteractor) : Presenter<CityScreen>() {

    override fun attachScreen(screen: CityScreen) {
        super.attachScreen(screen)
    }

    override fun detachScreen() {
        super.detachScreen()
    }
}