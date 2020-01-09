package com.demo.nearbyvenues.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.demo.nearbyvenues.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.android.viewmodel.ext.android.viewModel


class FragmentMap : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private val mapViewModel: FragmentMapViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initMap()
        initBottomSheet()
        locationInitCheck()
        initVenueClickListener()
    }

    private fun initUi() {

    }

    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapVenues) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    fun initBottomSheet() {

    }

    override fun onDestroy() {
        mapViewModel.stopLocationUpdates()
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_CODE && permissions.isNotEmpty() && grantResults.isNotEmpty()) {
            if (permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initLocationListener()
            } else {
                // todo: show error message
            }
        }
    }

    private fun locationInitCheck() {
        if (mapViewModel.isLocationPermissionGranted()) {
            initLocationListener()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_CODE
            )
        }
    }

    private fun initLocationListener() {
        mapViewModel.requestLocationUpdates().observe(this@FragmentMap, Observer {
            Log.i(TAG, "location: " + it.latitude + ":" + it.longitude)
            mapViewModel.setCurrentLocation(it)
            updateMap()
            mapViewModel.stopLocationUpdates()
        })
    }

    private fun initVenueClickListener() {
        mapViewModel.subscribeToSelectedVenue().observe(this@FragmentMap, Observer {
            mapViewModel.setSelectedVenue(it)
            positionMap()
        })
    }

    override fun onMapReady(map: GoogleMap) {
        map.uiSettings.isCompassEnabled = false
        map.uiSettings.isZoomControlsEnabled = false
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        this.map = map
    }

    private fun updateMap() {
        map.moveCamera(CameraUpdateFactory.newLatLng(mapViewModel.getCurrentLocationLatLng()))
        initMapListeners()
    }

    private fun initMapListeners() {
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                mapViewModel.getCurrentLocationLatLng(),
                15f
            )
        )
        map.setOnCameraIdleListener {
            fetchVenues()
        }
    }

    private fun fetchVenues() {
        mapViewModel.fetchNearbyVenues(map.projection.visibleRegion.latLngBounds).observe(this@FragmentMap, Observer {
            venueBottomSheet.loadVenueList(it)
        })
    }

    private fun positionMap() {

    }

    companion object {
        private const val TAG = "FragmentMap"
        private const val LOCATION_PERMISSION_CODE = 2001

        fun newInstance() = FragmentMap()
    }
}
