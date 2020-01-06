package com.demo.nearbyvenues.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.demo.nearbyvenues.data.repository.AppRepository

class FragmentMapViewModel(private val appRepository: AppRepository) : ViewModel() {
    fun fetchNearbyVenues() = liveData {
        emitSource(appRepository.fetchVenues())
    }
}
