package com.gsrocks.locationmaps.test.app.testdi

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import com.gsrocks.locationmaps.core.data.SavedLocationRepository
import com.gsrocks.locationmaps.core.data.di.DataModule
import com.gsrocks.locationmaps.core.data.di.FakeSavedLocationRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
interface FakeDataModule {

    @Binds
    abstract fun bindRepository(
        fakeRepository: FakeSavedLocationRepository
    ): SavedLocationRepository
}
