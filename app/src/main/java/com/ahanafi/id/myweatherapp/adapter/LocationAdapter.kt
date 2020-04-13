package com.ahanafi.id.myweatherapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahanafi.id.myweatherapp.R
import com.ahanafi.id.myweatherapp.model.Location
import com.ahanafi.id.myweatherapp.view.DetailActivity
import com.ahanafi.id.myweatherapp.view.DetailActivity.Companion.LOCATION_ID
import com.ahanafi.id.myweatherapp.view.DetailActivity.Companion.LOCATION_NAME
import com.ahanafi.id.myweatherapp.view.DetailActivity.Companion.LOCATION_POSITION
import kotlinx.android.synthetic.main.items_location.view.*

class LocationAdapter(
    private val context: Context
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    private var locationList : List<Location> = ArrayList()

    fun setLocation(list: List<Location>){
        this.locationList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = locationList.size

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal fun bind(location: Location){
            itemView.tv_location_name.text = location.title
            itemView.tv_lat_lng.text = location.lattLong

            itemView.setOnClickListener{
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra(LOCATION_NAME, location.title)
                    putExtra(LOCATION_POSITION, location.lattLong)
                    putExtra(LOCATION_ID, location.woeid)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_location, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locationList[position]
        holder.bind(location)
    }
}