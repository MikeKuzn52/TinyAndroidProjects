package com.example.koinmvvmexample.di

import android.app.Application
import android.content.Context
import com.example.koinmvvmexample.data.Repository.UserRepository
import com.example.koinmvvmexample.data.Storage.SharedPreferences.UserStorageLocalSave
import com.example.koinmvvmexample.domain.Interfaces.UserRepositoryInter
import com.example.koinmvvmexample.domain.UseCases.GetData
import com.example.koinmvvmexample.domain.UseCases.MyCounter
import com.example.koinmvvmexample.domain.UseCases.SaveData
import com.example.koinmvvmexample.presentation.models.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val diModule = module {
    // ViewModel DI
    viewModel<MainViewModel> {
        MainViewModel(
            getData = get(),
            saveData = get(),
            counter = get()
        )
    }

    // Data DI
    val SHARED_PREFERENCE_NAME = "shName"

    single {
        val context:Application = get()
        context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    single { UserStorageLocalSave(sharedPreferences = get()) }
    single<UserRepositoryInter> { UserRepository(userStorage = get()) }

    // Domain DI
    factory { GetData(userRepositoryInter = get()) }
    factory { SaveData(userRepositoryInter = get()) }
    factory { MyCounter(application = get(), 5000) }

}