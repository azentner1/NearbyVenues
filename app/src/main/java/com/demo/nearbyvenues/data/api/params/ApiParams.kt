package com.demo.nearbyvenues.data.api.params

import com.google.android.gms.maps.model.LatLng

interface ApiParams {
    fun buildFoursquareAccessParams(): HashMap<String, String>
    fun buildVenueParams(northEastBound: LatLng, southWestBound: LatLng): HashMap<String, String>
}
