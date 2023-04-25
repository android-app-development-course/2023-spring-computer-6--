package com.example.login_page

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator


class WaveView2(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    private var width = 0
    private var height = 0
    private var baseLine = 0 // 基线，用于控制水位上涨的，这里是写死了没动，你可以不断的设置改变。
    private var mPaint: Paint? = null
    private val waveHeight = 100 // 波浪的最高度
    private var waveWidth //波长
            = 0
    private var offset = 0f //偏移量

    init {
        initView()
    }

    /**
     * 不断的更新偏移量，并且循环。
     */
    private fun updateXControl() {
        //设置一个波长的偏移
        val mAnimator = ValueAnimator.ofFloat(0f, waveWidth.toFloat())
        mAnimator.interpolator = LinearInterpolator()
        mAnimator.addUpdateListener { animation ->
            val animatorValue = animation.animatedValue as Float
            offset = animatorValue //不断的设置偏移量，并重画
            postInvalidate()
        }
        mAnimator.duration = 11000
        mAnimator.repeatCount = ValueAnimator.INFINITE
        mAnimator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, mPaint!!)
    }

    //初始化paint
    private fun initView() {
        mPaint = Paint()
        mPaint!!.color =Color.parseColor("#ffffff")
        mPaint!!.style = Paint.Style.FILL
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        width = measuredWidth //获取屏幕宽度
        height = measuredHeight //获取屏幕高度
        waveWidth = width
        baseLine = height / 2
        updateXControl()
    }//结束点的Y
    //只需要处理完半个波长，剩下的有for循环自已就添加了。
    //下面这三句话很重要，它是形成了一封闭区间，让曲线以下的面积填充一种颜色，大家可以把这3句话注释了看看效果。
//控制点的X,（起始点X + itemWidth/2 + offset)
    //控制点的Y
    //结束点的X
//半个波长
    //起始坐标
    //核心的代码就是这里
    /**
     * 核心代码，计算path
     * @return
     */
    private val path: Path
        private get() {
            val itemWidth = waveWidth / 2 //半个波长
            val mPath = Path()
            mPath.moveTo((-itemWidth * 3).toFloat(), baseLine.toFloat()) //起始坐标
            //核心的代码就是这里
            for (i in -3..1) {
                val startX = i * itemWidth
                mPath.quadTo(
                    startX + itemWidth / 2 + offset,  //控制点的X,（起始点X + itemWidth/2 + offset)
                    getWaveHeigh(i).toFloat(),  //控制点的Y
                    startX + itemWidth + offset,  //结束点的X
                    baseLine //结束点的Y
                        .toFloat()
                ) //只需要处理完半个波长，剩下的有for循环自已就添加了。
            }
            //下面这三句话很重要，它是形成了一封闭区间，让曲线以下的面积填充一种颜色，大家可以把这3句话注释了看看效果。
            mPath.lineTo(width.toFloat(), height.toFloat())
            mPath.lineTo(0f, height.toFloat())
            mPath.close()
            return mPath
        }

    //奇数峰值是正的，偶数峰值是负数
    private fun getWaveHeigh(num: Int): Int {
        return if (num % 2 == 0) {
            baseLine + waveHeight
        } else baseLine - waveHeight
    }
}