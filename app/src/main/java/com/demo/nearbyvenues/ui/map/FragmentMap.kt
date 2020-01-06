package com.demo.nearbyvenues.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.demo.nearbyvenues.R
import com.demo.nearbyvenues.ui.venues.VenueBottomSheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.venue_bottom_sheet.*
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentMap : Fragment() {

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
        initBottomSheet()
        showBottomSheet()
    }

    private fun initUi() {

    }

    private fun initBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(clBottomSheetRoot)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
    }

    private fun showBottomSheet() {
        val bottomSheet = VenueBottomSheet.newInstance()
        bottomSheet.show(requireFragmentManager(), "")
    }

    companion object {
        fun newInstance() = FragmentMap()
    }
}
