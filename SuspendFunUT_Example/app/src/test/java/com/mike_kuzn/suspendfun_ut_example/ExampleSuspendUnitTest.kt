package com.mike_kuzn.suspendfun_ut_example

import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

class ExampleSuspendUnitTest {

    val testString = "TestString"
    val expectedString = "TestString_End"
    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun `Incorrect coroutine test`() = runTest() {
        val testObject = TestObject(testString)
        val testClass = TestClass()
        // Сложность в том что testFun не suspend, а она запускает suspend функцию и как дождаться ее результата
        testClass.testFun(testObject)
        //assertEquals(expectedString, testObject.toString()) - Error т.к. testFun->sFun ещё не отработала
        assertEquals(testString, testObject.toString())
    }

    @Test
    fun `Correct coroutine test`() = runTest(testDispatcher) {
        // Важно что в runTest мы передаем именно тот testDispatcher который передали в тестируемый объект
        // это позволит управлять скоупом в тестируемом объекте, в том числе дожидаться job-ов.
        // runTest в отличии от runBlocking так же позволяет не ждать delay("много"),
        //   т.е. работать с виртуальным временем и даже управлять этим временем
        val testObject = TestObject(testString)
        val testClass = TestClass(testDispatcher)
        testClass.testFun(testObject)
        // Дождаться завершения всех job-ов.
        // Это позволит дождаться выполнения testClass.testFun->sFun и получить результат ее работы
        testDispatcher.scheduler.advanceUntilIdle()
        // через testDispatcher.scheduler так же можно дождаться завершения одной job-ы и другие действия с job-ами
        assertEquals(expectedString, testObject.toString())
    }
}