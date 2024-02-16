/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.mvettosi.plantreminder.data.notification.repository

import io.github.domain.model.Notification
import io.github.domain.repository.NotificationRepository
import io.github.mvettosi.plantreminder.data.database.notification.NotificationDao
import io.github.mvettosi.plantreminder.data.notification.mapper.NotificationEntityMapper
import javax.inject.Inject

class NotificationRepositoryImpl
@Inject
constructor(
    private val plantDao: NotificationDao,
    private val plantEntityMapper: NotificationEntityMapper
) : NotificationRepository {
  override suspend fun getNotifications(): List<Notification> =
      plantDao.getNotifications().map { plantEntityMapper.mapToDomain(it) }

  override suspend fun saveNotification(notification: Notification) {
    plantDao.insertNotification(plantEntityMapper.mapToEntity(notification))
  }
}
