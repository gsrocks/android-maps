package com.gsrocks.locationmaps.feature.userlocation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.gsrocks.locationmaps.core.model.LocationAddress
import com.gsrocks.locationmaps.core.ui.MapsLocationSampleTheme
import com.gsrocks.locationmaps.feature.userlocation.R

@Composable
fun UserLocationRoute(
    viewModel: UserLocationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    UserLocationScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onSearchActiveChange = viewModel::onSearchActiveChange,
        onSearchAction = viewModel::onSearchAction,
        showLocationPermissionRationale = viewModel::showLocationPermissionRationale,
        onRationaleConfirm = viewModel::dismissLocationPermissionRationale,
        onRationaleDismiss = viewModel::dismissLocationPermissionRationale,
        onSearchSuggestionClick = viewModel::onSearchSuggestionClick,
        onErrorDismiss = viewModel::errorShown,
        onMyLocationClick = viewModel::onMyLocationClick
    )
}

@Composable
internal fun UserLocationScreen(
    uiState: UserLocationUiState,
    snackbarHostState: SnackbarHostState,
    onSearchQueryChange: (String) -> Unit,
    onSearchActiveChange: (Boolean) -> Unit,
    onSearchAction: (String) -> Unit,
    showLocationPermissionRationale: () -> Unit,
    onRationaleConfirm: () -> Unit,
    onRationaleDismiss: () -> Unit,
    onSearchSuggestionClick: (LocationAddress) -> Unit,
    onErrorDismiss: (Int) -> Unit,
    onMyLocationClick: () -> Unit
) {
    SnackbarEffect(
        errorMessages = uiState.errorMessages,
        snackbarHostState = snackbarHostState,
        onErrorDismiss = onErrorDismiss
    )

    val markerPosition = uiState.markerCoordinates?.let {
        LatLng(it.first, it.second)
    }
    val cameraPositionState = rememberCameraPositionState(uiState.markerCoordinates.toString()) {
        if (markerPosition != null) {
            position = CameraPosition.fromLatLngZoom(markerPosition, 10f)
        }
    }
    LaunchedEffect(markerPosition) {
        if (markerPosition != null) {
            cameraPositionState.animate(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(
                        markerPosition,
                        10f
                    )
                )
            )
        }
    }

    LaunchedEffect(uiState.currentLocation) {
        if (uiState.currentLocation != null) {
            cameraPositionState.animate(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(
                        LatLng(uiState.currentLocation.latitude, uiState.currentLocation.longitude),
                        15f
                    )
                )
            )
        }
    }

    LocationPermissionEffect(showRationale = showLocationPermissionRationale)

    if (uiState.showLocationPermissionRationale) {
        LocationPermissionRationale(
            onConfirm = onRationaleConfirm,
            onDismissRequest = onRationaleDismiss
        )
    }

    if (uiState.showLocationPrecisionRationale) {
        LocationPrecisionRationale(
            onConfirm = onRationaleConfirm,
            onDismissRequest = onRationaleDismiss
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onMyLocationClick) {
                Icon(
                    imageVector = Icons.Default.LocationSearching,
                    contentDescription = stringResource(R.string.my_location)
                )
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { scaffoldPadding ->
        LocationSearchBar(
            query = uiState.query,
            onQueryChange = onSearchQueryChange,
            onSearch = onSearchAction,
            active = uiState.searchActive,
            onActiveChange = onSearchActiveChange,
            suggestions = uiState.suggestions,
            onItemClick = onSearchSuggestionClick
        )

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            contentPadding = scaffoldPadding,
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        ) {
            if (markerPosition != null) {
                Marker(
                    state = MarkerState(position = markerPosition),
                    title = "Singapore",
                    snippet = "Marker in Singapore",
                )
            }
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
    suggestions: List<LocationAddress>,
    modifier: Modifier = Modifier,
    onItemClick: (LocationAddress) -> Unit
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
        LazyColumn {
            items(suggestions) { suggestion ->
                ListItem(
                    headlineContent = {
                        Text(suggestion.featureName.orEmpty())
                    },
                    modifier = Modifier.clickable { onItemClick(suggestion) }
                )
            }
        }
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

@Composable
fun LocationPrecisionRationale(
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Upgrade location precision?") },
        text = { Text("Let's upgrade your location precision, so we can make your experience better!") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Upgrade")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun SnackbarEffect(
    errorMessages: List<Int>,
    snackbarHostState: SnackbarHostState,
    onErrorDismiss: (Int) -> Unit
) {
    if (errorMessages.isNotEmpty()) {
        val errorMessageId = errorMessages.first()
        val errorMessageText = stringResource(errorMessageId)
        LaunchedEffect(errorMessageText, snackbarHostState) {
            snackbarHostState.showSnackbar(message = errorMessageText)
            onErrorDismiss(errorMessageId)
        }
    }
}

@Preview
@Composable
private fun UserLocationScreenPreview() {
    MapsLocationSampleTheme {
        UserLocationScreen(
            uiState = UserLocationUiState(),
            snackbarHostState = SnackbarHostState(),
            onSearchQueryChange = {},
            onSearchActiveChange = {},
            onSearchAction = {},
            showLocationPermissionRationale = {},
            onRationaleConfirm = {},
            onRationaleDismiss = {},
            onSearchSuggestionClick = {},
            onErrorDismiss = {},
            onMyLocationClick = {}
        )
    }
}
