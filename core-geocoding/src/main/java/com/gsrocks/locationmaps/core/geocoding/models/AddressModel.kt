package com.gsrocks.locationmaps.core.geocoding.models

data class AddressModel(
    val latitude: Double,
    val longitude: Double,
    val featureName: String?,
    val countryName: String?
)
