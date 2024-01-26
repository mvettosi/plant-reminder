package io.github.domain.repository

import io.github.domain.model.Notification

interface NotificationRepository {
    suspend fun saveNotification(notification: Notification)
    suspend fun getNotifications(): List<Notification>
}