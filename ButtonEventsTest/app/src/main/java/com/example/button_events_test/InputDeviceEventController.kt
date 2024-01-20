package com.example.button_events_test

/*
* Т.З. :
* если события DOWN и UP не игнорируются, то должны быть сгенерированы SIMPLE_PRESS и SIMPLE_RELEASE соответственно
* повторный DOWN без UP должен игнорироваться
* UP без DOWN должен игнорироваться
* если после DOWN через менее 600мс пришел UP должен сработать VERY_SHORT_PRESS
* если после DOWN через 600мс пришел UP должен сработать MAINTAINED_PRESS
* (т.е. либо VERY_SHORT_PRESS либо MAINTAINED_PRESS)
* после MAINTAINED_PRESS через каждые 150 мс должен срабатывать REPEAT
* если после UP в течении 600 мс приходит DOWN и UP, то сразу после VERY_SHORT_PRESS должен быть сгененрирован DOUBlE_PRESS
*    т.е. DOWN, UP, DOWN, UP и после последнего UP: VERY_SHORT_PRESS, DOUBlE_PRESS, SIMPLE_RELEASE
* если пришёл DOWN, а UP нет в течении 30сек, должен быть сгененрирован BLOCKED_STATE
* если после DOUBlE_PRESS прихадит новый UP удовледворяющий требованиям DOUBlE_PRESS, то повторный DOUBlE_PRESS не должен генерироваться
*/

import android.util.Log
import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

enum class InEvent { DOWN, UP }
enum class Event {
    SIMPLE_PRESS,
    SIMPLE_RELEASE,
    VERY_SHORT_PRESS,
    MAINTAINED_PRESS,
    REPEATED_PRESS,
    DOUBLE_PRESS,
    BLOCKED_STATE,
}

internal class InputDeviceEventController(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Unconfined
) {

    private companion object TimeConstants {
        var VERY_SHORT_TIME = 600L
        var DOUBLE_CLICK_TIME = 600L
        var REPEAT_TIME = 150L
        var BLOCKED_TIME = 30000 // 30 sec
    }

    data class KeyData(
        var job: Job?,
        var isShortTime: Boolean = true,
        var newPressTimeStamp: Long = 0L,
        var oldPressTimeStamp: Long = 0L,
    )

    private val keyData = mutableMapOf<Int, KeyData>()

    fun onEvent(
        keyCode: Int,
        inEvent: InEvent,
        eventTimeMillis: Long = System.currentTimeMillis(), // For test
    ) {
        when (inEvent) {
            InEvent.DOWN -> {
                if (!keyData.contains(keyCode)) {
                    keyData[keyCode] = KeyData(null)
                }
                keyData[keyCode]!!.apply {
                    if (job != null) {
                        Log.d("***[", "Ignore duplicated KeyCode:$keyCode Down")
                    } else {
                        isShortTime = true
                        job = generateEvents(keyCode, this)
                        job!!.invokeOnCompletion { cause ->
                            job = null
                            if (isShortTime) {
                                emitEvent(keyCode, Event.VERY_SHORT_PRESS)
                                if ((eventTimeMillis - oldPressTimeStamp) < DOUBLE_CLICK_TIME) {
                                    newPressTimeStamp = 0 // To exclude DOUBLE_CLICK duplication
                                    emitEvent(keyCode, Event.DOUBLE_PRESS)
                                }
                            }
                            emitEvent(keyCode, Event.SIMPLE_RELEASE)
                            if (cause !is CancellationException) {
                                Log.e("***[", "Error:$cause")
                            }
                        }
                        oldPressTimeStamp = newPressTimeStamp
                        newPressTimeStamp = eventTimeMillis
                    }
                }
            }

            InEvent.UP -> {
                keyData[keyCode]?.apply {
                    job?.cancel()
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun generateEvents(keyCode: Int, keyData: KeyData): Job =
    // Using the GlobalScope is necessary because
        //  this code will be used in a service that will work "forever"
        GlobalScope.launch(dispatcher) {
            emitEvent(keyCode, Event.SIMPLE_PRESS)
            delay(VERY_SHORT_TIME)
            withContext(NonCancellable) {
                keyData.isShortTime = false
                emitEvent(keyCode, Event.MAINTAINED_PRESS)
            }
            repeat(((BLOCKED_TIME - VERY_SHORT_TIME) / REPEAT_TIME).toInt()) {
                delay(REPEAT_TIME)
                emitEvent(keyCode, Event.REPEATED_PRESS)
            }
            emitEvent(keyCode, Event.BLOCKED_STATE)
            Log.v("***[", "BLOCKED_STATE for KeyCode $keyCode")
            delay(Long.MAX_VALUE)
        }

    var eventListener: ((keyCode: Int, event: Event) -> Unit)? = null

    private fun emitEvent(keyCode: Int, event: Event) {
        eventListener?.let { it(keyCode, event) }
    }

    @VisibleForTesting
    fun setTestTimes(divider: Int) {
        VERY_SHORT_TIME = 600L / divider
        DOUBLE_CLICK_TIME = 600L / divider
        REPEAT_TIME = 150L / divider
        BLOCKED_TIME = 30000 / 10 / divider
    }
}
