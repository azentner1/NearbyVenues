package com.demo.nearbyvenues.ui.map

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.demo.nearbyvenues.data.location.LocationService
import com.demo.nearbyvenues.data.repository.device.DeviceRepository

class FragmentMapViewModel(private val deviceRepository: DeviceRepository, private val locationService: LocationService) : ViewModel() {

    private var currentLocation: Location? = null

    fun requestLocationUpdates() = liveData {
        emitSource(locationService.requestLocationUpdates())
    }

    fun setCurrentLocation(location: Location) {
        this.currentLocation = location
    }

    fun isLocationPermissionGranted(): Boolean {
        return deviceRepository.isLocationPermissionGranted()
    }

    fun stopLocationUpdates() {
        locationService.stopLocationUpdates()
    }
}
