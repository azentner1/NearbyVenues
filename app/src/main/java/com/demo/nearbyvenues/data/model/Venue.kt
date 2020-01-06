package com.demo.nearbyvenues.data.model


data class Venue(val id: String, val name: String, val contact: Contact, val location: VenueLocation,
                 val categories: List<Category>)
