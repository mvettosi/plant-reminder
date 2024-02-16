package io.github.domain.model

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

data class Plant(
    val id: Int = -1,
    val name: String,
    val size: PlantSize,
    val image: String? = null,
    val description: String? = null,
    val waterAmount: Int,
    val waterFrequency: Set<DayOfWeek>,
    val waterTime: LocalTime,
    val lastWaterDate: LocalDateTime = LocalDateTime.MIN,
    val nextWaterDate: LocalDateTime,
    val deleted: Boolean = false
)
