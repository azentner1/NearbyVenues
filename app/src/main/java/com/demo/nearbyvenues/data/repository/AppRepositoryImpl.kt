package com.demo.nearbyvenues.data.repository

import androidx.lifecycle.liveData
import com.demo.nearbyvenues.data.api.ApiDataSource


class AppRepositoryImpl(private val apiDataSource: ApiDataSource) : AppRepository {

    override fun fetchVenues() = liveData {
        emitSource(apiDataSource.fetchVenues())
    }
}
