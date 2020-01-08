package com.demo.nearbyvenues.data.repository

import com.demo.nearbyvenues.data.location.LocationService
import com.demo.nearbyvenues.data.repository.device.DeviceRepository
import com.demo.nearbyvenues.data.repository.venue.VenueRepository

interface AppRepository : LocationService, VenueRepository, DeviceRepository
