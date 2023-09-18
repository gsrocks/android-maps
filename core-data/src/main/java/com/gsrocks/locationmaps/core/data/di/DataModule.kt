package com.gsrocks.locationmaps.core.data.di

import com.gsrocks.locationmaps.core.data.DefaultGeoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.gsrocks.locationmaps.core.data.UserLocationRepository
import com.gsrocks.locationmaps.core.data.DefaultUserLocationRepository
import com.gsrocks.locationmaps.core.data.GeoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsUserLocationRepository(
        userLocationRepository: DefaultUserLocationRepository
    ): UserLocationRepository

    @Binds
    fun bindsGeocodingRepository(
        defaultGeocodingRepository: DefaultGeoRepository
    ): GeoRepository
}

class FakeUserLocationRepository @Inject constructor() : UserLocationRepository {
    override val userLocations: Flow<List<String>> = flowOf(fakeUserLocations)

    override suspend fun add(name: String) {
        throw NotImplementedError()
    }
}

val fakeUserLocations = listOf("One", "Two", "Three")
