package com.gsrocks.locationmaps.feature.userlocation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gsrocks.locationmaps.core.ui.MyApplicationTheme
import com.gsrocks.locationmaps.core.ui.clearFocus
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
    Scaffold(
        modifier = Modifier.clearFocus()
    ) { scaffoldPadding ->
        Column(
            modifier = modifier.padding(scaffoldPadding)
        ) {
            var nameUserLocation by remember { mutableStateOf("Compose") }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = nameUserLocation,
                    onValueChange = { nameUserLocation = it }
                )

                Button(modifier = Modifier.width(96.dp), onClick = { onSave(nameUserLocation) }) {
                    Text("Save")
                }
            }
            items.forEach {
                Text("Saved item: $it")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        UserLocationScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    MyApplicationTheme {
        UserLocationScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}
