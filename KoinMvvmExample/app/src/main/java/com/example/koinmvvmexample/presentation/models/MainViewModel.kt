package com.example.koinmvvmexample.presentation.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.koinmvvmexample.domain.Entities.UserData
import com.example.koinmvvmexample.domain.UseCases.GetData
import com.example.koinmvvmexample.domain.UseCases.MyCounter
import com.example.koinmvvmexample.domain.UseCases.SaveData

class MainViewModel(
    private val getData: GetData,
    private val saveData: SaveData,
    private val counter: MyCounter
): ViewModel() {

    var data = mutableStateOf("No data")
    var dataToSave = mutableStateOf("")

    init {
        counter.execute()
    }

    fun Restart() {
        counter.execute()
    }

    fun getCount() = counter.get()

    fun Get() {
        data.value = getData.execute().text
    }

    fun Set() {
        saveData.execute(UserData(dataToSave.value))
    }
}