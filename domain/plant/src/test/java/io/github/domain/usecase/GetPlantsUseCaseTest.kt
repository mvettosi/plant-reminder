package io.github.domain.usecase

import io.github.domain.model.Plant
import io.github.domain.model.PlantFilter
import io.github.domain.model.PlantSize
import io.github.domain.repository.PlantRepository
import io.github.domain.util.TimeHelper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import java.time.LocalDateTime
import java.time.LocalTime

import kotlin.test.*

@kotlinx.coroutines.ExperimentalCoroutinesApi
class GetPlantsUseCaseTest {
    @RelaxedMockK lateinit var plantRepository: PlantRepository
    @RelaxedMockK lateinit var timeHelper: TimeHelper
    private lateinit var underTest: GetPlantsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { timeHelper.now() } returns TestData.now
        underTest = GetPlantsUseCase(plantRepository, timeHelper)
    }

    @Test
    fun testGetUpcomingPlants() = runTest {
        // Arrange
        val upcomingPlants = listOf(
            TestData.basePlant.copy(id = "1", nextWaterDate = TestData.now.minusDays(1)),
            TestData.basePlant.copy(id = "2", nextWaterDate = TestData.now.plusDays(3)),
            TestData.basePlant.copy(id = "3", nextWaterDate = TestData.now.plusDays(2)),
            TestData.basePlant.copy(id = "4", nextWaterDate = TestData.now.plusDays(1), deleted = true),
        )
        coEvery { plantRepository.getPlants() } returns upcomingPlants

        // Act
        val result = underTest(PlantFilter.UPCOMING)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrThrow().size)
        assertEquals("3", result.getOrThrow()[0].id)
        assertEquals("2", result.getOrThrow()[1].id)
    }

    @Test
    fun testGetForgotToWaterPlants() = runTest {
        // Arrange
        val forgotToWaterPlants = listOf(
            TestData.basePlant.copy(id = "1", nextWaterDate = TestData.now.minusDays(1)),
            TestData.basePlant.copy(id = "2", nextWaterDate = TestData.now.minusDays(2)),
            TestData.basePlant.copy(id = "3", nextWaterDate = TestData.now.plusDays(3)),
            TestData.basePlant.copy(id = "4", nextWaterDate = TestData.now.plusDays(2), deleted = true),
        )
        coEvery { plantRepository.getPlants() } returns forgotToWaterPlants

        // Act
        val result = underTest(PlantFilter.FORGOT_TO_WATER)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrThrow().size)
        assertEquals("2", result.getOrThrow()[0].id)
        assertEquals("1", result.getOrThrow()[1].id)
    }

    @Test
    fun testGetHistoryPlants() = runTest {
        // Arrange
        val historyPlants = listOf(
            TestData.basePlant.copy(id = "1", lastWaterDate = TestData.now.minusDays(1), nextWaterDate = TestData.now.minusDays(3)),
            TestData.basePlant.copy(id = "2", lastWaterDate = TestData.now.minusDays(2), nextWaterDate = TestData.now.minusDays(1)),
            TestData.basePlant.copy(id = "3", lastWaterDate = LocalDateTime.MIN, nextWaterDate = TestData.now.plusDays(4)),
            TestData.basePlant.copy(id = "4", lastWaterDate = TestData.now.minusDays(2), nextWaterDate = TestData.now.plusDays(3)),
            TestData.basePlant.copy(id = "5", nextWaterDate = TestData.now.plusDays(1), deleted = true),
        )
        coEvery { plantRepository.getPlants() } returns historyPlants

        // Act
        val result = underTest(PlantFilter.HISTORY)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(3, result.getOrThrow().size)
        assertEquals("2", result.getOrThrow()[0].id)
        assertEquals("4", result.getOrThrow()[1].id)
        assertEquals("1", result.getOrThrow()[2].id)
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

        val now: LocalDateTime = LocalDateTime.now()
    }
}