package io.github.domain.usecase

import io.github.domain.model.Plant
import javax.inject.Inject

class DeletePlantUseCase
@Inject constructor(
    private val getPlantUseCase: GetPlantUseCase,
    private val savePlantUseCase: SavePlantUseCase
) {
  suspend operator fun invoke(plantId: String): Result<Plant> = runCatching {
    getPlantUseCase(plantId)
        .getOrThrow()
        .copy(deleted = true)
        .also { savePlantUseCase(it) }
  }
}