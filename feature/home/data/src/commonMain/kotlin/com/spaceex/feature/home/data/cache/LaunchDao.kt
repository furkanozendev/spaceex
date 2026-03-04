package com.spaceex.feature.home.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spaceex.feature.home.data.cache.entity.LaunchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LaunchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunches(launches: List<LaunchEntity>)

    @Query("SELECT * FROM launches")
    suspend fun getAllLaunches(): List<LaunchEntity>

    @Query("SELECT * FROM launches WHERE id = :id")
    fun getLaunchById(id: String): Flow<LaunchEntity?>
}
