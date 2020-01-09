package com.demo.nearbyvenues.data.repository.venue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.demo.nearbyvenues.data.api.ApiDataSource
import com.demo.nearbyvenues.data.model.Venue
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class VenueRepositoryImpl(private val apiDataSource: ApiDataSource) : VenueRepository {

    private var selectedVenue = MutableLiveData<Venue>()

    override fun fetchVenues(northEastBound: LatLng, southWestBound: LatLng) = liveData {
        emitSource(apiDataSource.fetchVenues(northEastBound, southWestBound))
    }

    override suspend fun getSelectedVenue() : LiveData<Venue> {
        return withContext(Dispatchers.Main) {
            selectedVenue
        }
    }

    override fun setSelectedVenue(venue: Venue) {
        selectedVenue.postValue(venue)
    }

}