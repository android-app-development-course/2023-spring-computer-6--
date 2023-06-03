package com.example.teamup

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.TextView

class OpeningActivity : AppCompatActivity() {

    private val handler=Handler()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opening)
        supportActionBar?.hide() // 隐藏顶部栏

        var resume_title = findViewById<TextView>(R.id.resume_title)
        var face= Typeface.createFromAsset(assets,"Boleh.ttf")
        resume_title.setTypeface(face)


        findViewById<TextView>(R.id.resume_title).startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,R.anim.resume_title_move
            ))

    }

    private val runnable= Runnable {
        if(!isFinishing){
            val intent1= Intent(this,LoginActivity::class.java) //用于跳转
            startActivity(intent1)
            overridePendingTransition(R.anim.static_bg,R.anim.resume_move)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable,2000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }
}