package com.demo.nearbyvenues.data.repository.venue

import androidx.lifecycle.LiveData
import com.demo.nearbyvenues.data.model.Venue
import com.demo.nearbyvenues.data.model.VenueData
import com.google.android.gms.maps.model.LatLng


interface VenueRepository {
    fun fetchVenues(northEastBound: LatLng, southWestBound: LatLng): LiveData<VenueData>
    suspend fun getSelectedVenue(): LiveData<Venue>
    fun setSelectedVenue(venue: Venue)
}