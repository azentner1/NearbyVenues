package com.demo.nearbyvenues.data.location

import android.location.Location
import androidx.lifecycle.LiveData


interface LocationService {
    fun startLocationUpdates()
    suspend fun requestLocationUpdates(): LiveData<Location>
    fun stopLocationUpdates()
}