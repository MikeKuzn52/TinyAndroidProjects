package com.example.koinmvvmexample.data.Storage.Models

class StorageModel(val text: String) {
    override fun equals(other: Any?): Boolean {
        return other is StorageModel && other.text == text
    }
}