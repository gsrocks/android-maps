package com.gsrocks.locationmaps.core.data

import com.gsrocks.locationmaps.core.geocoding.GeocodingDataSource
import com.gsrocks.locationmaps.core.geocoding.models.AddressModel
import javax.inject.Inject

// TODO: add mappers
interface GeocodingRepository {
    suspend fun getFromLocationName(locationName: String): List<AddressModel>

    suspend fun getFromCoordinates(latitude: Double, longitude: Double): List<AddressModel>
}

class DefaultGeocodingRepository @Inject constructor(
    private val geocodingDataSource: GeocodingDataSource
) : GeocodingRepository {
    override suspend fun getFromLocationName(locationName: String): List<AddressModel> {
        return geocodingDataSource.getFromLocationName(locationName)
    }

    override suspend fun getFromCoordinates(
        latitude: Double,
        longitude: Double
    ): List<AddressModel> {
        return geocodingDataSource.getFromCoordinates(latitude, longitude)
    }
}
