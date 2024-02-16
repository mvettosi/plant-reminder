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

package io.github.mvettosi.plantreminder.data.database.plant

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
data class PlantEntity(
    val name: String,
    val size: String,
    val image: String? = null,
    val description: String? = null,
    val waterAmount: Int,
    val waterFrequency: Set<DayOfWeek>,
    val waterTime: LocalTime,
    val lastWaterDate: LocalDateTime = LocalDateTime.MIN,
    val nextWaterDate: LocalDateTime,
    val trashed: Boolean = false
) {
  @PrimaryKey(autoGenerate = true) var uid: Int = 0
}
