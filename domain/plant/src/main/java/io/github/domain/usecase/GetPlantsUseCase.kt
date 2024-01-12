package io.github.domain.usecase

import io.github.domain.model.Plant
import io.github.domain.model.PlantFilter
import io.github.domain.model.PlantFilter.FORGOT_TO_WATER
import io.github.domain.model.PlantFilter.HISTORY
import io.github.domain.model.PlantFilter.UPCOMING
import io.github.domain.repository.PlantRepository
import io.github.domain.util.TimeHelper
import java.time.LocalDateTime
import javax.inject.Inject

class GetPlantsUseCase
@Inject constructor(private val plantRepository: PlantRepository, private val timeHelper: TimeHelper) {
  suspend operator fun invoke(filter: PlantFilter): Result<List<Plant>> = runCatching {
    plantRepository.getPlants()
      .filter { !it.deleted }
      .let { plants ->
      val now = timeHelper.now()
      when (filter) {
        UPCOMING -> plants
          .filter { it.nextWaterDate.isAfter(now) }
          .sortedBy { it.nextWaterDate }
        FORGOT_TO_WATER -> plants
          .filter { it.nextWaterDate.isBefore(now) }
          .sortedBy { it.nextWaterDate }
        HISTORY -> plants
          .filter { it.lastWaterDate != LocalDateTime.MIN || it.nextWaterDate.isBefore(now) }
          .sortedByDescending {
            if (it.nextWaterDate.isBefore(now)) it.nextWaterDate else it.lastWaterDate
          }
      }
    }
  }
}