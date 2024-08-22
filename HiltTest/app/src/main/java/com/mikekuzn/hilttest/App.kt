package com.mikekuzn.hilttest

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@HiltAndroidApp
class App: Application()

@Module
@InstallIn(SingletonComponent::class)
class DiModule {

    @Singleton
    @Provides
    fun providesTestClass1() = TestClass1()
}