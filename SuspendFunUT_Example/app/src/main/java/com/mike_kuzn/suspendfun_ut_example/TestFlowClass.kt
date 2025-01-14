package com.mike_kuzn.suspendfun_ut_example

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface MyRepository{
    val value: Flow<Int>
}

class TestFlowClass(
    private val myRepository: MyRepository,
    private val dispatcher: CoroutineDispatcher,
) {
    var currentValue: Int? = null

    fun initialize() {
        CoroutineScope(dispatcher).launch {
            myRepository.value.collect{ value ->
                currentValue = value
            }
        }
    }
}