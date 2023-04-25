package com.mikekuzn.rendernodeeffects.domain

import android.annotation.SuppressLint
import android.graphics.RenderEffect
import android.view.View

interface GetEffect {
    @SuppressLint("NewApi")
    fun invoke(value: Float): RenderEffect?
    val name: String
    val maxVal: Int
    val startVal: Int
}