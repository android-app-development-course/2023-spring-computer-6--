package com.example.teamup

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Typeface
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.teamup.dialog.DialogFragment
import java.text.SimpleDateFormat

class CreateTeamActivity : AppCompatActivity() {

    private lateinit var mTextView: TextView
    private lateinit var mCardView: CardView
    private lateinit var mbtnLayout: LinearLayout

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team)
        overridePendingTransition(0,0)

        var create_a_team = findViewById<TextView>(R.id.create_a_team)
        var face= Typeface.createFromAsset(assets,"Bodo Amat.ttf")
        create_a_team.setTypeface(face)


        supportActionBar?.hide() // 隐藏顶部栏
        mCardView = findViewById(R.id.cardview_create_a_team)
        mTextView = findViewById(R.id.create_a_team)
        mbtnLayout = findViewById(R.id.btn_create_team_layout)
        startAnim()

//        点击确定按钮的监听事件
        findViewById<Button>(R.id.create_team_confirm).setOnClickListener {
            exitAnim()
            Handler().postDelayed({
                DialogFragment().show(supportFragmentManager,"DialogFragment")
                overridePendingTransition(0,0)// 取消系统动画
                finish()
            }, 800) // 延迟 1 秒后执行跳转
        }

//        点击取消按钮的监听事件
        findViewById<Button>(R.id.create_team_cancel).setOnClickListener {
            exitAnim()
            Handler().postDelayed({
                DialogFragment().show(supportFragmentManager,"DialogFragment")
                overridePendingTransition(0,0)// 取消系统动画
                finish()
            }, 800)
        }
//      点击 截止日期 文本，显示 日期时间选择 的对话框
        val dateEditText = findViewById<EditText>(R.id.deadline)
        dateEditText.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val calendar: Calendar = Calendar.getInstance()
                val year: Int = calendar.get(Calendar.YEAR)
                val month: Int = calendar.get(Calendar.MONTH)
                val dayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)
                val hourOfDay: Int = calendar.get(Calendar.HOUR_OF_DAY)
                val minute: Int = calendar.get(Calendar.MINUTE)
                val datePickerDialog = DatePickerDialog(this@CreateTeamActivity,
                    { view, year, month, dayOfMonth -> // 在此处处理选定的日期
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, month)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }, year, month, dayOfMonth
                )
                datePickerDialog.setOnCancelListener {
                    // 处理取消对话框事件
                }
                val timePickerDialog = TimePickerDialog(this@CreateTeamActivity,
                    { view, hourOfDay, minute -> // 在此处处理选定的时间
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)

                        // 将日期和时间格式化为字符串并设置到 EditText 中
                        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
                        val dateTimeString: String = sdf.format(calendar.getTime())
                        dateEditText.setText(dateTimeString)
                    }, hourOfDay, minute, true
                )
                datePickerDialog.show()
                timePickerDialog.show()
            }
        })
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
        mTextView.startAnimation(anim1)
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
        mTextView.startAnimation(anim1)
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