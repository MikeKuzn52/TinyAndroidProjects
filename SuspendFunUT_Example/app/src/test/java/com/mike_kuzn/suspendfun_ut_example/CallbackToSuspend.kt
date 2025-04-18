package com.mike_kuzn.suspendfun_ut_example

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Test
import kotlin.coroutines.resume

interface FakeServiceConnection{
    fun onServiceConnected()//className: ComponentName, service: IBinder)
    fun onServiceDisconnected()//className: ComponentName)
    fun onBindingDied()//name: ComponentName?)
}

class CallbackToSuspend {

    private lateinit var connection: FakeServiceConnection

    private fun fakeStartService(time: Long) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            println("Запускаем appContext.bindService(...)")
            delay(time)
            println("Конект прошёл, запускаем onServiceConnected")
            connection.onServiceConnected()
        }
    }

    @Test
    fun waitCallback(): Unit = runBlocking {
        fakeStartService(100)
        println("Ждем пока произойдет onServiceConnected (или что то ещё)")
        val result = withTimeoutOrNull(1000) {
            suspendCancellableCoroutine { continuation ->
                connection = object : FakeServiceConnection {
                    override fun onServiceConnected() {
                        println("onServiceConnected произошёл, заканчиваем ждать")
                        continuation.resume(true)
                    }

                    override fun onServiceDisconnected() {
                        continuation.resume(false)
                    }

                    override fun onBindingDied() {
                        continuation.resume(false)
                    }

                }
            }
        }
        println("Вышли из ожиданий")
        assert(result == true)
    }

    @Test
    fun waitCallbackTimeOut(): Unit = runBlocking {
        fakeStartService(1000)
        println("Ждем пока произойдет onServiceConnected (или что то ещё)")
        val result = withTimeoutOrNull(100) {
            suspendCancellableCoroutine { continuation ->
                connection = object : FakeServiceConnection {
                    override fun onServiceConnected() {
                        println("onServiceConnected произошёл, заканчиваем ждать")
                        continuation.resume(true)
                    }

                    override fun onServiceDisconnected() {
                        continuation.resume(false)
                    }

                    override fun onBindingDied() {
                        continuation.resume(false)
                    }

                }
            }
        }
        println("Вышли из ожиданий")
        assert(result == null)
    }
}