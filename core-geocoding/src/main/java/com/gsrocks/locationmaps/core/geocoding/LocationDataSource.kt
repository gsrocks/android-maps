package com.gsrocks.locationmaps.core.geocoding

import com.gsrocks.locationmaps.core.model.Coordinates

interface LocationDataSource {
    suspend fun getCurrentLocation(): Coordinates
}
