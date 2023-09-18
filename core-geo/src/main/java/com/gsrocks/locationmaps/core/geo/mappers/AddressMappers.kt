package com.gsrocks.locationmaps.core.geo.mappers

import android.location.Address
import com.gsrocks.locationmaps.core.model.LocationAddress

fun Address.asModel() = LocationAddress(
    latitude = latitude,
    longitude = longitude,
    featureName = featureName,
    countryName = countryName
)
