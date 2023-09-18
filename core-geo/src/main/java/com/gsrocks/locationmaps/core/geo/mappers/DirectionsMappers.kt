package com.gsrocks.locationmaps.core.geo.mappers

import com.google.maps.model.Bounds
import com.google.maps.model.DirectionsLeg
import com.google.maps.model.DirectionsRoute
import com.gsrocks.locationmaps.core.model.DirectionLeg
import com.gsrocks.locationmaps.core.model.DirectionRoute
import com.gsrocks.locationmaps.core.model.MapBounds

fun DirectionsRoute.asModel() = DirectionRoute(
    bounds = bounds.asModel(),
    legs = legs.map { it.asModel() }
)

fun Bounds.asModel() = MapBounds(
    northeast = northeast.asModel(),
    southwest = southwest.asModel()
)

fun DirectionsLeg.asModel() = DirectionLeg(
    start = startLocation.asModel(),
    end = endLocation.asModel()
)
