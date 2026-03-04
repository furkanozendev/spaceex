package com.spaceex.feature.home.data.cache

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.spaceex.feature.home.data.cache.entity.LaunchEntity

@Database(entities = [LaunchEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class LaunchDatabase : RoomDatabase() {
    abstract fun launchDao(): LaunchDao
}

expect object AppDatabaseConstructor : RoomDatabaseConstructor<LaunchDatabase> {
    override fun initialize(): LaunchDatabase
}