package com.demo.nearbyvenues.data.api

import androidx.lifecycle.liveData
import com.demo.nearbyvenues.data.api.params.ApiParams
import com.demo.nearbyvenues.data.api.response.VenuesResponse
import com.demo.nearbyvenues.data.model.VenueData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class ApiDataSourceImpl(private val apiService: ApiService, private val apiParams: ApiParams) : ApiDataSource {

    override fun fetchVenues() = liveData {
        emit(suspendCoroutine<VenueData> {
            apiService.fetchNearbyVenues(apiParams.buildVenueParams()).enqueue(object : Callback<VenuesResponse> {
                override fun onResponse(call: Call<VenuesResponse>, response: Response<VenuesResponse>) {
                    if(response.body() != null && response.isSuccessful) {
                        it.resume(VenueData(venueList = response.body()?.response?.venues ?: listOf(), isSuccess = true))
                    } else {
                        it.resume(VenueData(listOf(), isSuccess = false))
                    }
                }

                override fun onFailure(call: Call<VenuesResponse>, t: Throwable) {
                    it.resume(VenueData(listOf(), isSuccess = false))
                }
            })
        })
    }
}
