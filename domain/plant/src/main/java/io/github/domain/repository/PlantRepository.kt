package io.github.domain.repository

import io.github.domain.model.Plant

interface PlantRepository {
    suspend fun getPlants(): List<Plant>
    suspend fun getPlant(plantId: String): Plant
    suspend fun savePlant(plant: Plant)
    suspend fun deleteAll(trashed: Boolean)
}