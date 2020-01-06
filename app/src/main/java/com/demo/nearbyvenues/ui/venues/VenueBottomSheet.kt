package com.demo.nearbyvenues.ui.venues

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.nearbyvenues.R
import com.demo.nearbyvenues.data.model.VenueData
import com.demo.nearbyvenues.ui.base.decorators.DividerItemDecoration
import com.demo.nearbyvenues.ui.venues.adapter.VenueAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.venue_bottom_sheet.*
import org.koin.android.viewmodel.ext.android.viewModel

class VenueBottomSheet : BottomSheetDialogFragment() {

    private var venueAdapter: VenueAdapter = VenueAdapter(mutableListOf())

    private val venueViewModel: VenueBottomSheetViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.venue_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        venueViewModel.fetchNearbyVenues().observe(this@VenueBottomSheet, Observer {
            loadVenueList(it)
        })
    }


    private fun initUi() {
        rvVenueList.layoutManager = LinearLayoutManager(requireContext())
        rvVenueList.addItemDecoration(DividerItemDecoration(ResourcesCompat.getDrawable(resources, R.drawable.recycler_view_divider, null)))
        rvVenueList.adapter = venueAdapter
    }


    private fun loadVenueList(venueData: VenueData) {
        if (venueData.isSuccess) {
            venueAdapter.setData(venueData.venueList)
        } else {
            // show error
        }
    }

    companion object {
        fun newInstance() = VenueBottomSheet()
    }

}
