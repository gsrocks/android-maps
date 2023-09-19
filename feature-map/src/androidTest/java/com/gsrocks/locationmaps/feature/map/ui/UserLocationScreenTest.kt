package com.gsrocks.locationmaps.feature.map.ui

import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI tests for [MapRoute].
 */
@RunWith(AndroidJUnit4::class)
class UserLocationScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @OptIn(ExperimentalMaterial3Api::class)
    @Before
    fun setup() {
        composeTestRule.setContent {
            MapScreen(
                uiState = MapUiState(),
                snackbarHostState = SnackbarHostState(),
                bottomSheetState = SheetState(skipPartiallyExpanded = false),
                onSearchQueryChange = {},
                onSearchActiveChange = {},
                onSearchAction = {},
                showLocationPermissionRationale = {},
                onRationaleConfirm = {},
                onRationaleDismiss = {},
                onSearchSuggestionClick = {},
                onErrorDismiss = {},
                onMyLocationClick = {},
                onDismissBottomSheet = {},
                onMapClick = {}
            )
        }
    }

    @Test
    fun firstItem_exists() {
        composeTestRule.onNodeWithText(FAKE_DATA.first()).assertExists().performClick()
    }
}

private val FAKE_DATA = listOf("Compose", "Room", "Kotlin")
