package com.gsrocks.locationmaps.core.geo.mappers

import android.location.Address
import com.gsrocks.locationmaps.core.model.Coordinates
import com.gsrocks.locationmaps.core.model.LocationAddress

fun Address.asModel() = LocationAddress(
    coordinates = Coordinates(latitude, longitude),
    featureName = featureName,
    countryName = countryName
)
