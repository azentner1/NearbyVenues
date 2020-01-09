package com.demo.nearbyvenues.ui.map.selected

import androidx.lifecycle.ViewModel


class SelectedVenueHeaderViewModel() : ViewModel() {

    private var isShown: Boolean = false

    fun setHeaderShown(shown: Boolean) {
        this.isShown = shown
    }

    fun isHeaderShown(): Boolean {
        return isShown
    }
}