package com.gsrocks.locationmaps.feature.userlocation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrocks.locationmaps.core.data.UserLocationRepository
import com.gsrocks.locationmaps.feature.userlocation.ui.UserLocationUiState.Error
import com.gsrocks.locationmaps.feature.userlocation.ui.UserLocationUiState.Loading
import com.gsrocks.locationmaps.feature.userlocation.ui.UserLocationUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLocationViewModel @Inject constructor(
    private val userLocationRepository: UserLocationRepository
) : ViewModel() {

    val uiState: StateFlow<UserLocationUiState> = userLocationRepository
        .userLocations.map<List<String>, UserLocationUiState> { Success(data = it) }
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun addUserLocation(name: String) {
        viewModelScope.launch {
            userLocationRepository.add(name)
        }
    }
}

sealed interface UserLocationUiState {
    object Loading : UserLocationUiState
    data class Error(val throwable: Throwable) : UserLocationUiState
    data class Success(val data: List<String>) : UserLocationUiState
}
