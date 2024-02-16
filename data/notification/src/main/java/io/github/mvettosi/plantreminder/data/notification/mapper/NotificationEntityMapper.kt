package io.github.mvettosi.plantreminder.data.notification.mapper

import io.github.domain.model.Notification
import io.github.mvettosi.plantreminder.data.database.notification.NotificationEntity
import io.github.mvettosi.plantreminder.data.database.notification.NotificationType
import javax.inject.Inject

class NotificationEntityMapper @Inject constructor() {
  fun mapToEntity(input: Notification) =
      NotificationEntity(
              type =
                  when (input) {
                    is Notification.DailyReminder -> NotificationType.DAILY_REMINDER
                    is Notification.ForgotToWater -> NotificationType.FORGOT_TO_WATER
                  },
              displayedTime = input.displayedTime,
              plantsToWated =
                  if (input is Notification.DailyReminder) input.plantsToWated else null,
              plantId = if (input is Notification.ForgotToWater) input.plantId else null,
          )
          .apply { uid = input.id }

  fun mapToDomain(input: NotificationEntity) =
      when (input.type) {
        NotificationType.DAILY_REMINDER ->
            Notification.DailyReminder(
                id = input.uid,
                displayedTime = input.displayedTime,
                plantsToWated = input.plantsToWated ?: 0)
        NotificationType.FORGOT_TO_WATER ->
            Notification.ForgotToWater(
                id = input.uid, displayedTime = input.displayedTime, plantId = input.plantId ?: 0)
      }
}
