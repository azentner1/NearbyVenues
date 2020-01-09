package com.demo.nearbyvenues.ui.venues.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.demo.nearbyvenues.R
import com.demo.nearbyvenues.data.model.Venue
import com.demo.nearbyvenues.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.venue_list_item.view.*
import java.text.MessageFormat


class VenueAdapter(private var venueList: MutableList<Venue> = mutableListOf(), private val onVenueSelected: (venue: Venue) -> Unit = {}) : RecyclerView.Adapter<VenueAdapter.ViewHolder>() {

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

    fun notifyItemRemoved(venue: Venue) {
        val index = venueList.indexOf(venue)
        venueList.removeAt(index)
        notifyItemRemoved(index)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(venue: Venue) {
            itemView.txtVenueName.text = venue.name
            var venueName = ""
            var venueImagePath = ""
            if (venue.categories.isNotEmpty()) {
                venueName = venue.categories[0].name
                venueImagePath = MessageFormat.format("{0}{1}{2}", venue.categories[0].icon.prefix, Constants.CATEGORY_IMAGE_SIZE, venue.categories[0].icon.suffix)
            }
            itemView.txtVenueType.text = venueName
            itemView.txtVenueType.isVisible = venueName.isNotEmpty()
            itemView.txtVenueAddress.text = venue.location.address
            itemView.setOnClickListener {
                onVenueSelected.invoke(venue)
            }
            if (venueImagePath.isNotEmpty()) {
                Picasso.get().load(venueImagePath).placeholder(R.drawable.ic_venue_placeholder).into(itemView.ivVenueImage)
            }
        }
    }
}
