package com.gsrocks.locationmaps.core.data

import com.gsrocks.locationmaps.core.data.errors.MlsError
import com.gsrocks.locationmaps.core.geocoding.GeocodingDataSource
import com.gsrocks.locationmaps.core.geocoding.LocationDataSource
import com.gsrocks.locationmaps.core.model.Coordinates
import com.gsrocks.locationmaps.core.model.LocationAddress
import javax.inject.Inject

class GeocodingError : MlsError()
class LocationError : MlsError()

interface GeocodingRepository {
    suspend fun getAddressByName(name: String): Result<List<LocationAddress>>

    suspend fun getAddressByCoordinates(
        latitude: Double,
        longitude: Double
    ): Result<List<LocationAddress>>

    suspend fun getCurrentLocation(): Result<Coordinates>
}

class DefaultGeocodingRepository @Inject constructor(
    private val geocodingDataSource: GeocodingDataSource,
    private val locationDataSource: LocationDataSource
) : GeocodingRepository {
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
}