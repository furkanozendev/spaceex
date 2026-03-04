package com.spaceex.core.cache

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSHomeDirectory
import platform.Foundation.NSUserDomainMask

actual class DatabaseFactory {

    @OptIn(ExperimentalForeignApi::class)
    actual inline fun <reified T : RoomDatabase> createRoomDatabase(
        name: String,
        crossinline factory: () -> T
    ): RoomDatabase.Builder<T> {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = true,
            error = null,
        )

        val dirPath = documentDirectory?.path ?: "${NSHomeDirectory()}/Documents"

        val cleanDirPath = dirPath.removeSuffix("/")

        val dbFilePath = "$cleanDirPath/$name"

        return Room.databaseBuilder<T>(
            name = dbFilePath,
            factory = { factory() }
        )
    }
}
