package com.gsrocks.locationmaps.feature.userlocation.ui

import androidx.annotation.StringRes
import com.gsrocks.locationmaps.core.common.empty
import com.gsrocks.locationmaps.core.model.LocationAddress

data class UserLocationUiState(
    val query: String = String.empty,
    val searchActive: Boolean = false,
    val showLocationPermissionRationale: Boolean = false,
    val showLocationPrecisionRationale: Boolean = false,
    val suggestions: List<LocationAddress> = emptyList(),
    val markerCoordinates: Pair<Double, Double>? = null,
    @StringRes val errorMessages: List<Int> = emptyList()
)
