package com.demo.nearbyvenues.data.api

import androidx.lifecycle.LiveData
import com.demo.nearbyvenues.data.model.VenueData


interface ApiDataSource {
    fun fetchVenues(): LiveData<VenueData>
}
