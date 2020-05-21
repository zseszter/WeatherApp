package com.example.weatherapphomework.ui.city

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherapphomework.model.City

class CityComparator : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem == newItem
    }
}