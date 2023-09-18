package com.gsrocks.locationmaps.core.geo.mappers

import com.google.maps.model.LatLng
import com.gsrocks.locationmaps.core.model.Coordinates

fun LatLng.asModel() = Coordinates(
    latitude = lat,
    longitude = lng
)

fun Coordinates.asLatLng() = LatLng(latitude, longitude)
