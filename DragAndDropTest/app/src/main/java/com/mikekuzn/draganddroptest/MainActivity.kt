package com.mikekuzn.draganddroptest

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.mikekuzn.draganddroptest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val TEXTVIEW_TAG = "DRAG TEXT"
    private val BUTTON_TAG = "DRAG BUTTON"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvDraggable.tag = TEXTVIEW_TAG;
        binding.btnDraggable.tag = BUTTON_TAG;
        val dragListener = DragListener()
        val longClickListener = LongClickListener()
        binding.bottomLayout.setOnDragListener(dragListener)
        binding.topLayout.setOnDragListener(dragListener)
        binding.btnDraggable.setOnLongClickListener(longClickListener)
        binding.tvDraggable.setOnLongClickListener(longClickListener)
    }

    class LongClickListener: OnLongClickListener {
        override fun onLongClick(view: View?): Boolean {
            view?.let {
                val item = ClipData.Item(view.tag as CharSequence)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(view.tag.toString(), mimeTypes, item)

                val shadowBuilder = DragShadowBuilder(view)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    view.startDragAndDrop(data //data to be dragged
                        , shadowBuilder //drag shadow
                        , view //local data about the drag and drop operation
                        , 0 //flags (not currently used, set to 0)
                    )
                } else {
                    view.startDrag(data //data to be dragged
                        , shadowBuilder //drag shadow
                        , view //local data about the drag and drop operation
                        , 0 //flags (not currently used, set to 0)
                    )
                }
                view.visibility = View.INVISIBLE
            }
            return true
        }
    }

    class DragListener: View.OnDragListener {
        override fun onDrag(newOwner: View?, dragEvent: DragEvent?): Boolean {
            if (dragEvent == null) return false
            if (newOwner !is LinearLayout) return false

                when (dragEvent.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    return dragEvent.clipDescription
                            .hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        newOwner.background.colorFilter = BlendModeColorFilter(Color.MAGENTA, BlendMode.SRC_IN)
                    } else {
                        newOwner.background.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_IN)
                    }
                    newOwner.invalidate()
                    return true
                }
                DragEvent.ACTION_DRAG_LOCATION ->
                    return true
                DragEvent.ACTION_DRAG_EXITED -> {
                    newOwner.background.clearColorFilter()
                    newOwner.invalidate()
                    return true
                }
                DragEvent.ACTION_DROP -> {
                    newOwner.background.clearColorFilter()
                    newOwner.invalidate()
                    val droppedView = dragEvent.localState as View
                    val owner = droppedView.parent as ViewGroup
                    owner.removeView(droppedView)
                    newOwner.addView(droppedView)
                    droppedView.visibility = View.VISIBLE
                    return true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    newOwner.background.clearColorFilter()
                    newOwner.invalidate()
                    Log.d("DragDrop Example", "Drag&Drop reult=${dragEvent.getResult()}")
                    // invoke getResult(), and displays what happened.
                    return true
                }
                else -> Log.e("DragDrop Example", "Unknown action type received by OnDragListener.")
            }
            return false
        }
    }
}