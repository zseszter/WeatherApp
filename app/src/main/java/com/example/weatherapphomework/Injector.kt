package com.example.weatherapphomework

import android.app.Activity

val Activity.cityInjector: WeatherApplicationComponent
    get() {
        return (this.applicationContext as WeatherApplication).injector
    }

val Activity.weatherInjector: WeatherApplicationComponent
    get() {
        return (this.applicationContext as WeatherApplication).injector
    }
