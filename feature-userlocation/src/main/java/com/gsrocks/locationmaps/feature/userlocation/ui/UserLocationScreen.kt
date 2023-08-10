package com.gsrocks.locationmaps.feature.userlocation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.gsrocks.locationmaps.core.ui.MapsLocationSampleTheme
import com.gsrocks.locationmaps.feature.userlocation.ui.UserLocationUiState.Success

@Composable
fun UserLocationScreen(
    modifier: Modifier = Modifier,
    viewModel: UserLocationViewModel = hiltViewModel()
) {
    val items by viewModel.uiState.collectAsStateWithLifecycle()
    if (items is Success) {
        UserLocationScreen(
            items = (items as Success).data,
            onSave = { name -> viewModel.addUserLocation(name) },
            modifier = modifier
        )
    }
}

@Composable
internal fun UserLocationScreen(
    items: List<String>,
    onSave: (name: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    Scaffold { scaffoldPadding ->
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            contentPadding = scaffoldPadding
        ) {
            Marker(
                state = MarkerState(position = singapore),
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MapsLocationSampleTheme {
        UserLocationScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    MapsLocationSampleTheme {
        UserLocationScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}
