package com.demo.nearbyvenues.ui.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.demo.nearbyvenues.R
import com.demo.nearbyvenues.data.model.ErrorMessage
import com.demo.nearbyvenues.data.model.ErrorMessageType
import com.demo.nearbyvenues.utils.SharedPrefsUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.android.viewmodel.ext.android.viewModel


class FragmentMap : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private val mapViewModel: FragmentMapViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap()
        initSelectedVenueListener()
        initPermissionListener()
    }

    override fun onResume() {
        super.onResume()
        locationInitCheck()
    }

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapVenues) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onDestroy() {
        mapViewModel.stopLocationUpdates()
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_CODE && permissions.isNotEmpty() && grantResults.isNotEmpty()) {
            if (permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initLocationListener()
                venueBottomSheet.hideError()
            } else {
                venueBottomSheet.showError(ErrorMessage(getString(R.string.error_location_permission_bottom_sheet),
                    type = ErrorMessageType.LOCATION_PERMISSION_ERROR))
            }
        }
    }

    private fun locationInitCheck() {
        if (mapViewModel.isLocationPermissionGranted()) {
            venueBottomSheet.setStateLoading()
            initLocationListener()
        } else {
            venueBottomSheet.setStateLoaded()
            getPermissions()
        }
    }

    private fun getPermissions() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (SharedPrefsUtil.getBoolean(LOCATION_PERMISSION_DONT_ASK, false)) {
                venueBottomSheet.showError(ErrorMessage(getString(R.string.error_location_permission_settings), type = ErrorMessageType.LOCATION_PERMISSION_ERROR_SETTINGS))
            } else {
                showLocationRationalDialog()
                venueBottomSheet.showError(ErrorMessage(getString(R.string.error_location_permission_settings), type = ErrorMessageType.LOCATION_PERMISSION_ERROR_SETTINGS))
            }
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_CODE)
            venueBottomSheet.showError(ErrorMessage(getString(R.string.error_location_permission_bottom_sheet), type = ErrorMessageType.LOCATION_PERMISSION_ERROR))
        }
    }

    private fun initLocationListener() {
        mapViewModel.requestLocationUpdates().observe(this@FragmentMap, Observer {
            mapViewModel.setCurrentLocation(it)
            updateMap()
            mapViewModel.stopLocationUpdates()
        })
    }

    private fun initSelectedVenueListener() {
        mapViewModel.subscribeToSelectedVenue().observe(this@FragmentMap, Observer {
            ivMapCenterMarker.isVisible = false
            mapViewModel.setSelectedVenue(it)
            selectedVenueHeader.setSelectedVenue(it)
            positionMapAndPin()
        })
    }

    override fun onMapReady(map: GoogleMap) {
        map.apply {
            uiSettings.isCompassEnabled = false
            uiSettings.isZoomControlsEnabled = false
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            venueBottomSheet.post {
                map.setPadding(0, 0, 0, venueBottomSheet.height)
            }
        }.also {
            this.map = it
        }
    }

    private fun updateMap() {
        map.moveCamera(CameraUpdateFactory.newLatLng(mapViewModel.getCurrentLocationLatLng()))
        initMapListeners()
    }

    private fun initMapListeners() {
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                mapViewModel.getCurrentLocationLatLng(),
                MAP_ZOOM_LEVEL
            )
        )
        map.setOnCameraIdleListener {
            fetchVenues()
        }
        map.setOnCameraMoveListener {
            ivMapCenterMarker.isVisible = true
        }
    }

    private fun fetchVenues() {
        venueBottomSheet.setStateLoading()
        mapViewModel.fetchNearbyVenues(map.projection.visibleRegion.latLngBounds).observe(this@FragmentMap, Observer {
            venueBottomSheet.loadVenueList(it)
            venueBottomSheet.setStateLoaded()
        })
    }

    private fun positionMapAndPin() {
        map.clear()
        map.addMarker(MarkerOptions().position(mapViewModel.getSelectedVenueLatLng()))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(mapViewModel.getSelectedVenueLatLng(), 15f))
    }

    private fun onBackPressed() {
        if (selectedVenueHeader.isHeaderShown()) {
            selectedVenueHeader.closeHeader()
        } else {
            activity?.finish()
        }
    }

    private fun initPermissionListener() {
        mapViewModel.subscribeToPermissionRequests().observe(this@FragmentMap, Observer {
            getPermissions()
        })
        mapViewModel.subscribeToPermissionSettingsRequests().observe(this@FragmentMap, Observer {
            openAppSettings()
        })
    }

    private fun showLocationRationalDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("")
            setMessage(getString(R.string.error_location_permission_settings))
            setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
                openAppSettings()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            setNeutralButton(getString(R.string.dont_ask)) { dialog, _ ->
                dialog.dismiss()
                SharedPrefsUtil.setBoolean(LOCATION_PERMISSION_DONT_ASK, true)
            }
            setCancelable(true)
        }.create().show()
    }

    private fun openAppSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", activity?.packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    companion object {
        private const val TAG = "FragmentMap"

        private const val LOCATION_PERMISSION_CODE = 2001
        private const val LOCATION_PERMISSION_DONT_ASK = "location_permission_dont_ask"

        private const val MAP_ZOOM_LEVEL = 10f
    }
}
