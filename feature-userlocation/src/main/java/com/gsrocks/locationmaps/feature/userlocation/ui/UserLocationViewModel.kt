package com.gsrocks.locationmaps.feature.userlocation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrocks.locationmaps.core.data.GeocodingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLocationViewModel @Inject constructor(
    private val geocodingRepository: GeocodingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserLocationUiState())
    val uiState: StateFlow<UserLocationUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val address = geocodingRepository.getFromLocationName("узвіз Крутогірний 12Б")
            Log.d("UserLocationViewModel", address.toString())
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update {
            it.copy(query = query)
        }
    }

    fun onSearchActiveChange(isActive: Boolean) {
        _uiState.update {
            it.copy(searchActive = isActive, query = "")
        }
    }

    fun onSearchAction(query: String) {
        // TODO: implement
    }
}
