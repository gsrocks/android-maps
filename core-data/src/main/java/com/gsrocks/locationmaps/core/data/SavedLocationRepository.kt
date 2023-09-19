package com.gsrocks.locationmaps.core.data

import com.gsrocks.locationmaps.core.database.daos.SavedLocationDao
import com.gsrocks.locationmaps.core.database.mappers.asEntity
import com.gsrocks.locationmaps.core.database.mappers.asModel
import com.gsrocks.locationmaps.core.model.SavedLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface SavedLocationRepository {
    val savedLocations: Flow<List<SavedLocation>>

    suspend fun add(savedLocation: SavedLocation)
}

class DefaultSavedLocationRepository @Inject constructor(
    private val savedLocationDao: SavedLocationDao
) : SavedLocationRepository {

    override val savedLocations: Flow<List<SavedLocation>> =
        savedLocationDao.getSavedLocationsFlow().map { items -> items.map { it.asModel() } }

    override suspend fun add(savedLocation: SavedLocation) {
        savedLocationDao.insertSavedLocation(savedLocation.asEntity())
    }
}
