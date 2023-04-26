package com.example.teamup

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.teamup.R
import com.example.teamup.RegisterActivity
import com.google.android.material.textview.MaterialTextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide() // 隐藏顶部栏

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


        // 点击登录按钮，跳转至主页
        findViewById<Button>(R.id.login_btn).setOnClickListener {
            val accountEditText = findViewById<EditText>(R.id.account)
            val passwordEditText = findViewById<EditText>(R.id.password)

            // (暂时) 直接登录成功，不进行判断
            if(false) {
                val resultAccount = accountEditText.text.toString()
                val resultPassword = passwordEditText.text.toString()
                val intent = Intent(this,MainActivity::class.java).apply {
                    putExtra("resultAccount", resultAccount)
                    putExtra("resultPassword", resultPassword)
                }
                setResult(Activity.RESULT_OK, intent)
                startActivity(intent)
                finish()
            }
        }

//        预览按钮
        findViewById<TextView>(R.id.preview).setOnClickListener{
            val intent= Intent(this,MainActivity::class.java) //用于跳转
            startActivity(intent)
            overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out)
            finish()
        }

        //前往注册
        findViewById<TextView>(R.id.register).setOnClickListener {

            val intent1= Intent(this,RegisterActivity::class.java) //用于跳转
            startActivity(intent1)
            overridePendingTransition(R.anim.slide_f_t,R.anim.slide_t_b)
            finish()
        }
    }

//    override fun onBackPressed() {
//        // Do nothing
//    }

}