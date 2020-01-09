package com.demo.nearbyvenues.data.repository.device

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class DeviceRepositoryImpl(private val context: Context, private val windowManager: WindowManager) : DeviceRepository {

    private var requestPermissionSettings = MutableLiveData<Boolean>()

    override fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun getScreenSize(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(displayMetrics)

        return displayMetrics.heightPixels
    }

    override fun requestPermissionsSettings() {
        requestPermissionSettings.postValue(true)
    }

    override suspend fun requestedPermissionsSettings(): LiveData<Boolean> {
        return withContext(Dispatchers.Main) {
            requestPermissionSettings
        }
    }
}