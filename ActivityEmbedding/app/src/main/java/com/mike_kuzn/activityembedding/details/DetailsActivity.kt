package com.mike_kuzn.activityembedding.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mike_kuzn.activityembedding.FABProvider
import com.mike_kuzn.activityembedding.FABSplitListener
import com.mike_kuzn.activityembedding.databinding.ActivityDetailsBinding
import com.mike_kuzn.activityembedding.share.ShareActivity

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(FABSplitListener(object : FABProvider() {
            override val fab: FloatingActionButton by lazy { binding.fab }
            override val activity: AppCompatActivity = this@DetailsActivity
        }))

        val text = getText()
        binding.detailTextView.text = text
        binding.shareTextView.setOnClickListener {
            ShareActivity.openShareScreenFor(text, this)
        }
    }

    private fun getText() = intent.getStringExtra(EXTRA_LETTER)
        ?: throw IllegalStateException("Must pass a letter to DetailActivity")


    companion object {
        private const val EXTRA_LETTER = "extra-letter"

        fun openDetailScreenFor(letter: String, with: Context) {
            val intent = Intent(with, DetailsActivity::class.java)
            intent.putExtra(EXTRA_LETTER, letter)
            with.startActivity(intent)
        }
    }
}
