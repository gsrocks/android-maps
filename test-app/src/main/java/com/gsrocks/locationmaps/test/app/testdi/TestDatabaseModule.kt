package com.gsrocks.locationmaps.test.app.testdi

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import com.gsrocks.locationmaps.core.data.UserLocationRepository
import com.gsrocks.locationmaps.core.data.di.DataModule
import com.gsrocks.locationmaps.core.data.di.FakeUserLocationRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
interface FakeDataModule {

    @Binds
    abstract fun bindRepository(
        fakeRepository: FakeUserLocationRepository
    ): UserLocationRepository
}
