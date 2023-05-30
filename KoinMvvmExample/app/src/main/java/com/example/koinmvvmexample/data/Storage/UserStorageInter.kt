package com.example.koinmvvmexample.data.Storage

import com.example.koinmvvmexample.data.Storage.Models.StorageModel

interface UserStorageInter {
    fun Save(data: StorageModel): Boolean

    fun Get(): StorageModel
}