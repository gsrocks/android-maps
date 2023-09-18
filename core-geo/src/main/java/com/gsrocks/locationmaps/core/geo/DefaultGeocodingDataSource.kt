package com.gsrocks.locationmaps.core.geo

import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.gsrocks.locationmaps.core.geo.mappers.asModel
import com.gsrocks.locationmaps.core.model.LocationAddress
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DefaultGeocodingDataSource @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val geocoder: Geocoder
) : GeocodingDataSource {
    override suspend fun getFromLocationName(
        locationName: String
    ): List<LocationAddress> = withContext(ioDispatcher) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            suspendCoroutine { continuation ->
                val listener = object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: List<Address>) {
                        continuation.resume(addresses.map { it.asModel() })
                    }

                    override fun onError(errorMessage: String?) {
                        continuation.resumeWithException(Exception(errorMessage))
                    }
                }
                geocoder.getFromLocationName(locationName, MAX_RESULTS, listener)
            }
        } else {
            return@withContext geocoder.getFromLocationName(
                locationName,
                MAX_RESULTS
            ).orEmpty().map { it.asModel() }
        }
    }

    override suspend fun getFromCoordinates(
        latitude: Double,
        longitude: Double
    ): List<LocationAddress> = withContext(ioDispatcher) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            suspendCoroutine { continuation ->
                val listener = object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: List<Address>) {
                        continuation.resume(addresses.map { it.asModel() })
                    }

                    override fun onError(errorMessage: String?) {
                        continuation.resumeWithException(Exception(errorMessage))
                    }
                }
                geocoder.getFromLocation(latitude, longitude, MAX_RESULTS, listener)
            }
        } else {
            return@withContext geocoder.getFromLocation(
                latitude,
                longitude,
                MAX_RESULTS
            ).orEmpty().map { it.asModel() }
        }
    }

    companion object {
        const val MAX_RESULTS = 10
    }
}
