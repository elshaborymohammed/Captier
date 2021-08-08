package com.example.capiter.data.module

import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.function.Consumer
import javax.inject.Singleton

@Module
@DisableInstallInCheck
class RequestModule {

    @Provides
    @Singleton
    fun providesRequestBuilder(
        converterFactories: java.util.Set<Converter.Factory>,
        callAdapterFactories: java.util.Set<CallAdapter.Factory>
    ): Retrofit.Builder {
        val builder = Retrofit.Builder()
            .baseUrl("https://capiter-a7b2.restdb.io/rest/")
        converterFactories.forEach(Consumer { factory: Converter.Factory ->
            builder.addConverterFactory(
                factory
            )
        })
        callAdapterFactories.forEach(Consumer { factory: CallAdapter.Factory ->
            builder.addCallAdapterFactory(
                factory
            )
        })
        return builder
    }

    @Provides
    @Singleton
    fun providesRequest(
        builder: Retrofit.Builder,
        okHttpClientBuilder: OkHttpClient.Builder
    ): Retrofit {
        return builder.client(okHttpClientBuilder.build()).build()
    }
}