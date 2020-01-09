package com.demo.nearbyvenues.ui.venues

import androidx.lifecycle.ViewModel
import com.demo.nearbyvenues.data.model.Venue
import com.demo.nearbyvenues.data.repository.device.DeviceRepository
import com.demo.nearbyvenues.data.repository.location.LocationRepository
import com.demo.nearbyvenues.data.repository.venue.VenueRepository

class VenueBottomSheetViewModel(private val venueRepository: VenueRepository, private val locationRepository: LocationRepository,
                                private val deviceRepository: DeviceRepository) : ViewModel() {

    private var selectedVenue: Venue? = null

    fun updateSelectedVenue(venue: Venue) {
        this.selectedVenue = venue
        venueRepository.setSelectedVenue(venue)
    }

    fun getFilteredAndSortedList(venueList: List<Venue>): List<Venue> {
        var list = venueList
        selectedVenue?.let {
            list = venueList.filter { venue ->  venue.id != it.id }
        }
        return list.sortedBy { it.location.distance }.toMutableList()
    }

    fun requestLocationPermissions() {
        locationRepository.requestLocationPermissions()
    }

    fun requestSettingsPermission() {
        deviceRepository.requestPermissionsSettings()
    }

}
