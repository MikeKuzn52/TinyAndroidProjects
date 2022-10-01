package com.mikekuzn.examle.pickimageexample

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import coil.load
import com.mikekuzn.examle.pickimageexample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            imagePicker.PickImage("image/*")
        }
    }

    private val imagePicker = ImagePicker(activityResultRegistry) { imageUrl ->
        binding.image.load(imageUrl)
    }
}

class ImagePicker(
    private val activityResultRegistry: ActivityResultRegistry,
    private val callBack: (imageUri:Uri?) -> Unit) {
    private val getImageUrl =
        activityResultRegistry.register("ImagePicker",ActivityResultContracts.GetContent(), callBack)
    fun PickImage(req:String) {
        getImageUrl.launch(req)
    }
}