package com.mikekuzn.workertest.di

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.startup.AppInitializer
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i("TestWorker", "Application onCreate")
        AppInitializer.getInstance(this)
            .initializeComponent(CustomWorkManagerInitializer::class.java)
    }

    /*@Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    */
}

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Singleton
    @Provides @Named("Name of my any param")
    fun provideAnything(): String {
        return "Anything №${(1..1000).random()}"
    }

   /* @Singleton
    @Provides
    fun provideHiltWorkerFactory(appContext: Context): HiltWorkerFactory {
        val workManagerEntryPoint = EntryPointAccessors.fromApplication(
            appContext,
            WorkManagerInitializerEntryPoint::class.java
        )
        return TODO
    }*/
}

