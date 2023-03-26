package com.mikekuzn.examle.duggetrtest

import android.app.Application
import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides


class MainApp: Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}
val Context.appComponent:AppComponent
    get() = when(this) {
        is MainApp -> appComponent
        else -> this.applicationContext.appComponent
    }


@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: MainActivity)
    val classA: testClassA
}


@Module
object AppModule {
    /*@Provides
    fun frovideD(): testClassD {
        return testClassD(5)
    }// */


    @Provides
    fun frovideE(): testClassE = testClassE()

}