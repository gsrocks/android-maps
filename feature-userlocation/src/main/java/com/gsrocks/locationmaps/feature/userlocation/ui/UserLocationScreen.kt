package com.gsrocks.locationmaps.feature.userlocation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.gsrocks.locationmaps.core.ui.MapsLocationSampleTheme

@Composable
fun UserLocationRoute(
    viewModel: UserLocationViewModel = hiltViewModel()
) {
    UserLocationScreen()
}

@Composable
internal fun UserLocationScreen() {
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

@Preview
@Composable
private fun UserLocationScreenPreview() {
    MapsLocationSampleTheme {
        UserLocationScreen()
    }
}
