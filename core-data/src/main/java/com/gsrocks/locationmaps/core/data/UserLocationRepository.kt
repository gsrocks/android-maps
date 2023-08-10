package com.gsrocks.locationmaps.core.data

import com.gsrocks.locationmaps.core.database.UserLocation
import com.gsrocks.locationmaps.core.database.UserLocationDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface UserLocationRepository {
    val userLocations: Flow<List<String>>

    suspend fun add(name: String)
}

class DefaultUserLocationRepository @Inject constructor(
    private val userLocationDao: UserLocationDao
) : UserLocationRepository {

    override val userLocations: Flow<List<String>> =
        userLocationDao.getUserLocations().map { items -> items.map { it.name } }

    override suspend fun add(name: String) {
        userLocationDao.insertUserLocation(UserLocation(name = name))
    }
}
