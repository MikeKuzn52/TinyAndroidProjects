package com.mike_kuzn.activityembedding.list

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import com.mike_kuzn.activityembedding.R
import com.mike_kuzn.activityembedding.databinding.ActivityListBinding
import com.mike_kuzn.activityembedding.details.DetailsActivity

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resources.getStringArray(R.array.headers).forEachIndexed { id, txt ->
            binding.mainViewGroup.addView(
                TextView(this).apply {
                    text = txt
                    textSize = 20F
                    setPadding(20)
                    setOnClickListener {
                        DetailsActivity.openDetailScreenFor(
                            resources.getStringArray(R.array.content)[id],
                            with = this@ListActivity
                        )
                    }
                }
            )
        }
    }

}