package io.github.domain.usecase

import io.github.domain.model.Plant
import io.github.domain.util.TimeHelper
import io.github.domain.util.getNewWateringDate
import javax.inject.Inject

class WaterPlantUseCase
@Inject constructor(private val getPlantUseCase: GetPlantUseCase, private val savePlantUseCase: SavePlantUseCase, private val timeHelper: TimeHelper) {
  suspend operator fun invoke(plantId: Int): Result<Plant> = runCatching {
    getPlantUseCase(plantId).getOrThrow().let { plant ->
      plant.copy(
        lastWaterDate = timeHelper.now(),
        nextWaterDate = plant.getNewWateringDate(timeHelper.now())
      )
    }.also { savePlantUseCase(it) }
  }
}