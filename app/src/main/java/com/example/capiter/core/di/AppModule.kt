package com.example.capiter.core.di

import com.example.capiter.data.module.DataModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(includes = [DataModule::class])
class AppModule