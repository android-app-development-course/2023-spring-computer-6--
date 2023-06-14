package com.example.teamup

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*

import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobACL
import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import com.example.teamup.DataClass.User

class RegisterActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    val BMOB_APPLICATION_ID = "8c5d3063d4274ca12e441a23f1a5261d"
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide() // 隐藏顶部栏


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

        Bmob.initialize(this, BMOB_APPLICATION_ID)
        findViewById<Button>(R.id.register_bt).setOnClickListener {
            val account_edit=findViewById<EditText>(R.id.account2)
            val pwd_new_edit=findViewById<EditText>(R.id.code)
            val pwd_conf_edit=findViewById<EditText>(R.id.code_again)

            if( account_edit.text.toString()=="" || pwd_new_edit.text.toString()=="" || pwd_conf_edit.text.toString()=="" ){
                Toast.makeText(this@RegisterActivity,"请输入完整信息", Toast.LENGTH_SHORT).show()

            }
            else{

                if(  pwd_new_edit.text.toString()!= pwd_conf_edit.text.toString() ){
                    Toast.makeText(this@RegisterActivity,"两次密码不一致", Toast.LENGTH_SHORT).show()

                }
                else{
                    val query = BmobQuery<User>()

                    // 添加查询条件
                    query.addWhereEqualTo("account", account_edit.text.toString())

                    // 执行查询，并在回调函数中处理查询结果
                    query.findObjects(object : FindListener<User>() {
                        override fun done(persons: MutableList<User>?, e: BmobException?) {
                            if (e == null) {
                                // 查询成功，处理查询结果
                                if (persons != null && persons.size > 0) {
                                    Toast.makeText(this@RegisterActivity,"该账号已经存在",Toast.LENGTH_SHORT).show()

                                } else {
                                    // 没有查询到符合条件的数据，可以注册

                                    val temp_user=User(
                                        account_edit.text.toString(),
                                        account_edit.text.toString(),
                                        pwd_conf_edit.text.toString(),
                                        "无",
                                        "无",
                                        "无",
                                        "无",
                                        arrayOf(),
                                        arrayOf())


                                    temp_user.save(object : SaveListener<String>() {
                                        override fun done(objectId: String?, e: BmobException?) {
                                            if (e == null) {

                                                //            写入共享内存
                                                val sharedPreferences: SharedPreferences =
                                                    getSharedPreferences("LoginUserInfo", MODE_PRIVATE)
                                                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                                editor.putString("id",objectId)
                                                editor.apply()

                                                Toast.makeText(this@RegisterActivity,"注册成功",Toast.LENGTH_SHORT).show()

                                                val intent1= Intent(this@RegisterActivity, MainActivity::class.java) //用于跳转
                                                intent1.putExtra("id",objectId)

                                                startActivity(intent1)
                                                finish()
                                                overridePendingTransition(R.anim.slide_f_b,R.anim.slide_t_t)
                                                finish()

                                            } else {
                                                Toast.makeText(this@RegisterActivity,"注册用户失败,请确定是否联网：" + e.message,Toast.LENGTH_SHORT).show()

                                            }
                                        }
                                    })

                                }
                            } else {
                                Toast.makeText(this@RegisterActivity,"注册用户失败,请确定是否联网：" + e.message,Toast.LENGTH_SHORT).show()

                            }
                        }
                    })
                }


            }


        }



    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent1= Intent(this,LoginActivity::class.java) //用于跳转
        startActivity(intent1)
        overridePendingTransition(R.anim.slide_f_b,R.anim.slide_t_t)
    }
}