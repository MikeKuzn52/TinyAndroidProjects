package com.mike_kuzn.suspendfun_ut_example

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class TestObject(
    var par: String
) {
    override fun toString() = par
}

class TestClass(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private val coroutineScope by lazy { CoroutineScope(dispatcher) }

    fun testFun(obj: TestObject) {
        coroutineScope.launch {
            sFun(obj)
        }
    }

    private suspend fun sFun(obj: TestObject) {
        // Пауза время которой значительно больше времени теста.
        // Тест столько ждать не может
        delay(1000000)
        obj.par+="_End"
    }
}