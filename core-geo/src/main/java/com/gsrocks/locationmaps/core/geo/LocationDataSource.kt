package com.gsrocks.locationmaps.core.geo

import com.gsrocks.locationmaps.core.model.Coordinates

interface LocationDataSource {
    suspend fun getCurrentLocation(): Coordinates
}
