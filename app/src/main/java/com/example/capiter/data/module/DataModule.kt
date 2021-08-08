package com.example.capiter.data.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module(includes = [CashModule::class, JsonModule::class, NetworkModule::class, RequestModule::class])
class DataModule {

    @Provides
    @IntoSet
    fun providesJsonConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @IntoSet
    fun providesCompactCallAdapterFactory(): CallAdapter.Factory {
        return RxJava3CallAdapterFactory.createSynchronous()
    }

    @Provides
    @IntoSet
    fun providesAuthenticationInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-apikey", "87c35db9718530498e6f8be2514da8cf5989a")
                    .build()
            )
        }
    }
}