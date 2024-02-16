package io.github.domain.usecase

import io.github.domain.model.Plant
import io.github.domain.repository.PlantRepository
import javax.inject.Inject

class GetPlantUseCase @Inject constructor(private val plantRepository: PlantRepository) {
  suspend operator fun invoke(plantId: Int): Result<Plant> = runCatching {
    plantRepository.getPlant(plantId)
  }
}
