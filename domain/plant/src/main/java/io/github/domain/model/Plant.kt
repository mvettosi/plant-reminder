package io.github.domain.model

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

data class Plant(
    val id: Long,
    val name: String,
    val size: PlantSize,
    val image: String? = null,
    val description: String? = null,
    val waterAmount: Int,
    val waterFrequency: Set<DayOfWeek>,
    val waterTime: LocalTime,
    val lastWaterDate: LocalDate? = null,
    val nextWaterDate: LocalDate,
)