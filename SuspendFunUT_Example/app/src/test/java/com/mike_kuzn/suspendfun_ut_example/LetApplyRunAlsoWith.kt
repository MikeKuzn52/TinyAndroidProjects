package com.mike_kuzn.suspendfun_ut_example

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

//https://avatars.mds.yandex.net/i?id=d6896b72082a390f5132cafd1aaad840_l-5299999-images-thumbs&n=13
class LetApplyRunAlsoWith {

    private val initValue = 5
    private var valInThis = initValue
    data class TestClass(var value: Int, var valInThis: Int = 0)
    private val anyValue = "AnyString for example"
    private lateinit var tc: TestClass

    @Before
    fun init() {
        valInThis = initValue
        tc = TestClass(initValue)
    }

    // it нельзя изменить,
    // более того если в паралельном потоке поменяли то на чем вызвали оператор, it останется
    // (но с дата классом нужено учитывать что останетсяя ссылка на то на чем вызвали оператор,
    // а сами данные в дата классе меняется и тут и в параллельном потоке)
    @Test
    fun changeIt() = runBlocking {
        data class T(var value: Int)
        var endVal = -1
        val t = T(0)
        val scope = CoroutineScope(Dispatchers.Unconfined)
        val job: Deferred<Unit> = t.let{
            scope.async{
                delay(10)
                endVal = it.value
            }
        }
        t.value = 1
        job.await()
        assertEquals(1, endVal)
    }

    @Test
    fun let() {
        // #1 let не подменяет this
        // #2 let возвращает результат посленего операнда (без return)
        // #3 let имеет it, как объект на котором вызвали оператор
        tc.let{ this.valInThis++;}// #1. Т.е. меняется не tc.valInThis
        val r2 = tc.let{ anyValue } // #2
        tc.let{ ++it.value } // #3
        assertEquals(valInThis, initValue+1) // #1
        assertEquals(r2, anyValue) // #2
        assertEquals(tc.value, initValue+1) // #3
    }

    @Test
    fun apply() {
        // #1 apply подменяет this
        // #2 apply возвращает объект на ктором вызвали оператор
        // #3 apply не имеет it
        tc.apply{ this.valInThis++;}// #1. Т.е. меняется не tc.valInThis
        val r2 = tc.apply{ anyValue } // #2
        // Error tc.apply{ ++it.value } // #3
        assertEquals(valInThis, initValue) // #1
        assertEquals(r2, tc) // #2
    }

    @Test
    fun run() {
        // #1 run подменяет this
        // #2 run возвращает результат посленего операнда (без return)
        // #3 run имеет it, как объект на котором вызвали оператор
        tc.run{ this.valInThis++;}// #1. Т.е. меняется не tc.valInThis
        val r2 = tc.run{ anyValue } // #2
        // Error tc.run{ ++it.value } // #3
        assertEquals(valInThis, initValue) // #1
        assertEquals(r2, anyValue) // #2
    }

    @Test
    fun also() {
        // #1 also не подменяет this
        // #2 also возвращает объект на ктором вызвали оператор
        // #3 also имеет it, как объект на котором вызвали оператор
        tc.also{ this.valInThis++;}// #1. Т.е. меняется не tc.valInThis
        val r2 = tc.also{ anyValue } // #2
        tc.also{ ++it.value } // #3
        assertEquals(valInThis, initValue+1) // #1
        assertEquals(r2, tc) // #2
        assertEquals(tc.value, initValue+1) // #3
    }

    @Test
    fun with() {
        // #1 with подменяет this
        // #2 with возвращает результат посленего операнда (без return)
        // #3 with имеет it, как объект на котором вызвали оператор
        with(tc){ this.valInThis++;}// #1. Т.е. меняется не tc.valInThis
        val r2 = with(tc){ anyValue } // #2
        // Error tc.with{ ++it.value } // #3
        assertEquals(valInThis, initValue) // #1
        assertEquals(r2, anyValue) // #2
    }

    @Test
    fun swap() {
        var p1 = 3
        var p2 = 5
        p2 = p1.apply { p1 = p2 }
        assertEquals(p1, 5)
        assertEquals(p2, 3)
    }

    // Отличие run и apply: apply возвращает "this", а run последний оператор без return
    // Отличие let и also: also возвращает "this", а let последний оператор без return
    // Отличие with и run: никакой, только в синтаксисе
}