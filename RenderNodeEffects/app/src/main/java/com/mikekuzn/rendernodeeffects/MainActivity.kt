package com.mikekuzn.rendernodeeffects

import android.graphics.RenderEffect
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.mikekuzn.rendernodeeffects.databinding.ActivityMainBinding
import com.mikekuzn.rendernodeeffects.domain.ApplyBluerEffect
import com.mikekuzn.rendernodeeffects.domain.ApplyBrightnessEffect
import com.mikekuzn.rendernodeeffects.domain.GetEffect

class MainActivity : AppCompatActivity(), Runnable {
    private lateinit var binding: ActivityMainBinding
    private val seekBarListenerList = ArrayList<SeekBarListener>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            Toast.makeText(this, "Not supported on ANDROID previous version 12", Toast.LENGTH_LONG)
                .show()
            return
        }

        binding.bluer.Seek.setOnSeekBarChangeListener(
            SeekBarListener(
                seekBarListenerList,
                this,
                binding.bluer.Seek,
                binding.bluer.SeekText,
                ApplyBluerEffect()
            )
        )
        binding.brightness.Seek.setOnSeekBarChangeListener(
            SeekBarListener(
                seekBarListenerList,
                this,
                binding.brightness.Seek,
                binding.brightness.SeekText,
                ApplyBrightnessEffect()
            )
        )

        var imageList = arrayOf(
            R.drawable.i1,
            R.drawable.i2,
            R.drawable.i3,
        )
        val viewPagerAdapter = ViewPagerAdapter(this@MainActivity, imageList)
        binding.idViewPager.adapter = viewPagerAdapter
    }


    class SeekBarListener constructor(
        private var list: ArrayList<SeekBarListener>,
        private val runnable: Runnable,
        seekBar: SeekBar,
        textView: TextView,
        private val effect: GetEffect
        ): OnSeekBarChangeListener{
        var renderEffect: RenderEffect? = null
        init {
            textView.text = effect.name
            seekBar.max = effect.maxVal
            seekBar.progress = effect.startVal
            list.add(this)
        }
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            renderEffect = effect.invoke(progress.toFloat())
            runnable.run()
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun run() {
        var finalEffect: RenderEffect? = null
        seekBarListenerList.forEach{newEffect ->
            newEffect.renderEffect?: return@forEach
            finalEffect?.let {
                finalEffect = RenderEffect.createChainEffect(
                    it,
                    newEffect.renderEffect!!)
            }?: run {
                finalEffect = newEffect.renderEffect
            }
        }
        binding.viewGroupToApplyEffects.setRenderEffect(finalEffect)
    }
}