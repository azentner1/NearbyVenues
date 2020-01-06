package com.demo.nearbyvenues.data.model


data class VenueLocation(val address: String, val lat: Double, val lon: Double, val distance: Double, val postalCode: String,
                         val city: String, val country: String)
