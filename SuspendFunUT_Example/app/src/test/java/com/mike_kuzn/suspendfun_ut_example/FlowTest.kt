package com.mike_kuzn.suspendfun_ut_example

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FlowTest {

    @Test
    fun testCollect(): Unit = runTest {
        class FakeRepository: MyRepository{
            override val value = flow {
                emit(69)
            }
        }
        val fakeRepository = FakeRepository()
        val flowClass = TestFlowClass(fakeRepository, Dispatchers.Unconfined)
        flowClass.initialize()
        advanceTimeBy(600)
        //Если job публична и ее можно вытащить из тестируемого кода,
        // то желательно закрыть эту job: .cancelAndJoin()
        assertEquals(69, flowClass.currentValue)
    }

    @Test
    fun testCollect2(): Unit = runTest {
        class FakeRepository: MyRepository{
            override val value = MutableSharedFlow<Int>()
        }
        val fakeRepository = FakeRepository()
        val flowClass = TestFlowClass(fakeRepository, Dispatchers.Unconfined)
        flowClass.initialize()
        advanceTimeBy(600)
        assertEquals(null, flowClass.currentValue)
        fakeRepository.value.emit(5)
        assertEquals(5, flowClass.currentValue)
        fakeRepository.value.emit(69)
        assertEquals(69, flowClass.currentValue)
    }
}
