package com.example.koinmvvmexample.domain.UseCases

import com.example.koinmvvmexample.domain.Entities.UserData
import com.example.koinmvvmexample.domain.Interfaces.UserRepositoryInter

class GetData(private val userRepositoryInter: UserRepositoryInter) {
    fun execute(): UserData = userRepositoryInter.Get()
}