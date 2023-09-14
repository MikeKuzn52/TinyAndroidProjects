package com.mikekuzn.animation

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.mikekuzn.animation.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button1.setOnClickListener{
            binding.text2.visibility = VISIBLE
            binding.testImage.visibility =  if (binding.testImage.visibility == GONE)
                VISIBLE
            else
                GONE
        }
        binding.button2.setOnClickListener{
            if (binding.testImage.visibility == GONE) {
                binding.testImage.visibility = VISIBLE
                binding.text2.visibility = GONE
                binding.text3.visibility = VISIBLE
            } else {
                binding.testImage.visibility = GONE
                binding.text2.visibility = VISIBLE
                binding.text3.visibility = GONE
            }

        }
    }
}