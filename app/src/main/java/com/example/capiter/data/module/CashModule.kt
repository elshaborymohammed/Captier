package com.example.capiter.data.module

import android.content.Context
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import javax.inject.Singleton

@Module
@DisableInstallInCheck
class CashModule {
    @Provides
    @Singleton
    fun provideOkHttpCache(@ApplicationContext context: Context): Cache {
        val cacheSize = 50 * 1024 * 1024 // 50 MiB
        return Cache(context.cacheDir, cacheSize.toLong())
    }
}