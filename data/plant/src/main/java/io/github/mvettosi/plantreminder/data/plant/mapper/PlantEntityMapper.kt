package io.github.mvettosi.plantreminder.data.plant.mapper

import io.github.domain.model.Plant
import io.github.domain.model.PlantSize
import io.github.mvettosi.plantreminder.data.database.plant.PlantEntity
import javax.inject.Inject

class PlantEntityMapper @Inject constructor() {
    fun mapToEntity(input: Plant) = PlantEntity(
        name = input.name,
        size = input.size.name,
        image = input.image,
        description = input.description,
        waterAmount = input.waterAmount,
        waterFrequency = input.waterFrequency,
        waterTime = input.waterTime,
        lastWaterDate = input.lastWaterDate,
        nextWaterDate = input.nextWaterDate,
        trashed = input.deleted
    ).apply {
        uid = input.id
    }

    fun mapToDomain(input: PlantEntity) = Plant(
        id = input.uid,
        name = input.name,
        size = PlantSize.valueOf(input.size),
        image = input.image,
        description = input.description,
        waterAmount = input.waterAmount,
        waterFrequency = input.waterFrequency,
        waterTime = input.waterTime,
        lastWaterDate = input.lastWaterDate,
        nextWaterDate = input.nextWaterDate,
        deleted = input.trashed
    )
}