package com.gsrocks.locationmaps.feature.map.ui

import androidx.annotation.StringRes
import com.gsrocks.locationmaps.core.common.empty
import com.gsrocks.locationmaps.core.model.Coordinates
import com.gsrocks.locationmaps.core.model.DirectionRoute
import com.gsrocks.locationmaps.core.model.LocationAddress

data class MapUiState(
    val query: String = String.empty,
    val searchActive: Boolean = false,
    val showLocationPermissionRationale: Boolean = false,
    val showLocationPrecisionRationale: Boolean = false,
    val suggestions: List<LocationAddress> = emptyList(),
    val markerCoordinates: Pair<Double, Double>? = null,
    @StringRes val errorMessages: List<Int> = emptyList(),
    val currentLocation: Coordinates? = null,
    val selectedAddressAddress: LocationAddress? = null,
    val route: DirectionRoute? = null
)