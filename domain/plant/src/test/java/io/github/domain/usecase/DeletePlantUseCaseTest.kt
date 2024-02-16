package io.github.domain.usecase

import io.github.domain.model.Plant
import io.github.domain.model.PlantSize
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.test.*
import kotlinx.coroutines.test.runTest
import org.junit.Before

@kotlinx.coroutines.ExperimentalCoroutinesApi
class DeletePlantUseCaseTest {
  @RelaxedMockK lateinit var getPlantUseCase: GetPlantUseCase
  @RelaxedMockK lateinit var savePlantUseCase: SavePlantUseCase
  private lateinit var underTest: DeletePlantUseCase

  @Before
  fun setUp() {
    MockKAnnotations.init(this)
    underTest = DeletePlantUseCase(getPlantUseCase, savePlantUseCase)
  }

  @Test
  fun testDeletion() = runTest {
    // Arrange
    val plant =
        Plant(
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
    coEvery { getPlantUseCase(plant.id) } returns Result.success(plant)

    // Act
    val result = underTest(plant.id)

    // Assert
    assertTrue(result.isSuccess)
    assertTrue(result.getOrThrow().deleted)
  }
}
