package com.demo.nearbyvenues.data.api.params


interface ApiParams {
    fun buildFoursquareAccessParams() : HashMap<String, String>
    fun buildVenueParams() : HashMap<String, String>
}
