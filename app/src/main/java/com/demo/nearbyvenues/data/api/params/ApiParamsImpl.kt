package com.demo.nearbyvenues.data.api.params

import com.demo.nearbyvenues.BuildConfig
import com.google.android.gms.maps.model.LatLng
import java.text.MessageFormat


class ApiParamsImpl : ApiParams {

    override fun buildFoursquareAccessParams(): HashMap<String, String> {
        val options = HashMap<String, String>()
        options[CLIENT_ID] = BuildConfig.ForsquareClientId
        options[CLIENT_SECRET] = BuildConfig.ForsquareClientSecret
        options[V] = BuildConfig.ForsquareUpkeepDate
        return options
    }

    override fun buildVenueParams(northEastBound: LatLng, southWestBound: LatLng): HashMap<String, String> {
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
