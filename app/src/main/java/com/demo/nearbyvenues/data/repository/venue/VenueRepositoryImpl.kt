package com.demo.nearbyvenues.data.repository.venue

import androidx.lifecycle.liveData
import com.demo.nearbyvenues.data.api.ApiDataSource


class VenueRepositoryImpl(private val apiDataSource: ApiDataSource) : VenueRepository {

    override fun fetchVenues() = liveData {
        emitSource(apiDataSource.fetchVenues())
    }

}