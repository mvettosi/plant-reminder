package io.github.domain.model

import java.time.LocalDateTime
import java.util.UUID

sealed class Notification(open val id: String, open val displayedTime: LocalDateTime) {
    data class DailyReminder(
        val plantsToWated: Int,
        override val id: String = UUID.randomUUID().toString(),
        override val displayedTime: LocalDateTime = LocalDateTime.now(),
    ) : Notification(id = id, displayedTime = displayedTime)

    data class ForgotToWater(
        val plantId: String,
        override val id: String = UUID.randomUUID().toString(),
        override val displayedTime: LocalDateTime = LocalDateTime.now(),
    ) : Notification(id = id, displayedTime = displayedTime)
}
