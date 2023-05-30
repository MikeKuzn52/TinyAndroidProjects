package com.example.koinmvvmexample.domain.Interfaces

import com.example.koinmvvmexample.domain.Entities.UserData

interface UserRepositoryInter {
    fun Save(data: UserData): Boolean
    fun Get(): UserData
}