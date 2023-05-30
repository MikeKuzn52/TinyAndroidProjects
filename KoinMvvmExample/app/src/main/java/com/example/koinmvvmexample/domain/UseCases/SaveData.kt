package com.example.koinmvvmexample.domain.UseCases

import com.example.koinmvvmexample.domain.Entities.UserData
import com.example.koinmvvmexample.domain.Interfaces.UserRepositoryInter

class SaveData(private val userRepositoryInter: UserRepositoryInter) {
    fun execute(data: UserData): Boolean = userRepositoryInter.Save(data)
}