package com.demo.nearbyvenues.data.api.params


class ApiParamsImpl : ApiParams {

    override fun buildFoursquareAccessParams(): HashMap<String, String> {
        val options = HashMap<String, String>()
        options["client_id"] = "YVESX2G2PWEGKBKFLGJJ2HSQWAOWDCSO0DNAKLRPWOGNAH0A"
        options["client_secret"] = "BVIB3OQFBHLLBMNN5GKJRBUGHN514OTDPI5P0T5MEAJMTDIF"
        options["v"] = "20211231"
        return options
    }

    override fun buildVenueParams(): HashMap<String, String> {
        val options = buildFoursquareAccessParams()
        options["ll"] = "40.74224,-73.99386"
        options["intent"] = "browse"
        options["radius"] = "2000"
//        options["query"] = "[food, restaurant, store]"
        options["limit"] = "50"
        return options
    }
}
