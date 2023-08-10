package com.gsrocks.locationmaps.core.geocoding

import com.gsrocks.locationmaps.core.geocoding.models.AddressModel

interface GeocodingDataSource {
    suspend fun getFromLocationName(locationName: String): List<AddressModel>

    suspend fun getFromCoordinates(latitude: Double, longitude: Double): List<AddressModel>
}
