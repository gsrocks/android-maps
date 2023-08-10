package com.gsrocks.locationmaps.core.geocoding.di

import android.content.Context
import android.location.Geocoder
import com.gsrocks.locationmaps.core.geocoding.DefaultGeocodingDataSource
import com.gsrocks.locationmaps.core.geocoding.GeocodingDataSource
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
}

@Module
@InstallIn(SingletonComponent::class)
interface BindsGeocodingModule {
    @Binds
    fun bindGeocodingDataSource(
        defaultGeocodingDataSource: DefaultGeocodingDataSource
    ): GeocodingDataSource
}
