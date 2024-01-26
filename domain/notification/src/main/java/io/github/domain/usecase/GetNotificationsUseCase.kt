package io.github.domain.usecase

import io.github.domain.repository.NotificationRepository
import javax.inject.Inject

class GetNotificationsUseCase
@Inject constructor(private val notificationRepository: NotificationRepository) {
  suspend operator fun invoke() = runCatching {
     notificationRepository.getNotifications()
  }
}