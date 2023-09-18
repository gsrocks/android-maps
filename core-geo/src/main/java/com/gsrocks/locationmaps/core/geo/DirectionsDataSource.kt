package com.gsrocks.locationmaps.core.geo

import com.gsrocks.locationmaps.core.model.Coordinates
import com.gsrocks.locationmaps.core.model.DirectionRoute

interface DirectionsDataSource {
    suspend fun getDirectionsBetween(start: Coordinates, end: Coordinates): DirectionRoute
}
