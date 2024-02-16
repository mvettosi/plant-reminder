package io.github.domain.usecase

import io.github.domain.model.Plant
import io.github.domain.repository.PlantRepository
import javax.inject.Inject

class SavePlantUseCase @Inject constructor(private val plantRepository: PlantRepository) {
  suspend operator fun invoke(plant: Plant) = runCatching { plantRepository.savePlant(plant) }
}
