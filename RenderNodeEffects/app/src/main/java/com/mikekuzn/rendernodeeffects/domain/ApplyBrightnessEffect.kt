package com.mikekuzn.rendernodeeffects.domain

import android.annotation.SuppressLint
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.RenderEffect
import android.util.Log
import android.view.View

class ApplyBrightnessEffect() : GetEffect {
    @SuppressLint("NewApi")
    override fun invoke(value: Float): RenderEffect {
        val brightness = value - 256
        Log.d("***", "Brightness val=$brightness")
        val colorMatrix = ColorMatrix()
        /*colorMatrix.set(floatArrayOf(
            brightness, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, brightness, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, brightness, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, brightness, 0.0f))*/
        colorMatrix.set(floatArrayOf(
            1.0f, 0.0f, 0.0f, 0.0f, brightness,
            0.0f, 1.0f, 0.0f, 0.0f, brightness,
            0.0f, 0.0f, 1.0f, 0.0f, brightness,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f))
        val filter = ColorMatrixColorFilter(colorMatrix)
        return RenderEffect.createColorFilterEffect(filter)
    }

    override val name = "BrightnessEffect"
    override val maxVal = 512
    override val startVal = 255
}