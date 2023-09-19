package com.gsrocks.locationmaps.feature.map.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.gsrocks.locationmaps.core.common.empty
import com.gsrocks.locationmaps.core.data.GeoRepository
import com.gsrocks.locationmaps.core.data.SavedLocationRepository
import com.gsrocks.locationmaps.core.model.Coordinates
import com.gsrocks.locationmaps.core.model.LocationAddress
import com.gsrocks.locationmaps.core.model.SavedLocation
import com.gsrocks.locationmaps.feature.userlocation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
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

@OptIn(FlowPreview::class)
@HiltViewModel
class MapViewModel @Inject constructor(
    private val geoRepository: GeoRepository,
    private val savedLocationRepository: SavedLocationRepository
) : ViewModel() {

    private var reverseGeocodeJob: Job? = null

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    private val _queryFlow = _uiState.map { it.query }
        .debounce(250.milliseconds)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = String.empty
        )

    init {
        viewModelScope.launch {
            _queryFlow.collectLatest { onSearchQuery(it) }
        }

        viewModelScope.launch {
            geoRepository.getDirectionsBetween(
                Coordinates(48.472556, 35.042278),
                Coordinates(48.49869502339745, 35.06941949015215)
            ).fold(
                onSuccess = { route ->
                    _uiState.update { state ->
                        state.copy(route = route)
                    }
                },
                onFailure = {
                    _uiState.update { state ->
                        state.copy(route = null)
                    }
                    showError(R.string.failed_to_get_directions)
                }
            )
        }

        viewModelScope.launch {
            savedLocationRepository.savedLocations.collectLatest { locations ->
                _uiState.update { state ->
                    state.copy(
                        markers = locations.map {
                            SimpleClusterItem(
                                itemPosition = LatLng(
                                    it.coordinates.latitude,
                                    it.coordinates.longitude
                                ),
                                itemTitle = it.title.orEmpty(),
                                itemSnippet = it.description.orEmpty()
                            )
                        }
                    )
                }
            }
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
            geoRepository.getAddressByName(query).fold(
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

    fun onSearchSuggestionClick(locationAddress: LocationAddress) {
        _uiState.update {
            it.copy(
                markerCoordinates = locationAddress.coordinates.latitude to locationAddress.coordinates.longitude,
                searchActive = false
            )
        }
    }

    fun onMyLocationClick() {
        viewModelScope.launch {
            geoRepository.getCurrentLocation().fold(
                onSuccess = { coordinates ->
                    _uiState.update { state ->
                        state.copy(
                            currentLocation = coordinates
                        )
                    }
                },
                onFailure = {
                    showError(R.string.failed_to_get_location)
                }
            )
        }
    }

    fun onMapClick(latLng: LatLng) {
        reverseGeocodeJob?.cancel()

        _uiState.update { state ->
            state.copy(
                clickedLocation = ClickedLocationState(
                    coordinates = Coordinates(
                        latLng.latitude,
                        latLng.longitude
                    ),
                    saved = _uiState.value.markers.any { it.itemPosition == latLng }
                )
            )
        }
        reverseGeocodeJob = viewModelScope.launch {
            geoRepository.getAddressByCoordinates(latLng.latitude, latLng.longitude).fold(
                onSuccess = { addresses ->
                    addresses.firstOrNull()?.let { address ->
                        _uiState.update { state ->
                            val locationState = state.clickedLocation.let {
                                it?.copy(
                                    title = address.featureName.orEmpty()
                                ) ?: ClickedLocationState(
                                    coordinates = address.coordinates,
                                    title = address.featureName.orEmpty()
                                )
                            }
                            state.copy(clickedLocation = locationState)
                        }
                    }
                },
                onFailure = {
                    showError(R.string.geocoding_failed)
                }
            )
        }
    }

    fun onDismissBottomSheet() {
        _uiState.update { state ->
            state.copy(clickedLocation = null)
        }
    }

    fun onToggleSavedLocation() {
        _uiState.value.clickedLocation?.let {
            viewModelScope.launch {
                savedLocationRepository.add(
                    SavedLocation(it.coordinates, it.title, it.description)
                )
            }
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
