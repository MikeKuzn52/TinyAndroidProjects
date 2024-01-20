package com.example.button_events_test

import android.util.Log
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Test
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before

class InputDeviceEventControllerTest {

    private val timeToWork = 15L

    private val mockedListener: (keyCode: Int, event: Event) -> Unit =
        mockk(relaxed = true)

    private fun getController(divider: Int) = InputDeviceEventController().apply {
        setTestTimes(divider)
        eventListener = mockedListener
    }

    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }

    @Test
    fun `SIMPLE_PRESS VERY_SHORT_PRESS SIMPLE_RELEASE 0 Time`() = runBlocking {
        val controller = getController(1)
        controller.onEvent(1, InEvent.DOWN, System.currentTimeMillis())
        controller.onEvent(1, InEvent.UP, System.currentTimeMillis())
        delay(5)
        verify { mockedListener(any(), Event.SIMPLE_PRESS) }
        verify { mockedListener(any(), Event.VERY_SHORT_PRESS) }
        verify { mockedListener(any(), Event.SIMPLE_RELEASE) }
        verify(exactly = 3) { mockedListener(any(), any()) }
    }

    @Test
    fun `SIMPLE_PRESS VERY_SHORT_PRESS SIMPLE_RELEASE 599 Time`() = runBlocking {
        val controller = getController(1)
        controller.onEvent(1, InEvent.DOWN, 1000)
        delay(599 - timeToWork * 3)
        controller.onEvent(1, InEvent.UP, 1000 + 599)
        delay(5)
        verify { mockedListener(any(), Event.SIMPLE_PRESS) }
        verify { mockedListener(any(), Event.VERY_SHORT_PRESS) }
        verify { mockedListener(any(), Event.SIMPLE_RELEASE) }
        verify(exactly = 3) { mockedListener(any(), any()) }
    }

    @Test
    fun `SIMPLE_PRESS MAINTAINED_PRESS SIMPLE_RELEASE 600 Time`() = runBlocking {
        val controller = getController(1)
        controller.onEvent(1, InEvent.DOWN, 1000)
        delay(600 + timeToWork)
        controller.onEvent(1, InEvent.UP, 1000 + 600)
        delay(5)
        verify { mockedListener(any(), Event.SIMPLE_PRESS) }
        verify { mockedListener(any(), Event.MAINTAINED_PRESS) }
        verify { mockedListener(any(), Event.SIMPLE_RELEASE) }
        verify(exactly = 3) { mockedListener(any(), any()) }
    }

    @Test
    fun `SIMPLE_PRESS MAINTAINED_PRESS SIMPLE_RELEASE 749 Time`() = runBlocking {
        val controller = getController(1)
        controller.onEvent(1, InEvent.DOWN, 1000)
        delay(749 - timeToWork)
        controller.onEvent(1, InEvent.UP, 1000 + 749)
        delay(5)
        verify { mockedListener(any(), Event.SIMPLE_PRESS) }
        verify { mockedListener(any(), Event.MAINTAINED_PRESS) }
        verify { mockedListener(any(), Event.SIMPLE_RELEASE) }
        verify(exactly = 3) { mockedListener(any(), any()) }
    }

    @Test
    fun `REPEATED_PRES 1`() = runBlocking {
        val controller = getController(1)
        controller.onEvent(1, InEvent.DOWN, 1000)
        delay(750 + timeToWork)
        controller.onEvent(1, InEvent.UP, 1000 + 750)
        delay(5)
        verify { mockedListener(any(), Event.MAINTAINED_PRESS) }
        verify(exactly = 1) { mockedListener(any(), Event.REPEATED_PRESS) }
        verify(exactly = 4) { mockedListener(any(), any()) }
    }

    @Test
    fun `REPEATED_PRES 2`() = runBlocking {
        val controller = getController(1)
        controller.onEvent(1, InEvent.DOWN, 1000)
        delay(900 + timeToWork * 2)
        controller.onEvent(1, InEvent.UP, 1000 + 900)
        delay(5)
        verify { mockedListener(any(), Event.MAINTAINED_PRESS) }
        verify(exactly = 2) { mockedListener(any(), Event.REPEATED_PRESS) }
        verify(exactly = 5) { mockedListener(any(), any()) }
    }

    @Test
    fun `REPEATED_PRES 10`() = runBlocking {
        val controller = getController(4)
        controller.onEvent(1, InEvent.DOWN, 1000)
        delay(2100 / 4 + 100)
        controller.onEvent(1, InEvent.UP, 1000 + 2100 / 4)
        delay(5)
        // to show all logs: verify(exactly = 0) {Log.d(any(), any())}
        verify { mockedListener(any(), Event.MAINTAINED_PRESS) }
        verify(exactly = 10) { mockedListener(any(), Event.REPEATED_PRESS) }
        verify(exactly = 13) { mockedListener(any(), any()) }
    }

    @Test
    fun `BLOCKED_STATE and unblocking`() = runBlocking {
        val controller = getController(150)
        controller.onEvent(1, InEvent.DOWN)
        delay(1000)
        verify(exactly = 1) { mockedListener(any(), Event.BLOCKED_STATE) }
        controller.onEvent(1, InEvent.DOWN)
        delay(5)
        controller.onEvent(1, InEvent.DOWN)
        delay(5)
        controller.onEvent(1, InEvent.DOWN)
        delay(5)
        verify(exactly = 1) { mockedListener(any(), Event.BLOCKED_STATE) }
        verify(exactly = 1) { mockedListener(any(), Event.SIMPLE_PRESS) }
        controller.onEvent(1, InEvent.UP)
        delay(5)
        controller.onEvent(1, InEvent.DOWN)
        delay(5)
        verify(exactly = 2) { mockedListener(any(), Event.SIMPLE_PRESS) }
        verify(exactly = 2) { mockedListener(any(), Event.MAINTAINED_PRESS) }
        verify(exactly = 0) { mockedListener(any(), Event.VERY_SHORT_PRESS) }
    }

    @Test
    fun `Test Up-Up-Up`() = runBlocking {
        val controller = getController(1)
        controller.onEvent(1, InEvent.UP, 1000)
        delay(50)
        controller.onEvent(1, InEvent.UP, 1000 + 50)
        delay(50)
        controller.onEvent(1, InEvent.UP, 1000 + 100)
        delay(5)
        verify(exactly = 0) { mockedListener(any(), any()) }
    }

    @Test
    fun `Test DOUBLE_CLICK`() = runBlocking {
        val controller = getController(1)
        controller.onEvent(1, InEvent.DOWN)
        delay(5)
        controller.onEvent(1, InEvent.UP)
        delay(5)
        controller.onEvent(1, InEvent.DOWN)
        delay(5)
        controller.onEvent(1, InEvent.UP)
        delay(5)
        verify(exactly = 1) { mockedListener(any(), Event.DOUBLE_PRESS) }
    }

    @Test
    fun `Test DOUBLE_CLICK max time`() = runBlocking {
        val controller = getController(1)
        controller.onEvent(1, InEvent.DOWN)
        delay(100)
        controller.onEvent(1, InEvent.UP)
        delay(300)
        controller.onEvent(1, InEvent.DOWN)
        delay(100)
        controller.onEvent(1, InEvent.UP)
        delay(5)
        verify(exactly = 1) { mockedListener(any(), Event.DOUBLE_PRESS) }
    }

    @Test
    fun `Exclude duplication DOUBLE_CLICK`() = runBlocking {
        val controller = getController(1)
        controller.onEvent(1, InEvent.DOWN)
        delay(5)
        controller.onEvent(1, InEvent.UP)
        delay(5)
        controller.onEvent(1, InEvent.DOWN)
        delay(5)
        controller.onEvent(1, InEvent.UP)
        delay(5)
        controller.onEvent(1, InEvent.DOWN)
        delay(5)
        controller.onEvent(1, InEvent.UP)
        delay(5)
        verify(exactly = 1) { mockedListener(any(), Event.DOUBLE_PRESS) }
    }
}
