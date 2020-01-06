package com.demo.nearbyvenues.ui.venues.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.nearbyvenues.R
import com.demo.nearbyvenues.data.model.Venue
import kotlinx.android.synthetic.main.venue_list_item.view.*


class VenueAdapter(private var venueList: MutableList<Venue>) : RecyclerView.Adapter<VenueAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.venue_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(venueList[position])
    }

    override fun getItemCount(): Int {
        return venueList.size
    }

    fun setData(venueList: List<Venue>) {
        this.venueList = venueList.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(venue: Venue) {
            itemView.txtVenueName.text = venue.name
//            itemView.txtVenueType.text = venue.ca.name
            itemView.txtVenueType.text = venue.location.address
        }
    }
}
