package com.demo.nearbyvenues.data.repository.location

import android.location.Location
import androidx.lifecycle.LiveData


interface LocationRepository {
    fun startLocationUpdates()
    suspend fun requestLocationUpdates(): LiveData<Location>
    fun stopLocationUpdates()
    suspend fun getCurrentLocation(): LiveData<Location>
    fun requestLocationPermissions()
    suspend fun requestedLocationPermissions() : LiveData<Boolean>
}