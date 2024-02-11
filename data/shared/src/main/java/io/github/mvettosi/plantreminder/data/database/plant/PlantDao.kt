package io.github.mvettosi.plantreminder.data.database.plant

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PlantDao {
    @Query("SELECT * FROM PlantEntity")
    fun getPlants(): List<PlantEntity>
    @Query("SELECT * FROM PlantEntity WHERE uid = :id LIMIT 1")
    fun getPlant(id: Int): PlantEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(item: PlantEntity)

    @Query("DELETE FROM PlantEntity WHERE trashed = :trashed")
    fun deleteUsers(trashed: Boolean)
}