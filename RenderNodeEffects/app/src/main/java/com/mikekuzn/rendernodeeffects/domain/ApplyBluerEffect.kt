package com.mikekuzn.rendernodeeffects.domain

import android.annotation.SuppressLint
import android.graphics.RenderEffect
import android.graphics.Shader
import android.view.View

class ApplyBluerEffect() : GetEffect {
    @SuppressLint("NewApi")
    override fun invoke(value: Float): RenderEffect? {
        if (value > 0) {
            return RenderEffect.createBlurEffect(value, value, Shader.TileMode.CLAMP)
        }
        return null
    }

    override val name = "BluerEffect"
    override val maxVal = 100
    override val startVal = 0
}