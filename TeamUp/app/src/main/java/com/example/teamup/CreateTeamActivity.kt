package com.example.teamup


import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.teamup.ui.DialogFragment

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

//        val displayMetrics = resources.displayMetrics

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