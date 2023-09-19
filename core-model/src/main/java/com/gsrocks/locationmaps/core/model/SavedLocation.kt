package com.gsrocks.locationmaps.core.model

data class SavedLocation(
    val coordinates: Coordinates,
    val title: String? = null,
    val description: String? = null
)
