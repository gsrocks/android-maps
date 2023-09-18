package com.gsrocks.locationmaps.core.model

data class DirectionRoute(
    val bounds: MapBounds,
    val legs: List<DirectionLeg>
)
