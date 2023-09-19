package com.gsrocks.locationmaps.data

import com.gsrocks.locationmaps.core.data.DefaultSavedLocationRepository
import com.gsrocks.locationmaps.core.database.daos.SavedLocationDao
import com.gsrocks.locationmaps.core.database.entities.SavedLocationEntity
import com.gsrocks.locationmaps.core.model.Coordinates
import com.gsrocks.locationmaps.core.model.SavedLocation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for [DefaultSavedLocationRepository].
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class DefaultSavedLocationRepositoryTest {

    @Test
    fun userLocations_newItemSaved_itemIsReturned() = runTest {
        val repository = DefaultSavedLocationRepository(FakeUserLocationDao())

        repository.add(
            SavedLocation(
                coordinates = Coordinates(48.442566, 35.052959),
                title = "Home"
            )
        )

        assertEquals(repository.savedLocations.first().size, 1)
    }

}

private class FakeUserLocationDao : SavedLocationDao {

    private val data = mutableListOf<SavedLocationEntity>()

    override fun getSavedLocationsFlow(): Flow<List<SavedLocationEntity>> = flow {
        emit(data)
    }

    override suspend fun insertSavedLocation(item: SavedLocationEntity) {
        data.add(0, item)
    }
}
