package com.gsrocks.locationmaps.core.geo.di

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import com.google.maps.GeoApiContext
import com.gsrocks.locationmaps.core.geo.DefaultGeocodingDataSource
import com.gsrocks.locationmaps.core.geo.DirectionsDataSource
import com.gsrocks.locationmaps.core.geo.FakeDirectionsDataSource
import com.gsrocks.locationmaps.core.geo.GeocodingDataSource
import com.gsrocks.locationmaps.core.geo.GmsLocationDataSource
import com.gsrocks.locationmaps.core.geo.LocationDataSource
import com.gsrocks.locationmaps.core.geo.geofencing.DefaultGeofencingDataSource
import com.gsrocks.locationmaps.core.geo.geofencing.GeofencingDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GeoModule {
    @Provides
    fun provideGeocoder(@ApplicationContext context: Context) = Geocoder(context)

    @Provides
    fun provideLocationProviderClient(@ApplicationContext context: Context) =
        LocationServices.getFusedLocationProviderClient(context)

    @Provides
    fun provideDirectionsDataSource(geoApiContext: GeoApiContext): DirectionsDataSource =
        FakeDirectionsDataSource()

    @Provides
    fun provideGeofencingClient(@ApplicationContext context: Context) =
        LocationServices.getGeofencingClient(context)
}

@Module
@InstallIn(SingletonComponent::class)
interface BindsGeoModule {
    @Binds
    fun bindGeocodingDataSource(
        defaultGeocodingDataSource: DefaultGeocodingDataSource
    ): GeocodingDataSource

    @Binds
    fun bindLocationDataSource(
        locationDataSource: GmsLocationDataSource
    ): LocationDataSource

    @Binds
    fun bindGeofencingDataSource(
        geofencingDataSource: DefaultGeofencingDataSource
    ): GeofencingDataSource
}
