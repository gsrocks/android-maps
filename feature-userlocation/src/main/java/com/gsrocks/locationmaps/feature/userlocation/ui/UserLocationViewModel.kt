package com.gsrocks.locationmaps.feature.userlocation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrocks.locationmaps.core.data.GeocodingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLocationViewModel @Inject constructor(
    private val geocodingRepository: GeocodingRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            val address = geocodingRepository.getFromLocationName("узвіз Крутогірний 12Б")
            Log.d("UserLocationViewModel", address.toString())
        }
    }
}

sealed interface UserLocationUiState {
    data object Loading : UserLocationUiState
    data class Error(val throwable: Throwable) : UserLocationUiState
    data class Success(val data: List<String>) : UserLocationUiState
}
