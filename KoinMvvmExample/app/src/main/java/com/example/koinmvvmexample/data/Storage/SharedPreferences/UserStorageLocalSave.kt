package com.example.koinmvvmexample.data.Storage.SharedPreferences

import android.content.SharedPreferences
import com.example.koinmvvmexample.data.Storage.Models.StorageModel
import com.example.koinmvvmexample.data.Storage.UserStorageInter

class UserStorageLocalSave(private val sharedPreferences: SharedPreferences): UserStorageInter {
    private val DATA_TEXT_NAME = "DATA_TEXT"
    private val DATA_TEXT_DEF = "NO DATA"

    override fun Save(data: StorageModel): Boolean {
        if (Get().equals(data)) {
            return false
        }
        sharedPreferences
            .edit()
            .putString(DATA_TEXT_NAME, data.text)
            .apply()
        return true
    }

    override fun Get(): StorageModel {
        return StorageModel(sharedPreferences.getString(DATA_TEXT_NAME, DATA_TEXT_DEF)?: DATA_TEXT_DEF)
    }
}