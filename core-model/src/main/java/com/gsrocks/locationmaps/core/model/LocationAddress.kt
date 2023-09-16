package com.gsrocks.locationmaps.core.model

data class LocationAddress(
    val latitude: Double,
    val longitude: Double,
    val featureName: String?,
    val countryName: String?
)
