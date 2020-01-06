package com.demo.nearbyvenues.ui.venues

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.demo.nearbyvenues.data.repository.AppRepository

class VenueBottomSheetViewModel(val appRepository: AppRepository) : ViewModel() {

    fun fetchNearbyVenues() = liveData {
        emitSource(appRepository.fetchVenues())
    }

}
