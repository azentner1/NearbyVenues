package com.demo.nearbyvenues.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.demo.nearbyvenues.R
import com.demo.nearbyvenues.ui.venues.VenueBottomSheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.venue_bottom_sheet.*
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentMap : Fragment() {

    private val mapViewModel: FragmentMapViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initBottomSheet()
        locationInitCheck()
    }

    private fun initUi() {

    }

    private fun initBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(clBottomSheetRoot)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
        val bottomSheet = VenueBottomSheet.newInstance()
        bottomSheet.show(requireFragmentManager(), "")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
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
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_CODE)
        }
    }

    private fun initLocationListener() {
        mapViewModel.requestLocationUpdates().observe(this@FragmentMap, Observer {
            mapViewModel.setCurrentLocation(it)
            updateMap()
        })
    }

    private fun updateMap() {

    }

    companion object {

        private const val TAG = "FragmentMap"

        private const val LOCATION_PERMISSION_CODE = 2001

        fun newInstance() = FragmentMap()
    }
}
