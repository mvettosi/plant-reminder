package io.github.domain.usecase

import io.github.domain.model.Plant
import io.github.domain.model.PlantSize
import io.github.domain.util.TimeHelper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@kotlinx.coroutines.ExperimentalCoroutinesApi
class WaterPlantUseCaseTest {
    @RelaxedMockK lateinit var getPlantUseCase: GetPlantUseCase
    @RelaxedMockK lateinit var savePlantUseCase: SavePlantUseCase
    @RelaxedMockK lateinit var timeHelper: TimeHelper
    private lateinit var underTest: WaterPlantUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        underTest = WaterPlantUseCase(getPlantUseCase, savePlantUseCase, timeHelper)
    }

    @Test
    fun testWateringNewPlantSameWeek() = runTest {
        // Arrange
        // Thursday, December 28, 2023 8:00:00 AM
        val now: LocalDateTime = LocalDateTime.of(2023, Month.DECEMBER, 25, 16, 0)
        every { timeHelper.now() } returns now
        val plant = TestData.basePlant.copy(
            waterFrequency = setOf(DayOfWeek.WEDNESDAY),
            waterTime = LocalTime.of(8, 0),
            lastWaterDate = LocalDateTime.MIN,
            nextWaterDate = now,
        )
        coEvery { getPlantUseCase(plant.id) } returns Result.success(plant)

        // Act
        val result = underTest(plant.id)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(expected = now, actual = result.getOrThrow().lastWaterDate)
        assertEquals(expected = LocalDateTime.of(2023, Month.DECEMBER, 27, 8, 0), actual = result.getOrThrow().nextWaterDate)
    }

    @Test
    fun testWateringNewPlantNextWeek() = runTest {
        // Arrange
        // Thursday, December 28, 2023 8:00:00 AM
        val now: LocalDateTime = LocalDateTime.of(2023, Month.DECEMBER, 28, 6, 0)
        every { timeHelper.now() } returns now
        val plant = TestData.basePlant.copy(
            waterFrequency = setOf(DayOfWeek.MONDAY, DayOfWeek.THURSDAY),
            waterTime = LocalTime.of(8, 0),
            lastWaterDate = LocalDateTime.MIN,
            nextWaterDate = now,
        )
        coEvery { getPlantUseCase(plant.id) } returns Result.success(plant)

        // Act
        val result = underTest(plant.id)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(expected = now, actual = result.getOrThrow().lastWaterDate)
        assertEquals(expected = LocalDateTime.of(2024, Month.JANUARY, 1, 8, 0), actual = result.getOrThrow().nextWaterDate)
    }

    @Test
    fun testWateringOldPlant() = runTest {
        // Arrange
        // Friday, January 12, 2024 6:00:00 AM
        val now: LocalDateTime = LocalDateTime.of(2024, Month.JANUARY, 12, 6, 0)
        every { timeHelper.now() } returns now
        val plant = TestData.basePlant.copy(
            waterFrequency = setOf(DayOfWeek.MONDAY, DayOfWeek.THURSDAY),
            waterTime = LocalTime.of(8, 0),
            lastWaterDate = LocalDateTime.of(2024, Month.JANUARY, 8, 8, 0),
            nextWaterDate = LocalDateTime.of(2024, Month.JANUARY, 11, 8, 0),
        )
        coEvery { getPlantUseCase(plant.id) } returns Result.success(plant)

        // Act
        val result = underTest(plant.id)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(expected = now, actual = result.getOrThrow().lastWaterDate)
        assertEquals(expected = LocalDateTime.of(2024, Month.JANUARY, 15, 8, 0), actual = result.getOrThrow().nextWaterDate)
    }

    private object TestData {
        val basePlant = Plant(
            name = "name",
            size = PlantSize.MEDIUM,
            image = null,
            description = "description",
            waterAmount = 200,
            waterFrequency = emptySet(),
            waterTime = LocalTime.MIDNIGHT,
            lastWaterDate = LocalDateTime.MIN,
            nextWaterDate = LocalDateTime.MIN,
            deleted = false,
        )
    }
}