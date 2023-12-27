package io.github.domain.util

import io.github.domain.model.Plant
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters

fun Plant.getNewWateringDate(): LocalDateTime =
    waterFrequency.minOf {
        lastWaterDate
            .toLocalDate()
            .with(TemporalAdjusters.next(it))
            .atTime(waterTime)
    }