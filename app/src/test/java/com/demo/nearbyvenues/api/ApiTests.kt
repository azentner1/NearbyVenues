package com.demo.nearbyvenues.api

import com.demo.nearbyvenues.data.api.ApiService
import com.demo.nearbyvenues.data.api.params.ApiParamsImpl
import com.google.android.gms.maps.model.LatLng
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class ApiTests {

    private lateinit var apiService: ApiService
    private var mockWebServer = MockWebServer()


    @Before
    fun setup() {
        val httpClient = OkHttpClient.Builder()
            .build()

        apiService = Retrofit.Builder()
            .client(httpClient).baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        //this knows to stop working at times, comment out if you get an error that you've already called this
        mockWebServer.start()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testVenuesResponse() {
        val buffer = Buffer()
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(buffer.readFrom(this.javaClass.classLoader?.getResourceAsStream("response1.json")!!))

        mockWebServer.enqueue(response)

        val apiParams = ApiParamsImpl()

        val venueReponse = apiService.fetchNearbyVenues(apiParams.buildVenueParams(LatLng(37.586, -121.801), LatLng(37.257, -122.367))).execute()

        assert(venueReponse.isSuccessful)
        assert(venueReponse.body()?.response?.venues?.isNotEmpty()!!)
        assert(venueReponse.body()?.response?.venues?.filter { it.id == "4b82f26ff964a520c9ee30e3" } != null) // Charleston Park
    }
}