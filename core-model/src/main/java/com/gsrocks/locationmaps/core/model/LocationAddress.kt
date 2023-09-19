package com.gsrocks.locationmaps.core.model

data class LocationAddress(
    val coordinates: Coordinates,
    val featureName: String?,
    val countryName: String?
)
