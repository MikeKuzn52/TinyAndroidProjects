package com.mikekuzn.hilttest

import android.content.ComponentCallbacks
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.ActionMode
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import androidx.appcompat.app.AppCompatActivity

open class TestActivity: AppCompatActivity() {
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        Log.i("TestLog", "onActivityResult")
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onNewIntent(intent: Intent) {
        Log.i("TestLog", "onNewIntent")
        super.onNewIntent(intent)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        Log.i("TestLog", "onSaveInstanceState")
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun registerComponentCallbacks(callback: ComponentCallbacks?) {
        Log.i("TestLog", "registerComponentCallbacks")
        super.registerComponentCallbacks(callback)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.i("TestLog", "dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }

    override fun dispatchTrackballEvent(ev: MotionEvent?): Boolean {
        Log.i("TestLog", "dispatchTrackballEvent")
        return super.dispatchTrackballEvent(ev)
    }

    override fun dispatchGenericMotionEvent(ev: MotionEvent?): Boolean {
        Log.i("TestLog", "dispatchGenericMotionEvent")
        return super.dispatchGenericMotionEvent(ev)
    }

    override fun dispatchPopulateAccessibilityEvent(event: AccessibilityEvent?): Boolean {
        Log.i("TestLog", "dispatchPopulateAccessibilityEvent")
        return super.dispatchPopulateAccessibilityEvent(event)
    }

    override fun onWindowAttributesChanged(params: WindowManager.LayoutParams?) {
        Log.i("TestLog", "onWindowAttributesChanged")
        super.onWindowAttributesChanged(params)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        Log.i("TestLog", "onWindowFocusChanged")
        super.onWindowFocusChanged(hasFocus)
    }

    override fun onAttachedToWindow() {
        Log.i("TestLog", "onAttachedToWindow")
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        Log.i("TestLog", "onDetachedFromWindow")
        super.onDetachedFromWindow()
    }

    override fun onWindowStartingActionMode(callback: ActionMode.Callback?): ActionMode? {
        Log.i("TestLog", "onWindowStartingActionMode")
        return super.onWindowStartingActionMode(callback)
    }

    override fun onKeyMultiple(keyCode: Int, repeatCount: Int, event: KeyEvent?): Boolean {
        Log.i("TestLog", "onKeyMultiple")
        return super.onKeyMultiple(keyCode, repeatCount, event)
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        Log.i("TestLog", "onTopResumedActivityChanged")
        super.onTopResumedActivityChanged(isTopResumedActivity)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        Log.i("TestLog", "onPostCreate")
        super.onPostCreate(savedInstanceState, persistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.i("TestLog", "onRestoreInstanceState")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.i("TestLog", "onTouchEvent")
        return super.onTouchEvent(event)
    }

    override fun onTrackballEvent(event: MotionEvent?): Boolean {
        Log.i("TestLog", "onTrackballEvent")
        return super.onTrackballEvent(event)
    }

    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        Log.i("TestLog", "onGenericMotionEvent")
        return super.onGenericMotionEvent(event)
    }

    override fun onWindowStartingActionMode(
        callback: ActionMode.Callback?,
        type: Int
    ): ActionMode? {
        Log.i("TestLog", "onWindowStartingActionMode")
        return super.onWindowStartingActionMode(callback, type)
    }

    override fun onRestart() {
        Log.i("TestLog", "onRestart")
        super.onRestart()
    }

    override fun onResume() {
        Log.i("TestLog", "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.i("TestLog", "onPause")
        super.onPause()
    }

    override fun onStart() {
        super.onStart()
        Log.i("TestLog", "onStart")
    }

    override fun onPostResume() {
        super.onPostResume()
        Log.i("TestLog", "onPostResume")
    }

    override fun onStop() {
        super.onStop()
        Log.i("TestLog", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("TestLog", "onDestroy")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TestLog", "onCreate")
    }
}

/*
3-4 раза при on/off keyboard и 1 раз при повороте
onTopResumedActivityChanged
onPause
onStop
onDestroy
onDetachedFromWindow
onWindowAttributesChanged
onWindowAttributesChanged
onCreate
onWindowAttributesChanged
onWindowAttributesChanged
onWindowAttributesChanged
onWindowAttributesChanged
onStart
onRestoreInstanceState
onWindowAttributesChanged
onResume
onPostResume
onTopResumedActivityChanged
onAttachedToWindow
onWindowFocusChanged
*/
