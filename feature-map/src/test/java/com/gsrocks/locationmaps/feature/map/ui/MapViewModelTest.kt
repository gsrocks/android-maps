/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gsrocks.locationmaps.feature.map.ui


import com.gsrocks.locationmaps.core.data.SavedLocationRepository
import com.gsrocks.locationmaps.core.model.Coordinates
import com.gsrocks.locationmaps.core.model.SavedLocation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class MapViewModelTest {
    /*@Test
    fun uiState_initiallyLoading() = runTest {
        val viewModel = MapViewModel(FakeUserLocationRepository())
        assertEquals(viewModel.uiState.first(), UserLocationUiState.Loading)
    }

    @Test
    fun uiState_onItemSaved_isDisplayed() = runTest {
        val viewModel = MapViewModel(FakeUserLocationRepository())
        assertEquals(viewModel.uiState.first(), UserLocationUiState.Loading)
    }*/
}

private class FakeSavedLocationRepository : SavedLocationRepository {

    private val data = mutableListOf<SavedLocation>()

    override val savedLocations: Flow<List<SavedLocation>>
        get() = flow { emit(data.toList()) }

    override suspend fun add(savedLocation: SavedLocation) {
        data.add(
            0,
            SavedLocation(
                coordinates = Coordinates(48.442566, 35.052959),
                title = "Home"
            )
        )
    }
}
