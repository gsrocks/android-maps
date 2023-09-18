package com.gsrocks.locationmaps.core.geo

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.gsrocks.locationmaps.core.model.Coordinates
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DefaultLocationDataSource @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val locationProviderClient: FusedLocationProviderClient
) : LocationDataSource {
    // TODO: make cancellable
    override suspend fun getCurrentLocation(): Coordinates = withContext(ioDispatcher) {
        return@withContext suspendCoroutine { continuation ->
            locationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location ->
                    continuation.resume(
                        Coordinates(location.latitude, location.longitude)
                    )
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }
}
