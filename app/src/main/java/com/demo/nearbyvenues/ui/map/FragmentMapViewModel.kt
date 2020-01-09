package com.demo.nearbyvenues.ui.map

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.demo.nearbyvenues.data.model.Venue
import com.demo.nearbyvenues.data.repository.device.DeviceRepository
import com.demo.nearbyvenues.data.repository.location.LocationRepository
import com.demo.nearbyvenues.data.repository.venue.VenueRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

class FragmentMapViewModel(private val deviceRepository: DeviceRepository, private val locationRepository: LocationRepository,
                           private val venueRepository: VenueRepository) : ViewModel() {

    private lateinit var selectedVenue: Venue
    private lateinit var currentLocation: Location

    fun requestLocationUpdates() = liveData {
        emitSource(locationRepository.requestLocationUpdates())
    }

    fun fetchNearbyVenues(latLngBounds: LatLngBounds) = liveData {
        emitSource(venueRepository.fetchVenues(latLngBounds.northeast, latLngBounds.southwest))
    }

    fun setCurrentLocation(location: Location) {
        this.currentLocation = location
    }

    fun isLocationPermissionGranted(): Boolean {
        return deviceRepository.isLocationPermissionGranted()
    }

    fun stopLocationUpdates() {
        locationRepository.stopLocationUpdates()
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

    fun getSelectedVenueLatLng(): LatLng {
        return LatLng(selectedVenue.location.lat, selectedVenue.location.lng)
    }

    fun subscribeToPermissionRequests() = liveData {
        emitSource(locationRepository.requestedLocationPermissions())
    }

    fun subscribeToPermissionSettingsRequests() = liveData {
        emitSource(deviceRepository.requestedPermissionsSettings())
    }
}
