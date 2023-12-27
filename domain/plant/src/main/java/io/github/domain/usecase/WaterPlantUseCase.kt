package io.github.domain.usecase

import io.github.domain.model.Plant
import io.github.domain.util.getNewWateringDate
import java.time.LocalDateTime
import javax.inject.Inject

class WaterPlantUseCase
@Inject constructor(private val getPlantUseCase: GetPlantUseCase, private val savePlantUseCase: SavePlantUseCase) {
  suspend operator fun invoke(plantId: String): Result<Plant> = runCatching {
    getPlantUseCase(plantId).getOrThrow().let { plant ->
      plant.copy(
        lastWaterDate = LocalDateTime.now(),
        nextWaterDate = plant.getNewWateringDate()
      )
    }.also { savePlantUseCase(it) }
  }
}