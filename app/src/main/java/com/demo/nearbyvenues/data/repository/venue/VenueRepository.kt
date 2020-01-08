package com.demo.nearbyvenues.data.repository.venue

import androidx.lifecycle.LiveData
import com.demo.nearbyvenues.data.model.VenueData


interface VenueRepository {
    fun fetchVenues(): LiveData<VenueData>
}