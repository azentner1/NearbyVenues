package com.demo.nearbyvenues.data.location

import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.ext.getFullName


class LocationServiceImpl(private val context: Context) : LocationService, LocationCallback() {

    private var currentLocation =  MutableLiveData<Location>()

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private val locationRequest = LocationRequest().also {
        it.interval = LOCATION_FETCH_INTERVAL
        it.fastestInterval = LOCATION_FASTEST_FETCH_INTERVAL
        it.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onLocationResult(locationResult: LocationResult?) {
        locationResult ?: return
        currentLocation.postValue(locationResult.lastLocation)
        super.onLocationResult(locationResult)
    }

    override fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, this, Looper.getMainLooper())
    }

    override suspend fun requestLocationUpdates() : LiveData<Location>  {
        startLocationUpdates()
        return withContext(Dispatchers.Main) {
            currentLocation
        }
    }

    override fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(this)
    }

    companion object {

        private val TAG = LocationServiceImpl::class.getFullName()

        private const val LOCATION_FETCH_INTERVAL: Long = 20 * 1000
        private const val LOCATION_FASTEST_FETCH_INTERVAL: Long = 20 * 1000
    }
}