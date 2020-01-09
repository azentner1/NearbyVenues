package com.demo.nearbyvenues.data.repository.device

import androidx.lifecycle.LiveData


interface DeviceRepository {
    fun isLocationPermissionGranted() : Boolean
    fun getScreenSize(): Int
    fun requestPermissionsSettings()
    suspend fun requestedPermissionsSettings() : LiveData<Boolean>
}