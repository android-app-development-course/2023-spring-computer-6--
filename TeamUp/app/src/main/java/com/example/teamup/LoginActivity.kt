package com.example.teamup

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobACL
import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import com.example.teamup.DataClass.User


class LoginActivity : AppCompatActivity() {

    val BMOB_APPLICATION_ID = "8c5d3063d4274ca12e441a23f1a5261d"

    /** 上次点击返回键的时间  */
    private var lastBackPressed: Long = 0

    /** 两次点击的间隔时间  */
    private val QUIT_INTERVAL = 3000

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide() // 隐藏顶部栏
        Bmob.initialize(this, BMOB_APPLICATION_ID) //初始化数据库


//        val mydata=User("","","","","","","", arrayOf(), arrayOf())

//        val bmob =BmobObject()
//        bmob.save(object : SaveListener<String>() {
//            override fun done(objectId: String?, e: BmobException?) {
//                if (e == null) {
//                    Toast.makeText(this@LoginActivity,"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this@LoginActivity,"创建数据失败：" + e.message,Toast.LENGTH_LONG).show()
//                }
//            }
//        })


//        val p1=User("张三","asd","","AAA","asds","asdas","asdsaf", arrayOf(), arrayOf())
//        val p2 =User()
//        p2.userName="123"
//        p2.account="123456"
//        p2.password="passw"
//        p2.university="华南师范大学"
//        p2.major="2"
//        p2.gender="男"
//        p2.resume="你好"
//        p2.joinTeam= arrayOf( TeamInfo(p1.account,"对对对","dfdfdf","2023-06-23",5,0,
//            arrayOf(p1.account!!,p2.account!!)
//        ) )
//        p2.leadTeam= arrayOf(TeamInfo(p2.account,"sdf","4891","sdfsd",4,0,arrayOf(p1.account!!,p2.account!!)
//        ))
//
//
//        p2.save(object : SaveListener<String>() {
//            override fun done(objectId: String?, e: BmobException?) {
//                if (e == null) {
//                    Toast.makeText(this@LoginActivity,"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_SHORT).show()
//                } else {
////                    findViewById<TextView>(R.id.account).setText(e.message)
//                    Toast.makeText(this@LoginActivity,"创建数据失败：" + e.message,Toast.LENGTH_SHORT).show()
//
//                }
//            }
//        })





//        val query = BmobQuery<User>()
//
//        // 添加查询条件
//        query.addWhereEqualTo("userName", "123")
//
//        // 执行查询，并在回调函数中处理查询结果
//        query.findObjects(object : FindListener<User>() {
//            override fun done(persons: MutableList<User>?, e: BmobException?) {
//                if (e == null) {
//                    // 查询成功，处理查询结果
//                    if (persons != null && persons.size > 0) {
//                        // 查询到了符合条件的数据
//                        for (person in persons) {
//                            // 处理查询结果
//                            Log.d("TAG", ("**********" + person.university) ?: "123")
////                            findViewById<TextView>(R.id.account).setText(person.university)
//                        }
//                    } else {
//                        // 没有查询到符合条件的数据
//                    }
//                } else {
//                    // 查询失败，处理异常
//                }
//            }
//        })
//
//




//        val bmobQuery: BmobQuery<User> = BmobQuery<User>()
//        bmobQuery.getObject("6b6c11c537", object : QueryListener<User>() {
//            override fun done(object_: User, e: BmobException) {
//                if (e == null) {
//                    //toast("查询成功");
//                } else {
//                    //toast("查询失败：" + e.getMessage());
//                }
//            }
//        })


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

            if (accountEditText.text.toString()=="" || passwordEditText.text.toString()=="" ){
                Toast.makeText(this@LoginActivity,"请输入完整信息",Toast.LENGTH_SHORT).show()

            }
            else{
                val query = BmobQuery<User>()
                // 添加查询条件
                query.addWhereEqualTo("account", accountEditText.text.toString())
                // 执行查询，并在回调函数中处理查询结果
                query.findObjects(object : FindListener<User>() {
                    override fun done(persons: MutableList<User>?, e: BmobException?) {
                        if (e == null) {
                            // 查询成功，处理查询结果
                            if (persons != null && persons.size > 0) {

                                val query1 = BmobQuery<User>()
                                // 添加查询条件
                                query1.addWhereEqualTo("account", accountEditText.text.toString())
                                query1.addWhereEqualTo("password", passwordEditText.text.toString())
                                // 执行查询，并在回调函数中处理查询结果
                                query1.findObjects(object : FindListener<User>() {
                                    override fun done(persons1: MutableList<User>?, e: BmobException?) {
                                        if (e == null) {
                                            // 查询成功，处理查询结果
                                            if (persons1 != null && persons1.size == 1) {
                                                val intent = Intent(this@LoginActivity,MainActivity::class.java)
                                                intent.putExtra("id",persons1[0].objectId)
                                                Toast.makeText(this@LoginActivity,"登录成功",Toast.LENGTH_SHORT).show()

                                                val sharedPreferences: SharedPreferences =
                                                    getSharedPreferences("LoginUserInfo", MODE_PRIVATE)
                                                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                                editor.putString("id",persons1[0].objectId)
                                                editor.apply()

                                                startActivity(intent)
                                                overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out)
                                                finish()

                                            } else {
                                                // 没有查询到符合条件的数据，可以注册
                                                Toast.makeText(this@LoginActivity,"密码错误",Toast.LENGTH_SHORT).show()
                                            }
                                        } else {
                                            Toast.makeText(this@LoginActivity,"登录失败,请确定是否联网：" + e.message,Toast.LENGTH_SHORT).show()

                                        }
                                    }
                                })



                            } else {
                                // 没有查询到符合条件的数据，可以注册
                                Toast.makeText(this@LoginActivity,"该用户不存在",Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@LoginActivity,"登录失败,请确定是否联网：" + e.message,Toast.LENGTH_SHORT).show()

                        }
                    }
                })
            }







        }

//        预览按钮
        findViewById<TextView>(R.id.preview).setOnClickListener{
//            写入共享内存
            val sharedPreferences: SharedPreferences =
                getSharedPreferences("LoginUserInfo", MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("id","-1")
            editor.apply()

            val intent= Intent(this,MainActivity::class.java) //用于跳转
            intent.putExtra("id","-1")

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

    //重写onKeyDown()
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() === 0) {
            val backPressed = System.currentTimeMillis()
            if (backPressed - lastBackPressed > QUIT_INTERVAL) {
                lastBackPressed = backPressed
                Toast.makeText(this, "再按一次退出TeamUp", Toast.LENGTH_LONG).show()
            } else {
                finish()
                System.exit(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}