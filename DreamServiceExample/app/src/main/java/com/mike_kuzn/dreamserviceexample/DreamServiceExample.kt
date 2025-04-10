package com.mike_kuzn.dreamserviceexample

import android.service.dreams.DreamService
import android.widget.ImageView

class DreamServiceExample : DreamService() {
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // Exit dream upon user touch
        isInteractive = false
        // Hide system UI
        isFullscreen = true
        // Set the dream layout
        setContentView(R.layout.dream)
        val image = findViewById<ImageView>(R.id.image)
        image.setImageResource(R.drawable.image)
    }
}
