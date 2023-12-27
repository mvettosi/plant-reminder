package io.github.domain.repository

import io.github.domain.model.Plant

interface PlantRepository {
    suspend fun getPlants(): List<Plant>
}