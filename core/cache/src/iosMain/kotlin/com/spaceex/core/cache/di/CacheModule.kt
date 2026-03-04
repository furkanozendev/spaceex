package com.spaceex.core.cache.di

import com.spaceex.core.cache.DatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val cacheModule: Module = module {
    single { DatabaseFactory() }
}
