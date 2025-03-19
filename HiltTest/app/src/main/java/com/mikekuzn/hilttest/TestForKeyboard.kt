package com.mikekuzn.hilttest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import com.mikekuzn.hilttest.databinding.ActivityTestForKeyboardBinding
import javax.inject.Inject

class TestForKeyboard : TestActivity() {

    private lateinit var binding: ActivityTestForKeyboardBinding

    private val viewModel: MyViewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTestForKeyboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.text.setText(viewModel.data)
        binding.text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.data = p0.toString()
            }
        })
    }
}