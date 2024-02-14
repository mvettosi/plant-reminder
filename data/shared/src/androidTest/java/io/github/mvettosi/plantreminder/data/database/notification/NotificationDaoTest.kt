package io.github.mvettosi.plantreminder.data.database.notification

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
import java.time.LocalDateTime
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class NotificationDaoTest {
    private lateinit var notificationDao: NotificationDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        notificationDao = db.notificationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testInsertGetAllNotifications() = runTest {
        // Arrange
        val notifications = listOf(
            TestData.notificationEntity.copy(displayedTime = TestData.notificationEntity.displayedTime.plusDays(1)),
            TestData.notificationEntity.copy(displayedTime = TestData.notificationEntity.displayedTime.plusDays(2)),
            TestData.notificationEntity.copy(displayedTime = TestData.notificationEntity.displayedTime.plusDays(3))
        )
        notifications.forEach { notificationDao.insertNotification(it) }

        // Act
        val result = notificationDao.getNotifications()

        // Assert
        assertEquals(expected = 3, actual = result.size)
        assertEquals(expected = notifications[0].displayedTime, actual = result[0].displayedTime)
        assertEquals(expected = notifications[1].displayedTime, actual = result[1].displayedTime)
        assertEquals(expected = notifications[2].displayedTime, actual = result[2].displayedTime)
    }

    private object TestData {
        val notificationEntity = NotificationEntity(
            type = NotificationType.DAILY_REMINDER,
            displayedTime = LocalDateTime.of(2021, 1, 1, 12, 0),
            plantsToWated = 1,
            plantId = 1
        )
    }
}