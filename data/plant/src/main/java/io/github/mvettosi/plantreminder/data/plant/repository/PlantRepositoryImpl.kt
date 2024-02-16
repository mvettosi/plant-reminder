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

package io.github.mvettosi.plantreminder.data.plant.repository

import io.github.domain.model.Plant
import io.github.domain.repository.PlantRepository
import io.github.mvettosi.plantreminder.data.database.plant.PlantDao
import io.github.mvettosi.plantreminder.data.plant.mapper.PlantEntityMapper
import javax.inject.Inject

class PlantRepositoryImpl
@Inject
constructor(private val plantDao: PlantDao, private val plantEntityMapper: PlantEntityMapper) :
    PlantRepository {
  override suspend fun getPlants(): List<Plant> =
      plantDao.getPlants().map { plantEntityMapper.mapToDomain(it) }

  override suspend fun getPlant(plantId: Int): Plant =
      plantEntityMapper.mapToDomain(plantDao.getPlant(plantId))

  override suspend fun savePlant(plant: Plant) {
    plantDao.insertPlant(plantEntityMapper.mapToEntity(plant))
  }

  override suspend fun deleteAll(trashed: Boolean) {
    plantDao.deleteUsers(trashed)
  }
}
