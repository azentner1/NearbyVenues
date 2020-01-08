package com.demo.nearbyvenues.ui.venues

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.demo.nearbyvenues.data.repository.venue.VenueRepository

class   VenueBottomSheetViewModel(private val venueRepository: VenueRepository) : ViewModel() {

    fun fetchNearbyVenues() = liveData {
        emitSource(venueRepository.fetchVenues())
    }

}
