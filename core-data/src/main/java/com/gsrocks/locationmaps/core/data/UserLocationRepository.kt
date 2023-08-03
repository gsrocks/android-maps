/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
