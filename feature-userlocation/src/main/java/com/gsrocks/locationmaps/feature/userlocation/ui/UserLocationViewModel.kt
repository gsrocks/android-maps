package com.gsrocks.locationmaps.feature.userlocation.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrocks.locationmaps.core.common.empty
import com.gsrocks.locationmaps.core.data.GeocodingRepository
import com.gsrocks.locationmaps.feature.userlocation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private const val TAG = "UserLocationViewModel"

@HiltViewModel
class UserLocationViewModel @Inject constructor(
    private val geocodingRepository: GeocodingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserLocationUiState())
    val uiState: StateFlow<UserLocationUiState> = _uiState.asStateFlow()

    private val _queryFlow = _uiState.map { it.query }
        .debounce(250.milliseconds)
        .stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(), String.empty)

    init {
        viewModelScope.launch {
            _queryFlow.collectLatest { onSearchQuery(it) }
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

    fun showLocationPermissionRationale() {
        _uiState.update {
            it.copy(
                showLocationPermissionRationale = true
            )
        }
    }

    fun dismissLocationPermissionRationale() {
        _uiState.update {
            it.copy(showLocationPermissionRationale = false)
        }
    }

    private fun onSearchQuery(query: String) {
        viewModelScope.launch {
            geocodingRepository.getAddressByName(query).fold(
                onSuccess = { addresses ->
                    _uiState.update { state ->
                        state.copy(suggestions = addresses)
                    }
                },
                onFailure = {
                    showError(R.string.geocoding_failed)
                }
            )
        }
    }

    fun onSearchSuggestionClick(locationAddress: com.gsrocks.locationmaps.core.model.LocationAddress) {
        _uiState.update {
            it.copy(
                markerCoordinates = locationAddress.latitude to locationAddress.longitude,
                searchActive = false
            )
        }
    }

    fun errorShown(messageId: Int) {
        _uiState.update { state ->
            val errorMessages = state.errorMessages.filterNot { it == messageId }
            state.copy(errorMessages = errorMessages)
        }
    }

    private fun showError(@StringRes id: Int) {
        _uiState.update { state ->
            val errorMessages = state.errorMessages + id
            state.copy(
                errorMessages = errorMessages,
                suggestions = emptyList()
            )
        }
    }
}
