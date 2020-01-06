package com.demo.nearbyvenues.data.repository

import androidx.lifecycle.LiveData
import com.demo.nearbyvenues.data.model.VenueData


interface AppRepository {
    fun fetchVenues(): LiveData<VenueData>
}
