package io.github.mvettosi.plantreminder.data.database

import androidx.room.TypeConverter
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

class RoomConverters {
  @TypeConverter
  fun fromTimestamp(value: Long?): LocalDateTime? {
    return value?.let { LocalDateTime.ofEpochSecond(value, 0, ZoneOffset.UTC) }
  }

  @TypeConverter
  fun dateToTimestamp(date: LocalDateTime?): Long? {
    return date?.toEpochSecond(ZoneOffset.UTC)
  }

  @TypeConverter
  fun fromTime(value: String?): LocalTime? {
    return value?.let { LocalTime.parse(value) }
  }

  @TypeConverter
  fun toTime(value: LocalTime?): String? {
    return value?.toString()
  }

  @TypeConverter
  fun fromDayOfWeekSet(value: Set<DayOfWeek>): String {
    return value.joinToString(",") { it.name }
  }

  @TypeConverter
  fun toDayOfWeekSet(value: String): Set<DayOfWeek> {
    return value.split(",").map { DayOfWeek.valueOf(it) }.toSet()
  }
}
