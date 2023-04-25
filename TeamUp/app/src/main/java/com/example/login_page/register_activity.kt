package com.example.login_page

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class register_activity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var register_t= findViewById<TextView>(R.id.register_title)
        var face= Typeface.createFromAsset(assets,"Jackpot.ttf")
        register_t.setTypeface(face)

        var back_bt = findViewById<TextView>(R.id.back_bt)
        face=Typeface.createFromAsset(assets,"Lemon Days.ttf")
        back_bt.setTypeface(face)

        var register_bt = findViewById<TextView>(R.id.register_bt)
        face=Typeface.createFromAsset(assets,"Lemon Days.ttf")
        register_bt.setTypeface(face)

        //背景移动
        findViewById<View>(R.id.register_move_box).startAnimation(
            AnimationUtils.loadAnimation(
            applicationContext,R.anim.boxmove_register
        ))

        //密码是否可见
        var judge1:Boolean=false
        var code_vis:ImageView=findViewById<ImageView>(R.id.code_vis)
        var code_edit:EditText=findViewById<EditText>(R.id.code)
        code_vis.setOnClickListener{
            if(judge1==false){
                code_vis.setImageResource(R.drawable.visible)
                code_edit.transformationMethod=HideReturnsTransformationMethod.getInstance()

            }
            else{
                code_vis.setImageResource(R.drawable.invisible)
                code_edit.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            code_edit.setSelection(code_edit.text.length)
            judge1=!judge1
        }

        var judge2:Boolean=false
        var code_again_vis:ImageView=findViewById<ImageView>(R.id.code_again_vis)
        var code_again_edit:EditText=findViewById<EditText>(R.id.code_again)
        code_again_vis.setOnClickListener{
            if(judge2==false){
                code_again_vis.setImageResource(R.drawable.visible)
                code_again_edit.transformationMethod=HideReturnsTransformationMethod.getInstance()
            }
            else{
                code_again_vis.setImageResource(R.drawable.invisible)
                code_again_edit.transformationMethod = PasswordTransformationMethod.getInstance()

            }
            code_again_edit.setSelection(code_again_edit.text.length)
            judge2=!judge2

        }




        //返回
        findViewById<Button>(R.id.back_bt).setOnClickListener {
            onBackPressed()
            finish()

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent1= Intent(this,MainActivity::class.java) //用于跳转
        startActivity(intent1)

        overridePendingTransition(R.anim.slide_f_b,R.anim.slide_t_t)


    }
}