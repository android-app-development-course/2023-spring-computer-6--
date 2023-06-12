package com.example.teamup

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Typeface
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import android.telecom.Call.Details
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import com.example.teamup.DataClass.TeamInfo
import com.example.teamup.DataClass.User
import java.text.SimpleDateFormat

class CreateTeamActivity : AppCompatActivity() {
    private lateinit var mTitleView: TextView
    private lateinit var mCardView: CardView
    private lateinit var mbtnLayout: LinearLayout
    private lateinit var TeamNameView: TextView
    private lateinit var DeadLineView: TextView
    private lateinit var maxMemberView: TextView
    private lateinit var DetailsView: TextView

    @SuppressLint("CutPasteId", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team)
        overridePendingTransition(0,0)
        supportActionBar?.hide() // 隐藏顶部栏

        mTitleView = findViewById(R.id.create_a_team_title)
        var face= Typeface.createFromAsset(assets,"Bodo Amat.ttf")
        mTitleView.setTypeface(face)
        mCardView = findViewById(R.id.cardview_create_a_team)
        mbtnLayout = findViewById(R.id.btn_create_team_layout)
        TeamNameView = findViewById(R.id.create_team_name)
        DeadLineView = findViewById(R.id.deadline)
        maxMemberView = findViewById(R.id.num_of_attendees)
        DetailsView = findViewById(R.id.create_team_info)
        startAnim()
        //

        val UserID = intent.getStringExtra("id")     // 当前登录用户 的 ObjectID
//        点击确定按钮的监听事件
        findViewById<Button>(R.id.create_team_confirm).setOnClickListener {
//          1. 添加队伍信息
            val team = TeamInfo(
                UserID,
                TeamNameView.text.toString(),
                DetailsView.text.toString(),
                DeadLineView.text.toString(),
                maxMemberView.text.toString().toInt() ,
                1,
                arrayOf()
            )
            team.save(object : SaveListener<String>() {
                override fun done(objectId: String?, e: BmobException?) {
                    if (e == null) {
                        Toast.makeText(
                            this@CreateTeamActivity,
                            "创建团队成功，返回objectId为：" + objectId,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@CreateTeamActivity,
                            "创建团队失败：" + e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
//          2. 更新UserID对应的信息，增加领导队伍
            exitAnim()
            Handler().postDelayed({
                DialogUnLogin().show(supportFragmentManager,"DialogFragment")
                overridePendingTransition(0,0)// 取消系统动画
                finish()
            }, 800) // 延迟 1 秒后执行跳转
        }

//        点击取消按钮的监听事件
        findViewById<Button>(R.id.create_team_cancel).setOnClickListener {
            exitAnim()
            Handler().postDelayed({
                DialogUnLogin().show(supportFragmentManager,"DialogFragment")
                overridePendingTransition(0,0)// 取消系统动画
                finish()
            }, 800)
        }

//      点击 截止日期 文本，显示 日期时间选择 的对话框
        val dateEditText = findViewById<EditText>(R.id.deadline)
        dateEditText.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val year: Int = calendar.get(Calendar.YEAR)
            val month: Int = calendar.get(Calendar.MONTH)
            val dayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)
            val hourOfDay: Int = calendar.get(Calendar.HOUR_OF_DAY)
            val minute: Int = calendar.get(Calendar.MINUTE)
            val datePickerDialog = DatePickerDialog(
                this@CreateTeamActivity,
                { view, year, month, dayOfMonth -> // 在此处处理选定的日期
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }, year, month, dayOfMonth
            )
            val timePickerDialog = TimePickerDialog(
                this@CreateTeamActivity,
                { view, hourOfDay, minute -> // 在此处处理选定的时间
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)

                    // 将日期和时间格式化为字符串并设置到 EditText 中
                    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
                    val dateTimeString: String = sdf.format(calendar.getTime())
                    dateEditText.setText(dateTimeString)
                }, hourOfDay, minute, true
            )
            timePickerDialog.show()
            datePickerDialog.show()
        }
    }

    private fun startAnim() {
        // 创建平移动画
        val anim1 = TranslateAnimation(0f, 0f, -250f, 0f)
        anim1.duration = 1000
        anim1.interpolator = DecelerateInterpolator()

        // 创建平移动画
        val anim2 = TranslateAnimation(0f, 0f, 4000f, 0f)
        anim2.duration = 700
        anim2.startOffset=300
        anim2.interpolator = DecelerateInterpolator()

        // 开始动画
        mTitleView.startAnimation(anim1)
        mCardView.startAnimation(anim2)
        mbtnLayout.startAnimation(anim2)
    }
    private fun exitAnim() {
        // 创建平移动画
        val anim1 = TranslateAnimation(0f, 0f,  0f,-250f)
        anim1.duration = 1000
        anim1.interpolator = AccelerateInterpolator()

        // 创建平移动画
        val anim2 = TranslateAnimation(0f, 0f,  0f,4000f)
        anim2.duration = 700
        anim2.startOffset= 300
        anim2.interpolator = AccelerateInterpolator()

        // 开始动画
        mTitleView.startAnimation(anim1)
        mCardView.startAnimation(anim2)
        mbtnLayout.startAnimation(anim2)
    }
    override fun onBackPressed() {
        exitAnim()
        Handler().postDelayed({
            finish()
        }, 800) // 延迟 1 秒后执行跳转
    }
}