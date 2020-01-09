package com.demo.nearbyvenues.ui.venues

import androidx.lifecycle.ViewModel
import com.demo.nearbyvenues.data.model.Venue
import com.demo.nearbyvenues.data.repository.device.DeviceRepository
import com.demo.nearbyvenues.data.repository.venue.VenueRepository

class VenueBottomSheetViewModel(private val venueRepository: VenueRepository, private val deviceRepository: DeviceRepository) : ViewModel() {

    fun getBottomSheetMaxSize() : Int {
        return deviceRepository.getScreenSize() / 3
    }

    fun updateSelectedVenue(venue: Venue) {
        venueRepository.setSelectedVenue(venue)
    }
}
