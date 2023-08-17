package com.gsrocks.locationmaps.feature.userlocation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.gsrocks.locationmaps.core.ui.MapsLocationSampleTheme
import com.gsrocks.locationmaps.feature.userlocation.R

@Composable
fun UserLocationRoute(
    viewModel: UserLocationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UserLocationScreen(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onSearchActiveChange = viewModel::onSearchActiveChange,
        onSearchAction = viewModel::onSearchAction,
        showLocationPermissionRationale = viewModel::showLocationPermissionRationale,
        onRationaleConfirm = viewModel::dismissLocationPermissionRationale,
        onRationaleDismiss = viewModel::dismissLocationPermissionRationale
    )
}

@Composable
internal fun UserLocationScreen(
    uiState: UserLocationUiState,
    onSearchQueryChange: (String) -> Unit,
    onSearchActiveChange: (Boolean) -> Unit,
    onSearchAction: (String) -> Unit,
    showLocationPermissionRationale: () -> Unit,
    onRationaleConfirm: () -> Unit,
    onRationaleDismiss: () -> Unit
) {
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    LocationPermissionEffect(showRationale = showLocationPermissionRationale)

    if (uiState.showLocationRationale) {
        LocationPermissionRationale(
            onConfirm = onRationaleConfirm,
            onDismissRequest = onRationaleDismiss
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.LocationSearching,
                    contentDescription = stringResource(R.string.my_location)
                )
            }
        }
    ) { scaffoldPadding ->
        LocationSearchBar(
            query = uiState.query,
            onQueryChange = onSearchQueryChange,
            onSearch = onSearchAction,
            active = uiState.searchActive,
            onActiveChange = onSearchActiveChange
        )

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            contentPadding = scaffoldPadding,
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        ) {
            Marker(
                state = MarkerState(position = singapore),
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(),
        placeholder = { Text(stringResource(R.string.search_locations)) },
        leadingIcon = {
            if (active) {
                IconButton(
                    onClick = { onActiveChange(false) }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = stringResource(R.string.search_locations)
                )
            }
        },
        trailingIcon = {
            if (active) {
                IconButton(
                    onClick = { onQueryChange("") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.clear_search)
                    )
                }
            }
        }
    ) {
        // TODO: implement
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun LocationPermissionEffect(
    showRationale: () -> Unit
) {
    // Permission requests should only be made from an Activity Context, which is not present
    // in previews
    if (LocalInspectionMode.current) return

    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    LaunchedEffect(locationPermissionState) {
        if (locationPermissionState.shouldShowRationale) {
            showRationale()
        }
        if (!locationPermissionState.allPermissionsGranted &&
            !locationPermissionState.shouldShowRationale
        ) {
            locationPermissionState.launchMultiplePermissionRequest()
        }
    }
}

@Composable
private fun LocationPermissionRationale(
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Allow us to access your location?") },
        text = { Text("We will use it to provide you with useful features like navigation.") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Allow")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Deny")
            }
        }
    )
}

@Preview
@Composable
private fun UserLocationScreenPreview() {
    MapsLocationSampleTheme {
        UserLocationScreen(
            uiState = UserLocationUiState(),
            onSearchQueryChange = {},
            onSearchActiveChange = {},
            onSearchAction = {},
            showLocationPermissionRationale = {},
            onRationaleConfirm = {},
            onRationaleDismiss = {}
        )
    }
}
