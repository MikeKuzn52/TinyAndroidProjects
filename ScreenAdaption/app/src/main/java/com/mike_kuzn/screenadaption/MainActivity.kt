package com.mike_kuzn.screenadaption

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.mike_kuzn.screenadaption.databinding.ActivityMainBinding

/*
 * Данное приложение было необходимо для отработки ошибки WindowManager
 * (суть ошибки - иногда черные квадраты на месте фрагментов)
 * При разделении версток ошибк не наблюдалась
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var screenWidthDp = 801
    private var position = 0
    private var positionOld = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null)
            position = savedInstanceState.getInt("position")

        update()
        showDetails(position)

        binding.Back.setOnClickListener {
            positionOld = position
            position = -1
            update()
        }
    }

    fun itemClick(position: Int) {
        this.position = position
        positionOld = position
        update()
        showDetails(position)
    }

    @Suppress("DEPRECATED_IDENTITY_EQUALS")
    private fun showDetails(pos: Int) {
        if (findViewById<FrameLayout>(R.id.cont) != null) {
            var details: Details? = supportFragmentManager
                .findFragmentById(R.id.cont) as Details?
            if (details == null || details.getPosition() !== pos) {
                details = Details.newInstance(pos)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.cont, details).commit()
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        screenWidthDp = newConfig.screenWidthDp
        update()
    }
    private fun update() {
        Log.i("MikeKuzn", "onConfigurationChanged $screenWidthDp")
        if (screenWidthDp > 800) {
            binding.Back.visibility = GONE
            binding.headerContainer.visibility = VISIBLE
            binding.cont.visibility = VISIBLE
            if (position == -1) {
                position = positionOld
                positionOld = -1
                showDetails(position)
            }
            //Log.i("MikeKuzn", "state 1")
        } else {
            binding.Back.visibility = VISIBLE
            if (position != -1 && positionOld != -1) {
                binding.headerContainer.visibility = GONE
                binding.cont.visibility = VISIBLE
                //Log.i("MikeKuzn", "state 2")
            } else {
                binding.headerContainer.visibility = VISIBLE
                binding.cont.visibility = GONE
                //Log.i("MikeKuzn", "state 3")
            }
        }
    }
}