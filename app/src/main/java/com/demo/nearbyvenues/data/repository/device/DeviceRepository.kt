package com.demo.nearbyvenues.data.repository.device


interface DeviceRepository {
    fun isLocationPermissionGranted() : Boolean
}