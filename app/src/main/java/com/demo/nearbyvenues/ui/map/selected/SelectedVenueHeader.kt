package com.demo.nearbyvenues.ui.map.selected

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.demo.nearbyvenues.R
import com.demo.nearbyvenues.data.model.Venue
import com.demo.nearbyvenues.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.selected_venue_header.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.MessageFormat


class SelectedVenueHeader(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs),
    KoinComponent {

    private val selectedVenueHeaderViewModel: SelectedVenueHeaderViewModel by inject()

    init {
        inflate(context, R.layout.selected_venue_header, this)
        initUi()
        this.animate().translationYBy(INITIAL_TRANSLATION_Y).setDuration(INITAL_ANIMATION_TIME)
            .start() // set initial state - off screen
    }

    private fun initUi() {

    }

    fun setSelectedVenue(venue: Venue) {
        if (!selectedVenueHeaderViewModel.isHeaderShown()) {
            showHeader()
        }
        txtHeaderVenueName.text = venue.name
        var venueName = ""
        var venueImagePath = ""
        if (venue.categories.isNotEmpty()) {
            venueName = venue.categories[0].name
            venueImagePath = MessageFormat.format("{0}{1}{2}",venue.categories[0].icon.prefix, Constants.CATEGORY_IMAGE_SIZE, venue.categories[0].icon.suffix )
        }
        txtHeaderVenueType.text = venueName
        txtHeaderVenueType.isVisible = venueName.isNotEmpty()
        txtHeaderVenueAddress.text = venue.location.address
        if (venueImagePath.isNotEmpty()) {
            Picasso.get().load(venueImagePath).placeholder(R.drawable.ic_venue_placeholder)
                .into(ivHeaderVenueImage)
        }
    }

    fun isHeaderShown(): Boolean {
        return selectedVenueHeaderViewModel.isHeaderShown()
    }

    private fun showHeader() {
        this.animate().translationYBy(SHOW_TRANSLATION_Y).setDuration(ANIMATION_TIME).start()
        selectedVenueHeaderViewModel.setHeaderShown(shown = true)
    }

    fun closeHeader() {
        this.animate().translationYBy(HIDE_TRANSLATION_Y).setDuration(ANIMATION_TIME).start()
        selectedVenueHeaderViewModel.setHeaderShown(shown = false)
    }

    companion object {
        const val INITIAL_TRANSLATION_Y = -200f
        const val SHOW_TRANSLATION_Y = 240f
        const val HIDE_TRANSLATION_Y = -240f

        const val INITAL_ANIMATION_TIME = 0L
        const val ANIMATION_TIME = 100L
    }

}