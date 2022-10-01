package com.mikekuzn.examle.pickimageexample

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import coil.load
import com.mikekuzn.examle.pickimageexample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val getImageUrl =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
                imageUrl: Uri? ->
            binding.image.load(imageUrl)
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            getImageUrl.launch("image/*")
        }
    }
}