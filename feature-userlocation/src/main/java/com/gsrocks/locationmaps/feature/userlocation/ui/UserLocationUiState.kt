package com.gsrocks.locationmaps.feature.userlocation.ui

import com.gsrocks.locationmaps.core.model.LocationAddress

data class UserLocationUiState(
    val query: String = "",
    val searchActive: Boolean = false,
    val showLocationPermissionRationale: Boolean = false,
    val showLocationPrecisionRationale: Boolean = false,
    val suggestions: List<LocationAddress> = emptyList(),
    val markerCoordinates: Pair<Double, Double>? = null
)
