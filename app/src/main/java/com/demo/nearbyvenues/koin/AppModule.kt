package com.demo.nearbyvenues.koin

import android.content.Context
import android.view.WindowManager
import com.demo.nearbyvenues.data.api.ApiDataSource
import com.demo.nearbyvenues.data.api.ApiDataSourceImpl
import com.demo.nearbyvenues.data.api.ApiService
import com.demo.nearbyvenues.data.api.params.ApiParams
import com.demo.nearbyvenues.data.api.params.ApiParamsImpl
import com.demo.nearbyvenues.data.location.LocationService
import com.demo.nearbyvenues.data.location.LocationServiceImpl
import com.demo.nearbyvenues.data.repository.device.DeviceRepository
import com.demo.nearbyvenues.data.repository.device.DeviceRepositoryImpl
import com.demo.nearbyvenues.data.repository.venue.VenueRepository
import com.demo.nearbyvenues.data.repository.venue.VenueRepositoryImpl
import com.demo.nearbyvenues.ui.map.FragmentMapViewModel
import com.demo.nearbyvenues.ui.venues.VenueBottomSheetViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModules = module {

    // api
    single<ApiService> { ApiService(get()) }
    single<ApiParams> { ApiParamsImpl() }

    // location
    single<LocationService> { LocationServiceImpl(get()) }

    // repositories
    single<VenueRepository> { VenueRepositoryImpl(get()) }
    single<DeviceRepository> { DeviceRepositoryImpl(get(), get()) }

    // data sources
    single<ApiDataSource> { ApiDataSourceImpl(get(), get()) }

    // system
    factory { androidContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager }

    // view models
    viewModel { FragmentMapViewModel(get(), get(), get()) }
    viewModel { VenueBottomSheetViewModel(get(), get()) }

}
