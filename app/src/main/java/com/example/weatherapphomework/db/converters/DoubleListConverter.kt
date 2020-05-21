package com.example.weatherapphomework.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class DoubleListConverter {

    private val gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Double> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Double>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Double>): String {
        return gson.toJson(someObjects)
    }
}