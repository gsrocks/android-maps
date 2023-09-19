package com.gsrocks.locationmaps.core.database.mappers

import com.gsrocks.locationmaps.core.database.entities.SavedLocationEntity
import com.gsrocks.locationmaps.core.model.Coordinates
import com.gsrocks.locationmaps.core.model.SavedLocation

fun SavedLocation.asEntity() = SavedLocationEntity(
    latitude = coordinates.latitude,
    longitude = coordinates.longitude,
    title = title,
    description = description
)

fun SavedLocationEntity.asModel() = SavedLocation(
    coordinates = Coordinates(latitude, longitude),
    title = title,
    description = description
)
