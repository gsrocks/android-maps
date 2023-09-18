package com.gsrocks.locationmaps.core.geo

import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.gsrocks.locationmaps.core.geo.mappers.asLatLng
import com.gsrocks.locationmaps.core.geo.mappers.asModel
import com.gsrocks.locationmaps.core.model.Coordinates
import com.gsrocks.locationmaps.core.model.DirectionRoute

class GmsDirectionsDataSource(
    private val geoApiContext: GeoApiContext
) : DirectionsDataSource {
    override suspend fun getDirectionsBetween(
        start: Coordinates,
        end: Coordinates
    ): DirectionRoute {
        val result = DirectionsApi.newRequest(geoApiContext)
            .origin(start.asLatLng())
            .destination(end.asLatLng())
            .await()
        val firstRoute = result.routes.first()
        return firstRoute.asModel()
    }
}
