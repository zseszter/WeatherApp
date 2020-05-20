package com.example.weatherapphomework.ui.city

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapphomework.R
import com.example.weatherapphomework.model.City
import kotlinx.android.synthetic.main.city_list_item.view.*
import java.math.RoundingMode

class CityAdapter constructor(
        private val context: Context,
        private val listener: Listener) : ListAdapter<City, CityAdapter.ViewHolder>(CityComparator()) {

    interface Listener {
        fun onItemClicked(cityName: String)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.city_list_item, viewGroup, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = getItem(position)

        val temp = city.temperature!!.plus(CityActivity.KELVIN_CONST).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()

        holder.cityName.text = city.name
        holder.temperature.text = "${temp.toString()} °C"

        holder.card.setOnClickListener {
            city.name?.let {
                listener.onItemClicked(it)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var card: CardView = view.cityCard
        var cityName: TextView = view.cityName_tv
        var temperature: TextView = view.temp_tv
    }
}