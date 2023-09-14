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
    private var count:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       bigIcon = findViewById(R.id.bigIcon)
        smallIcon = findViewById(R.id.smallIcon)

        val animRotateIn_icon: Animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        smallIcon?.startAnimation(animRotateIn_icon);
    }

    override fun onResume() {
        super.onResume()
        val animRotateIn_big: Animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        bigIcon.startAnimation(animRotateIn_big)
        count = 0
    }

    fun button1(view: View) {
        val secondIntent: Intent = Intent(this, SecondActivity::class.java)
        startActivity(secondIntent)
        overridePendingTransition(R.anim.diagonaltranslate,R.anim.alpha)
    }

    fun button2(view: View) {
        startActivity(Intent(this, ThirdActivity::class.java))
    }

    fun button3(view: View) {
        startActivity(Intent(this, FourthActivity::class.java))
    }

    override fun onUserLeaveHint() {
        Toast.makeText(applicationContext, "User Leave Hint", Toast.LENGTH_SHORT).show()
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