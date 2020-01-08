package com.demo.nearbyvenues.koin

import com.demo.nearbyvenues.data.api.ApiDataSource
import com.demo.nearbyvenues.data.api.ApiDataSourceImpl
import com.demo.nearbyvenues.data.api.ApiService
import com.demo.nearbyvenues.data.api.params.ApiParams
import com.demo.nearbyvenues.data.api.params.ApiParamsImpl
import com.demo.nearbyvenues.data.location.LocationService
import com.demo.nearbyvenues.data.location.LocationServiceImpl
import com.demo.nearbyvenues.data.repository.venue.VenueRepository
import com.demo.nearbyvenues.data.repository.venue.VenueRepositoryImpl
import com.demo.nearbyvenues.ui.map.FragmentMapViewModel
import com.demo.nearbyvenues.ui.venues.VenueBottomSheetViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModules = module {

    // api
    single<ApiService> { ApiService(get()) }
    single<ApiParams> { ApiParamsImpl() }

    // repositories
    single<VenueRepository> { VenueRepositoryImpl(get()) }

    // data sources
    single<ApiDataSource> { ApiDataSourceImpl(get(), get()) }

    // view models
    viewModel { FragmentMapViewModel(get()) }
    viewModel { VenueBottomSheetViewModel(get()) }

    // location
    single<LocationService> { LocationServiceImpl(get()) }
}
