package com.gsrocks.locationmaps.core.data.di

import com.gsrocks.locationmaps.core.data.DefaultGeoRepository
import com.gsrocks.locationmaps.core.data.DefaultSavedLocationRepository
import com.gsrocks.locationmaps.core.data.GeoRepository
import com.gsrocks.locationmaps.core.data.SavedLocationRepository
import com.gsrocks.locationmaps.core.model.Coordinates
import com.gsrocks.locationmaps.core.model.SavedLocation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsSavedLocationRepository(
        savedLocationRepository: DefaultSavedLocationRepository
    ): SavedLocationRepository

    @Binds
    fun bindsGeoRepository(
        defaultGeoRepository: DefaultGeoRepository
    ): GeoRepository
}

class FakeSavedLocationRepository @Inject constructor() : SavedLocationRepository {
    override val savedLocations: Flow<List<SavedLocation>> = flowOf(fakeUserLocations)

    override suspend fun add(savedLocation: SavedLocation) {
        throw NotImplementedError()
    }
}

val fakeUserLocations = listOf(
    SavedLocation(
        coordinates = Coordinates(48.442566, 35.052959),
        title = "Home"
    )
)
