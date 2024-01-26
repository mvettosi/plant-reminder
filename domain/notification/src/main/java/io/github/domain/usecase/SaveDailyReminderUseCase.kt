package io.github.domain.usecase

import io.github.domain.model.Notification
import io.github.domain.repository.NotificationRepository
import javax.inject.Inject

class SaveDailyReminderUseCase
@Inject constructor(private val notificationRepository: NotificationRepository) {
  suspend operator fun invoke(plantsToWater: Int) = runCatching {
     notificationRepository.saveNotification(Notification.DailyReminder(plantsToWated = plantsToWater))
  }
}