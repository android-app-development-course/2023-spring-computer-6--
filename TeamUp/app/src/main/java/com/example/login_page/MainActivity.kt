package com.example.login_page

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var appname = findViewById<TextView>(R.id.appname)
        var face=Typeface.createFromAsset(assets,"Boleh.ttf")
        appname.setTypeface(face)

        var weic = findViewById<TextView>(R.id.welc)
        face=Typeface.createFromAsset(assets,"Jackpot.ttf")
        weic.setTypeface(face)

        var login_btn = findViewById<TextView>(R.id.login_btn)
        face=Typeface.createFromAsset(assets,"Lemon Days.ttf")
        login_btn.setTypeface(face)

        //密码是否可见
        var judge1:Boolean=false
        var code_vis: ImageView =findViewById<ImageView>(R.id.password_vis)
        var code_edit: EditText =findViewById<EditText>(R.id.password)
        code_vis.setOnClickListener{
            if(judge1==false){
                code_vis.setImageResource(R.drawable.visible)
                code_edit.transformationMethod= HideReturnsTransformationMethod.getInstance()

            }
            else{
                code_vis.setImageResource(R.drawable.invisible)
                code_edit.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            code_edit.setSelection(code_edit.text.length)
            judge1=!judge1
        }

        //背景移动
        findViewById<View>(R.id.login_move_box).startAnimation(AnimationUtils.loadAnimation(
            applicationContext,R.anim.boxmove_login
        ))


        //前往注册
        findViewById<TextView>(R.id.register).setOnClickListener {


            val intent1= Intent(this,register_activity::class.java) //用于跳转
            startActivity(intent1)
            overridePendingTransition(R.anim.slide_f_t,R.anim.slide_t_b)
            finish()
        }






    }

}