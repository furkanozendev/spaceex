package com.spaceex.core.cache

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    val context: Context
) {
    actual inline fun <reified T : RoomDatabase> createRoomDatabase(
        name: String,
        crossinline factory: () -> T
    ): RoomDatabase.Builder<T> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(name)
        return Room.databaseBuilder<T>(
            context = appContext,
            name = dbFile.absolutePath,
            factory = { factory() }
        )
    }
}
