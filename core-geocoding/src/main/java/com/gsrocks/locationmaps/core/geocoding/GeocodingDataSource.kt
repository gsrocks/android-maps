package com.gsrocks.locationmaps.core.geocoding

import com.gsrocks.locationmaps.core.model.LocationAddress

interface GeocodingDataSource {
    suspend fun getFromLocationName(locationName: String): List<LocationAddress>

    suspend fun getFromCoordinates(latitude: Double, longitude: Double): List<LocationAddress>
}
