package com.demo.nearbyvenues.ui.venues

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.nearbyvenues.R
import com.demo.nearbyvenues.data.model.VenueData
import com.demo.nearbyvenues.ui.base.decorators.DividerItemDecoration
import com.demo.nearbyvenues.ui.venues.adapter.VenueAdapter
import kotlinx.android.synthetic.main.venue_bottom_sheet.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class VenueBottomSheet(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), KoinComponent {

    private val venueBottomSheetViewModel: VenueBottomSheetViewModel by inject()

    private val venueAdapter by lazy {
        VenueAdapter(onVenueSelected = {
            venueBottomSheetViewModel.updateSelectedVenue(it)
        })
    }

    init {
        inflate(context, R.layout.venue_bottom_sheet, this)
        initUi()
        post {
            setLayoutParams()
        }
    }

    private fun initUi() {
        rvVenueList.layoutManager = LinearLayoutManager(context)
        rvVenueList.addItemDecoration(DividerItemDecoration(ResourcesCompat.getDrawable(resources, R.drawable.recycler_view_divider, null)))
        rvVenueList.adapter = venueAdapter
    }

    fun loadVenueList(venueData: VenueData) {
        if (venueData.isSuccess) {
            venueAdapter.setData(venueData.venueList)
        } else {
            // show error
        }
    }

    fun setLayoutParams() {
        val params = layoutParams
        params.height = venueBottomSheetViewModel.getBottomSheetMaxSize()
        layoutParams = params
    }

    companion object {
        internal const val TAG = "VenueBottomSheet"
    }

}
