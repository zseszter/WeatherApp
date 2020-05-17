package com.example.weatherapphomework.ui.city

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapphomework.R
import com.example.weatherapphomework.model.City
import kotlinx.android.synthetic.main.city_list_item.view.*

class CityAdapter constructor(
        private val context: Context,
        private val cities: ArrayList<City>) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.city_list_item, viewGroup, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cities[position]

        holder.cityName.text = city.name
        holder.temperature.text = city.temperature!!.toString() + " °C"

        holder.card.setOnClickListener {
            Toast.makeText(context,"Btn clicked",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = cities.size
    //override fun getItemCount() = 10

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var card: CardView = view.cityCard
        var cityName: TextView = view.cityName_tv
        var temperature: TextView = view.temp_tv
    }
}