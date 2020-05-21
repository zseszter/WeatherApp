package com.example.weatherapphomework

import android.app.Activity

val Activity.injector: WeatherApplicationComponent
    get() = (this.applicationContext as WeatherApplication).injector