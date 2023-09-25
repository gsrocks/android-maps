package com.gsrocks.locationmaps.core.geo.geofencing

import com.gsrocks.locationmaps.core.model.Coordinates

interface GeofencingDataSource {
    fun createGeofence(
        requestId: String,
        coordinates: Coordinates,
        radius: Float,
        durationMills: Long
    )
}
