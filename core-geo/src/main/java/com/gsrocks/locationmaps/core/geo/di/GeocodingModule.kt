package com.gsrocks.locationmaps.core.geo.di

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import com.gsrocks.locationmaps.core.geo.DefaultGeocodingDataSource
import com.gsrocks.locationmaps.core.geo.DefaultLocationDataSource
import com.gsrocks.locationmaps.core.geo.GeocodingDataSource
import com.gsrocks.locationmaps.core.geo.LocationDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GeocodingModule {
    @Provides
    fun provideGeocoder(@ApplicationContext context: Context) = Geocoder(context)

    @Provides
    fun provideLocationProviderClient(@ApplicationContext context: Context) =
        LocationServices.getFusedLocationProviderClient(context)
}

@Module
@InstallIn(SingletonComponent::class)
interface BindsGeocodingModule {
    @Binds
    fun bindGeocodingDataSource(
        defaultGeocodingDataSource: DefaultGeocodingDataSource
    ): GeocodingDataSource

    @Binds
    fun bindLocationDataSource(
        defaultLocationDataSource: DefaultLocationDataSource
    ): LocationDataSource
}
