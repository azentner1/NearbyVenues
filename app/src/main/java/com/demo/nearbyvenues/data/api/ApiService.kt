package com.demo.nearbyvenues.data.api

import android.content.Context
import com.demo.nearbyvenues.BuildConfig
import com.demo.nearbyvenues.data.api.response.VenuesResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface ApiService {

    @GET("venues/search")
    fun fetchNearbyVenues(@QueryMap fetchVenueParams: HashMap<String, String>): Call<VenuesResponse>

    companion object {
        operator fun invoke(context: Context) : ApiService {
            val interceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .build()
                return@Interceptor it.proceed(request)
            }

            val httpLoggingInterceptor = HttpLoggingInterceptor()

            if (BuildConfig.DEBUG) {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
            }

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .client(httpClient).baseUrl(BuildConfig.ApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
