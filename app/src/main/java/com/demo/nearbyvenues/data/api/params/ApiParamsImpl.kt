package com.demo.nearbyvenues.data.api.params

import com.google.android.gms.maps.model.LatLng
import java.text.MessageFormat


class ApiParamsImpl : ApiParams {

    override fun buildFoursquareAccessParams(): HashMap<String, String> {
        val options = HashMap<String, String>()
        options["client_id"] = "YVESX2G2PWEGKBKFLGJJ2HSQWAOWDCSO0DNAKLRPWOGNAH0A"
        options["client_secret"] = "BVIB3OQFBHLLBMNN5GKJRBUGHN514OTDPI5P0T5MEAJMTDIF"
        options["v"] = "20211231"
        return options
    }

    override fun buildVenueParams(northEastBound: LatLng, southWestBound: LatLng): HashMap<String, String> {
        val options = buildFoursquareAccessParams()
        options["ne"] = MessageFormat.format("{0},{1}", northEastBound.latitude, northEastBound.longitude)
        options["sw"] = MessageFormat.format("{0},{1}", southWestBound.latitude, southWestBound.longitude)
        options["intent"] = "browse"
//        options["query"] = "[food, restaurant, store]"
        options["limit"] = "50"
        return options
    }
}
