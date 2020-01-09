package com.demo.nearbyvenues.data.repository.device

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.core.content.ContextCompat


class DeviceRepositoryImpl(private val context: Context, private val windowManager: WindowManager) : DeviceRepository {

    override fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun getScreenSize(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(displayMetrics)

        return displayMetrics.heightPixels
    }
}