package com.example.koinmvvmexample.data.Repository

import com.example.koinmvvmexample.data.Storage.Models.StorageModel
import com.example.koinmvvmexample.data.Storage.SharedPreferences.UserStorageLocalSave
import com.example.koinmvvmexample.domain.Entities.UserData
import com.example.koinmvvmexample.domain.Interfaces.UserRepositoryInter

class UserRepository(private val userStorage: UserStorageLocalSave): UserRepositoryInter {

    private fun toStorage(data: UserData) = StorageModel(data.text)
    private fun toUserData(data: StorageModel) = UserData(data.text)

    override fun Save(data: UserData): Boolean = userStorage.Save(toStorage(data))

    override fun Get(): UserData = toUserData(userStorage.Get())
}