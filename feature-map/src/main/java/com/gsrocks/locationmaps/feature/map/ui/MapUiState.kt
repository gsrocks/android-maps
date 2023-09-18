package com.gsrocks.locationmaps.feature.map.ui

import androidx.annotation.StringRes
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
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
    val route: DirectionRoute? = null,
    val markers: List<SimpleClusterItem> = emptyList()
)

data class SimpleClusterItem(
    val itemPosition: LatLng,
    val itemTitle: String,
    val itemSnippet: String,
) : ClusterItem {
    override fun getPosition(): LatLng =
        itemPosition

    override fun getTitle(): String =
        itemTitle

    override fun getSnippet(): String =
        itemSnippet
}
