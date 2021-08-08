package com.example.capiter.data.module

import dagger.hilt.migration.DisableInstallInCheck
import dagger.Provides
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.FieldNamingPolicy
import com.example.capiter.data.module.JsonModule
import dagger.Module
import javax.inject.Singleton

@Module
@DisableInstallInCheck
class JsonModule {
    @Provides
    @Singleton
    fun providesJson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//            .excludeFieldsWithoutExposeAnnotation()
            .setDateFormat(providesDatePattern())
            .create()
    }

    companion object {
        fun providesDatePattern(): String {
            return "yyyy-MM-dd'T'HH:mm:ssZ"
        }
    }
}