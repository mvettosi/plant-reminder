package io.github.domain.usecase

import io.github.domain.model.Notification
import io.github.domain.repository.NotificationRepository
import javax.inject.Inject

class SaveForgotToWaterUseCase
@Inject constructor(private val notificationRepository: NotificationRepository) {
  suspend operator fun invoke(plantId: Int) = runCatching {
     notificationRepository.saveNotification(Notification.ForgotToWater(plantId = plantId))
  }
}