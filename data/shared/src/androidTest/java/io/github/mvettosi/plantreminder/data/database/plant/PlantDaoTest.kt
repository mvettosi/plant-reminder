package io.github.mvettosi.plantreminder.data.database.plant

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.mvettosi.plantreminder.data.database.AppDatabase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(AndroidJUnit4::class)
class PlantDaoTest {
    private lateinit var plantDao: PlantDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        plantDao = db.plantDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testInsertGetAllPlants() = runTest {
        // Arrange
        val plants = listOf(
            TestData.plantEntity.copy(name = "name1"),
            TestData.plantEntity.copy(name = "name2"),
            TestData.plantEntity.copy(name = "name3")
        )
        plants.forEach { plantDao.insertPlant(it) }

        // Act
        val result = plantDao.getPlants()

        // Assert
        assertEquals(expected = 3, actual = result.size)
        assertEquals(expected = "name1", actual = result[0].name)
        assertEquals(expected = "name2", actual = result[1].name)
        assertEquals(expected = "name3", actual = result[2].name)
    }

    @Test
    @Throws(Exception::class)
    fun testInsertGetPlant() = runTest {
        // Arrange
        val plants = listOf(
            TestData.plantEntity.copy(name = "name1").apply { uid = 1 },
            TestData.plantEntity.copy(name = "name2").apply { uid = 2 },
            TestData.plantEntity.copy(name = "name3").apply { uid = 3 }
        )
        plants.forEach { plantDao.insertPlant(it) }

        // Act
        val result = plantDao.getPlant(plants[1].uid)

        // Assert
        assertNotNull(result)
        assertEquals(expected = "name2", actual = result.name)
    }

    @Test
    @Throws(Exception::class)
    fun testDeleteAll() = runTest {
        // Arrange
        val plants = listOf(
            TestData.plantEntity.copy(name = "name1"),
            TestData.plantEntity.copy(name = "name2"),
            TestData.plantEntity.copy(name = "name3")
        )
        plants.forEach { plantDao.insertPlant(it) }

        // Act
        plantDao.deleteUsers(trashed = false)
        val result = plantDao.getPlants()

        // Assert
        assertEquals(expected = 0, actual = result.size)
    }

    private object TestData {
        val plantEntity = PlantEntity(
            name = "name",
            size = "size",
            image = "image",
            description = "description",
            waterAmount = 1,
            waterFrequency = setOf(DayOfWeek.MONDAY),
            waterTime = LocalTime.of(1, 1),
            lastWaterDate = LocalDateTime.MIN,
            nextWaterDate = LocalDateTime.MIN,
            trashed = false
        )
    }
}