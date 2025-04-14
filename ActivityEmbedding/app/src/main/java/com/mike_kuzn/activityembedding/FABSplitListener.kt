package com.mike_kuzn.activityembedding

import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Consumer
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.window.core.ExperimentalWindowApi
import androidx.window.embedding.SplitController
import androidx.window.embedding.SplitInfo
import com.google.android.material.floatingactionbutton.FloatingActionButton

abstract class FABProvider {
    abstract val fab: FloatingActionButton
    abstract val activity: AppCompatActivity
    open val label: String? = null
}

@OptIn(ExperimentalWindowApi::class)
class FABSplitListener(private val fabProvider: FABProvider) : DefaultLifecycleObserver {

    private val fabSplitListener = Consumer<List<SplitInfo>> { splitInfoList ->
        val shouldHideFAB = splitInfoList.lastOrNull()
            ?.primaryActivityStack
            ?.contains(fabProvider.activity)
            ?: false
        fabProvider.fab.visibility = if (shouldHideFAB) View.GONE else View.VISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        SplitController
            .getInstance()
            .addSplitListener(
                fabProvider.activity,
                fabProvider.activity.mainExecutor,
                fabSplitListener
            )
        fabProvider.fab.setOnClickListener {
            if (fabProvider.label != null) {
                Toast.makeText(
                    fabProvider.activity,
                    "Toast from ${fabProvider.label}",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                fabProvider.activity.finish()
            }
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        SplitController
            .getInstance()
            .removeSplitListener(fabSplitListener)
    }
}