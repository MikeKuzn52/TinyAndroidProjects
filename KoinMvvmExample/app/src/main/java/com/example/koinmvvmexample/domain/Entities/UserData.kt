package com.example.koinmvvmexample.domain.Entities

class UserData(val text: String) {
    override fun equals(other: Any?): Boolean {
        return other is UserData && other.text == text
    }
}