package com.demo.nearbyvenues.ui.venues

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.nearbyvenues.R
import com.demo.nearbyvenues.data.model.ErrorMessage
import com.demo.nearbyvenues.data.model.ErrorMessageType
import com.demo.nearbyvenues.data.model.Venue
import com.demo.nearbyvenues.data.model.VenueData
import com.demo.nearbyvenues.ui.base.decorators.DividerItemDecoration
import com.demo.nearbyvenues.ui.venues.adapter.VenueAdapter
import kotlinx.android.synthetic.main.venue_bottom_sheet.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class VenueBottomSheet(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs),
    KoinComponent {

    private val venueBottomSheetViewModel: VenueBottomSheetViewModel by inject()

    private val venueAdapter by lazy {
        VenueAdapter(onVenueSelected = {
            updateSelectedVenue(it)
        })
    }

    init {
        inflate(context, R.layout.venue_bottom_sheet, this)
        initUi()
    }

    private fun initUi() {
        rvVenueList.layoutManager = LinearLayoutManager(context)
        rvVenueList.addItemDecoration(DividerItemDecoration(ResourcesCompat.getDrawable(resources,
                    R.drawable.recycler_view_divider,null)))
        rvVenueList.adapter = venueAdapter
    }

    fun setStateLoading() {
        pbVenueSheetHeader.isVisible = true
        rvVenueList.isEnabled = false
        flVenueSheetLoading.isVisible = true
        llErrorContainer.isVisible = false
    }

    fun setStateLoaded() {
        pbVenueSheetHeader.isVisible = false
        rvVenueList.isEnabled = true
        flVenueSheetLoading.isVisible = false
    }

    fun loadVenueList(venueData: VenueData) {
        hideError() // just in case
        if (venueData.isSuccess) {
            venueAdapter.setData(venueBottomSheetViewModel.getFilteredAndSortedList(venueData.venueList))
        } else {
            showError(ErrorMessage(context.getString(R.string.error_loading_venue_data)))
        }
    }

    fun showError(errorMessage: ErrorMessage) {
        rvVenueList.isVisible = false
        llErrorContainer.isVisible = true
        pbVenueSheetHeader.isVisible = false
        flVenueSheetLoading.isVisible = false
        txtErrorMessage.text = errorMessage.message
        if (errorMessage.type.equals(ErrorMessageType.LOCATION_PERMISSION_ERROR)) {
            btnLocationPermissionAllow.text = context.getString(R.string.allow)
            btnLocationPermissionAllow.setOnClickListener {
                venueBottomSheetViewModel.requestLocationPermissions()
            }
        }
        if (errorMessage.type.equals(ErrorMessageType.LOCATION_PERMISSION_ERROR_SETTINGS)) {
            btnLocationPermissionAllow.text = context.getString(R.string.settings)
            btnLocationPermissionAllow.setOnClickListener {
                venueBottomSheetViewModel.requestSettingsPermission()
            }
        }
    }

    fun hideError() {
        rvVenueList.isVisible = true
        llErrorContainer.isVisible = false
        txtErrorMessage.text = ""
        btnLocationPermissionAllow.setOnClickListener(null)
    }

    private fun updateSelectedVenue(venue: Venue) {
        venueBottomSheetViewModel.updateSelectedVenue(venue)
        venueAdapter.notifyItemRemoved(venue)
    }

    companion object {
        internal const val TAG = "VenueBottomSheet"
    }

}
