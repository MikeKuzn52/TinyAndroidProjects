package com.mikekuzn.animation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var bigIcon: ImageView
    private var smallIcon: ImageView? = null
    var count:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       bigIcon = findViewById(R.id.bigIcon)
        smallIcon = findViewById(R.id.smallIcon)

        var animRotateIn_icon: Animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        smallIcon?.startAnimation(animRotateIn_icon);
    }

    override fun onResume() {
        super.onResume()
        var animRotateIn_big: Animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        bigIcon?.startAnimation(animRotateIn_big)
    }

    fun button1(view: View) {
        var secondIntent: Intent = Intent(this, SecontdainActivity::class.java)
        startActivity(secondIntent)
        overridePendingTransition(R.anim.diagonaltranslate,R.anim.alpha)
    }

    override fun onUserLeaveHint() {
        Toast.makeText(applicationContext, "Нажата кнопка HOME", Toast.LENGTH_SHORT).show()
        super.onUserLeaveHint()
    }

    override fun onBackPressed(){
        when(count) {
            0 -> Toast.makeText(applicationContext, "Попробуй еще раз", Toast.LENGTH_SHORT).show()
            1 -> Toast.makeText(applicationContext, "Ну еще разок", Toast.LENGTH_SHORT).show()
            2 -> {
                Toast.makeText(applicationContext, "А ты настойчивый", Toast.LENGTH_SHORT).show()
                super.onBackPressed()
            }
        }
        count++
    }
}