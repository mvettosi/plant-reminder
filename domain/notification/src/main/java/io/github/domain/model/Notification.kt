package io.github.domain.model

import java.time.LocalDateTime

sealed class Notification(open val id: Int, open val displayedTime: LocalDateTime) {
    data class DailyReminder(
        val plantsToWated: Int,
        override val id: Int = -1,
        override val displayedTime: LocalDateTime = LocalDateTime.now(),
    ) : Notification(id = id, displayedTime = displayedTime)

    data class ForgotToWater(
        val plantId: Int,
        override val id: Int = -1,
        override val displayedTime: LocalDateTime = LocalDateTime.now(),
    ) : Notification(id = id, displayedTime = displayedTime)
}
