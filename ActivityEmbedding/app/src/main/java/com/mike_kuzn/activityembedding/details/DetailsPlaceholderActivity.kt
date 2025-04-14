package com.mike_kuzn.activityembedding.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mike_kuzn.activityembedding.FABProvider
import com.mike_kuzn.activityembedding.FABSplitListener
import com.mike_kuzn.activityembedding.databinding.ActivityDetailsPlaceholderBinding

class DetailsPlaceholderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDetailsPlaceholderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(FABSplitListener(object : FABProvider() {
            override val fab: FloatingActionButton by lazy { binding.fab }
            override val activity: AppCompatActivity = this@DetailsPlaceholderActivity
            override val label = "DetailsPlaceholderActivity"
        }))
    }
}