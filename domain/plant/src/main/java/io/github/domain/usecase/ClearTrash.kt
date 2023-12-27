package io.github.domain.usecase

import io.github.domain.repository.PlantRepository
import javax.inject.Inject

class ClearTrash
@Inject constructor(private val plantRepository: PlantRepository) {
  suspend operator fun invoke() = runCatching { plantRepository.deleteAll(trashed = true) }
}