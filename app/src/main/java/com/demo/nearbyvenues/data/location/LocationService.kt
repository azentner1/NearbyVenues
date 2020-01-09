package com.demo.nearbyvenues.data.location

import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLngBounds


interface LocationService {
    fun startLocationUpdates()
    suspend fun requestLocationUpdates(): LiveData<Location>
    fun stopLocationUpdates()
    suspend fun getCurrentLocation(): LiveData<Location>
    fun setCurrentBounds(latLngBounds: LatLngBounds)
    suspend fun getCurrentBounds() : LiveData<LatLngBounds>
}