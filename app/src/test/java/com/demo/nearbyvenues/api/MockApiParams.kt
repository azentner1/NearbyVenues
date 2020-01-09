package com.demo.nearbyvenues.api

import com.demo.nearbyvenues.BuildConfig
import com.demo.nearbyvenues.data.api.params.ApiParamsImpl
import com.google.android.gms.maps.model.LatLng
import java.text.MessageFormat


class MockApiParams {

    fun buildFoursquareAccessParams(): HashMap<String, String> {
        val options = HashMap<String, String>()
        options[ApiParamsImpl.CLIENT_ID] = BuildConfig.ForsquareClientId
        options[ApiParamsImpl.CLIENT_SECRET] = BuildConfig.ForsquareClientSecret
        options[ApiParamsImpl.V] = BuildConfig.ForsquareUpkeepDate
        return options
    }

    fun buildVenueParams(northEastBound: LatLng, southWestBound: LatLng): HashMap<String, String> {
        val options = buildFoursquareAccessParams()
        options[NE_PARAM] = MessageFormat.format("{0},{1}", northEastBound.latitude, northEastBound.longitude)
        options[SW_PARAM] = MessageFormat.format("{0},{1}", southWestBound.latitude, southWestBound.longitude)
        options[INTENT_PARAM] = INTENT_PARAM_VALUE
//        options["query"] = "[food, restaurant, store]"
        options[LIMIT_PARAM] = LIMIT_PARAM_VALUE
        return options
    }

    companion object {
        const val CLIENT_ID = "client_id"
        const val CLIENT_SECRET = "client_secret"
        const val V = "v"

        const val NE_PARAM = "ne"
        const val SW_PARAM = "sw"
        const val INTENT_PARAM = "intent"
        const val QUERY_PARAM = "query"
        const val LIMIT_PARAM = "limit"

        const val INTENT_PARAM_VALUE = "browse"
        const val LIMIT_PARAM_VALUE = "50"
    }

}