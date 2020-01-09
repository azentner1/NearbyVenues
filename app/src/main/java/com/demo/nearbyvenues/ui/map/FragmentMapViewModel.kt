package com.demo.nearbyvenues.ui.map

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.demo.nearbyvenues.data.location.LocationService
import com.demo.nearbyvenues.data.model.Venue
import com.demo.nearbyvenues.data.repository.device.DeviceRepository
import com.demo.nearbyvenues.data.repository.venue.VenueRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

class FragmentMapViewModel(
    private val deviceRepository: DeviceRepository,
    private val locationService: LocationService,
    private val venueRepository: VenueRepository
) : ViewModel() {

    private var selectedVenue: Venue? = null
    private lateinit var currentLocation: Location

    fun requestLocationUpdates() = liveData {
        emitSource(locationService.requestLocationUpdates())
    }

    fun fetchNearbyVenues(latLngBounds: LatLngBounds) = liveData {
        emitSource(venueRepository.fetchVenues(latLngBounds.northeast, latLngBounds.southwest))
    }

    fun setCurrentLocation(location: Location) {
        this.currentLocation = location
    }

    fun isLocationAvailable(): Boolean {
        return ::currentLocation.isInitialized
    }

    fun isLocationPermissionGranted(): Boolean {
        return deviceRepository.isLocationPermissionGranted()
    }

    fun stopLocationUpdates() {
        locationService.stopLocationUpdates()
    }

    fun subscribeToSelectedVenue() = liveData {
        emitSource(venueRepository.getSelectedVenue())
    }

    fun setSelectedVenue(venue: Venue) {
        this.selectedVenue = venue
    }

    fun getCurrentLocationLatLng(): LatLng {
        return LatLng(currentLocation.latitude, currentLocation.longitude)
    }

    fun setCurrentBounds(latLngBounds: LatLngBounds) {
        locationService.setCurrentBounds(latLngBounds)
    }
}
