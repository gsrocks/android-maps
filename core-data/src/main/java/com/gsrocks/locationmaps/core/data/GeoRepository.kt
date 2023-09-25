package com.gsrocks.locationmaps.core.data

import com.gsrocks.locationmaps.core.data.errors.MlsError
import com.gsrocks.locationmaps.core.geo.DirectionsDataSource
import com.gsrocks.locationmaps.core.geo.GeocodingDataSource
import com.gsrocks.locationmaps.core.geo.LocationDataSource
import com.gsrocks.locationmaps.core.geo.geofencing.GeofencingDataSource
import com.gsrocks.locationmaps.core.model.Coordinates
import com.gsrocks.locationmaps.core.model.DirectionRoute
import com.gsrocks.locationmaps.core.model.LocationAddress
import javax.inject.Inject

class GeocodingError : MlsError()
class LocationError : MlsError()
class DirectionsError : MlsError()
class GeofenceError : MlsError()

interface GeoRepository {
    suspend fun getAddressByName(name: String): Result<List<LocationAddress>>

    suspend fun getAddressByCoordinates(
        latitude: Double,
        longitude: Double
    ): Result<List<LocationAddress>>

    suspend fun getCurrentLocation(): Result<Coordinates>

    suspend fun getDirectionsBetween(start: Coordinates, end: Coordinates): Result<DirectionRoute>

    fun createGeofence(
        requestId: String,
        coordinates: Coordinates,
        radius: Float,
        durationMills: Long
    ): Result<Unit>
}

class DefaultGeoRepository @Inject constructor(
    private val geocodingDataSource: GeocodingDataSource,
    private val locationDataSource: LocationDataSource,
    private val directionsDataSource: DirectionsDataSource,
    private val geofencingDataSource: GeofencingDataSource
) : GeoRepository {
    override suspend fun getAddressByName(name: String): Result<List<LocationAddress>> {
        return try {
            val address = geocodingDataSource.getFromLocationName(name)
            Result.success(address)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure(GeocodingError())
        }
    }

    override suspend fun getAddressByCoordinates(
        latitude: Double,
        longitude: Double
    ): Result<List<LocationAddress>> {
        return try {
            val address = geocodingDataSource.getFromCoordinates(latitude, longitude)
            Result.success(address)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure(GeocodingError())
        }
    }

    override suspend fun getCurrentLocation(): Result<Coordinates> {
        return try {
            val location = locationDataSource.getCurrentLocation()
            Result.success(location)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure(LocationError())
        }
    }

    override suspend fun getDirectionsBetween(
        start: Coordinates,
        end: Coordinates
    ): Result<DirectionRoute> {
        return try {
            val route = directionsDataSource.getDirectionsBetween(start, end)
            Result.success(route)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure(DirectionsError())
        }
    }

    override fun createGeofence(
        requestId: String,
        coordinates: Coordinates,
        radius: Float,
        durationMills: Long
    ): Result<Unit> {
        return try {
            geofencingDataSource.createGeofence(requestId, coordinates, radius, durationMills)
            Result.success(Unit)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure(GeofenceError())
        }
    }
}
