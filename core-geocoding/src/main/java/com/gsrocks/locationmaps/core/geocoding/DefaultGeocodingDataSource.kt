package com.gsrocks.locationmaps.core.geocoding

import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.gsrocks.locationmaps.core.geocoding.mappers.asModel
import com.gsrocks.locationmaps.core.geocoding.models.AddressModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DefaultGeocodingDataSource @Inject constructor(
    private val geocoder: Geocoder,
) : GeocodingDataSource {
    // TODO: inject
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    override suspend fun getFromLocationName(
        locationName: String
    ): List<AddressModel> = withContext(ioDispatcher) {
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
    ): List<AddressModel> = withContext(ioDispatcher) {
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
