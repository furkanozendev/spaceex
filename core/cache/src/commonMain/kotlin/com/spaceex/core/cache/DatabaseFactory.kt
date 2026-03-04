package com.spaceex.core.cache

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    inline fun <reified T : RoomDatabase> createRoomDatabase(
        name: String,
        crossinline factory: () -> T
    ): RoomDatabase.Builder<T>
}
