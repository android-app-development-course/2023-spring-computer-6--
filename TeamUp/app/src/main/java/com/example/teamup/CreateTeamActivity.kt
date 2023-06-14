package com.example.teamup

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.SharedPreferences
import android.graphics.Typeface
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
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
        // 读取内存
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("LoginUserInfo", MODE_PRIVATE)
        var UserID = sharedPreferences.getString("id","-1") ?: "-1"  // 当前登录用户 的 ObjectID

//        点击确定按钮的监听事件
        findViewById<Button>(R.id.create_team_confirm).setOnClickListener {
            var teamName = TeamNameView.text.toString()
            var teamDetail = DetailsView.text.toString()
            var teamDeadLine = DeadLineView.text.toString()
            var teamMaxMember = maxMemberView.text.toString()
//          1. 检查是否有 空表单
            if(teamName == "" || teamDetail == "" ||
                teamDeadLine == "" || teamMaxMember == "" ) {
                Toast.makeText(this,"请填写完整信息",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//          2. 添加队伍信息
            val team = TeamInfo(
                UserID,
                teamName,
                teamDetail,
                teamDeadLine,
                teamMaxMember.toInt(),
                arrayOf()
            )
            team.save(object : SaveListener<String>() {
                override fun done(newTeamID: String?, e: BmobException?) {
                    if (e == null) {
                        if (newTeamID != null) {
//          3. 更新UserID对应的信息，增加领导队伍
                            var user = User()
                            user.objectId = UserID
                            user.add("leadTeam", newTeamID)
                            user.leadTeam?.distinct() // 去重
                            user.update(object : UpdateListener() {
                                override fun done(e: BmobException?) {
                                    if (e == null) { // 更新成功
                                        Toast.makeText(
                                            this@CreateTeamActivity,
                                            "创建团队成功",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else { // 更新失败
                                        Toast.makeText(
                                            this@CreateTeamActivity,
                                            "创建团队成功，leadTeam 数组添加失败",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            })
                        }

                    } else {
                        Toast.makeText(
                            this@CreateTeamActivity,
                            "创建团队失败：" + e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("MyError",e.message?:"")
                    }
                }
            })
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

// 点击截止日期文本时显示日期时间选择器
        val dateEditText = findViewById<EditText>(R.id.deadline)
        dateEditText.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val year: Int = calendar.get(Calendar.YEAR)
            val month: Int = calendar.get(Calendar.MONTH)
            val dayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)

            // 创建日期选择器
            val datePickerDialog = DatePickerDialog(
                this@CreateTeamActivity,
                { view, year, month, dayOfMonth -> // 在此处处理选定的日期
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    // 创建时间选择器
                    val hourOfDay: Int = calendar.get(Calendar.HOUR_OF_DAY)
                    val minute: Int = calendar.get(Calendar.MINUTE)
                    val timePickerDialog = TimePickerDialog(
                        this@CreateTeamActivity,
                        { view, hourOfDay, minute -> // 在此处处理选定的时间
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            calendar.set(Calendar.MINUTE, minute)

                            // 将日期和时间格式化为字符串并设置到 EditText 中
                            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
                            val dateTimeString: String = sdf.format(calendar.getTime())
                            dateEditText.setText(dateTimeString)
                        },
                        hourOfDay,
                        minute,
                        true
                    )
                    timePickerDialog.show()
                },
                year,
                month,
                dayOfMonth
            )
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