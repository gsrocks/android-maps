package com.gsrocks.locationmaps.core.geocoding.mappers

import android.location.Address
import com.gsrocks.locationmaps.core.geocoding.models.AddressModel

fun Address.asModel() = AddressModel(
    latitude = latitude,
    longitude = longitude,
    featureName = featureName,
    countryName = countryName
)
