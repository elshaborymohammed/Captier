package com.example.capiter.data.module

import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import dagger.multibindings.IntoSet
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@DisableInstallInCheck
class NetworkModule {
    @Provides
    @IntoSet
    fun providesBodyInterceptors(): Interceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        cache: Cache,
        interceptorSet: java.util.Set<Interceptor>
    ): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
            .cache(cache)
            .callTimeout(3L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
        builder.interceptors().addAll(interceptorSet)
        return builder
    }
}