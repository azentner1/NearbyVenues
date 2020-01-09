package com.demo.nearbyvenues.data.api

import androidx.lifecycle.LiveData
import com.demo.nearbyvenues.data.model.VenueData
import com.google.android.gms.maps.model.LatLng


interface ApiDataSource {
    fun fetchVenues(northEastBound: LatLng, southWestBound: LatLng): LiveData<VenueData>
}
