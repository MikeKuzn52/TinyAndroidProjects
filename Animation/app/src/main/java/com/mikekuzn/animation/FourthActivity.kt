package com.mikekuzn.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import com.mikekuzn.animation.databinding.ActivityFourthBinding


class FourthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFourthBinding
    private val duration: Long = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFourthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button1.setOnClickListener {
            val onOff = binding.testImage.visibility == GONE
            val start = if (onOff) 0F else 1F
            val end = if (!onOff) 0F else 1F

            val animator1 = ValueAnimator.ofFloat(start, end).apply {
                duration = this@FourthActivity.duration
                interpolator = AccelerateInterpolator()
                addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                    override fun onAnimationUpdate(animation: ValueAnimator) {
                        binding.testImage.alpha = animation.animatedValue as Float
                        binding.testImage.visibility = VISIBLE
                    }
                })
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        binding.testImage.visibility = if (onOff) VISIBLE else GONE
                    }
                })
            }

            val animator2 = ValueAnimator.ofFloat(end*500, start*500).apply {
                duration = this@FourthActivity.duration / 2
                startDelay = this@FourthActivity.duration / 2
                interpolator = BounceInterpolator()
                addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                    override fun onAnimationUpdate(animation: ValueAnimator) {
                        binding.text1.x = animation.animatedValue as Float
                    }
                })
            }
            AnimatorSet().apply {
                playTogether(animator2, animator1)
                start()
            }
        }

        binding.button2.setOnClickListener{
            val onOff = binding.testImage.visibility == GONE
            val start = if (onOff) 0F else 1F
            val end = if (!onOff) 0F else 1F
            val animator1 = ObjectAnimator.ofFloat(binding.testImage, "alpha", end)
                .apply {
                    duration = this@FourthActivity.duration / 2
                    //interpolator = AccelerateInterpolator()
                    interpolator = TimeInterpolator {
                        Log.d("***[", "val = $it")
                        it
                    }
                    doOnStart { if (onOff) binding.testImage.visibility = VISIBLE; Log.d("***[", "Start") }
                    doOnEnd { if (!onOff) binding.testImage.visibility =  GONE; Log.d("***[", "End")}
                }
            val animator2 = ObjectAnimator.ofFloat(binding.text1, "x", end*500, start*500)
                .apply {
                    duration = this@FourthActivity.duration / 2
                    interpolator = BounceInterpolator()
                }
            AnimatorSet().apply {
                playSequentially(animator1, animator2)
                start()
            }
        }
    }
}