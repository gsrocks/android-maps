package com.gsrocks.locationmaps.feature.userlocation.ui

data class UserLocationUiState(
    val query: String = "",
    val searchActive: Boolean = false,
    val showLocationPermissionRationale: Boolean = false,
    val showLocationPrecisionRationale: Boolean = false
)
