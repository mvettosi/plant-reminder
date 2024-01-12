package io.github.domain.util

import io.github.domain.model.Plant
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

class TimeHelper @Inject constructor() {
    fun now(): LocalDateTime = LocalDateTime.now()
}

fun Plant.getNewWateringDate(now: LocalDateTime): LocalDateTime =
    waterFrequency.minOf {
        now
            .toLocalDate()
            .with(TemporalAdjusters.next(it))
            .atTime(waterTime)
    }