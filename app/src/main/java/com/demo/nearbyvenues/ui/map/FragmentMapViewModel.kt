package com.demo.nearbyvenues.ui.map

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.demo.nearbyvenues.data.repository.AppRepository

class FragmentMapViewModel(private val appRepository: AppRepository) : ViewModel() {

    private var currentLocation: Location? = null

    fun requestLocationUpdates() = liveData {
        emitSource(appRepository.requestLocationUpdates())
    }

    fun setCurrentLocation(location: Location) {
        this.currentLocation = location
    }

    fun isLocationPermissionGranted(): Boolean {
        return appRepository.isLocationPermissionGranted()
    }
}
