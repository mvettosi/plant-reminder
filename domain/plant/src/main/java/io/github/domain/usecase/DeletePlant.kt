package io.github.domain.usecase

import io.github.domain.model.Plant
import javax.inject.Inject

class DeletePlant
@Inject constructor(
    private val getPlantUseCase: GetPlantUseCase,
    private val savePlantUseCase: SavePlantUseCase
) {
  suspend operator fun invoke(plantId: String): Result<Plant> = runCatching {
    getPlantUseCase(plantId)
        .getOrThrow()
        .copy(trashed = true)
        .also { savePlantUseCase(it) }
  }
}