package com.example.teamup

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet


class RoundImageView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    androidx.appcompat.widget.AppCompatImageView(context!!, attrs, defStyleAttr) {
    //圆角大小，默认为10
    private val mBorderRadius = 500
    private val mPaint: Paint

    // 3x3 矩阵，主要用于缩小放大
    private val mMatrix: Matrix

    //渲染图像，使用图像为绘制图形着色
    private var mBitmapShader: BitmapShader? = null

    constructor(context: Context?) : this(context, null) {}
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0) {}

    init {
        mMatrix = Matrix()
        mPaint = Paint()
        mPaint.setAntiAlias(true)
    }

    protected override fun onDraw(canvas: Canvas) {
        if (getDrawable() == null) {
            return
        }
        val bitmap = drawableToBitamp(getDrawable())
        mBitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        var scale = 1.0f
        if (!(bitmap.width == getWidth() && bitmap.height == getHeight())) {
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
            scale = Math.max(
                getWidth() * 1.0f / bitmap.width,
                getHeight() * 1.0f / bitmap.height
            )
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale)
        // 设置变换矩阵
        mBitmapShader!!.setLocalMatrix(mMatrix)
        // 设置shader
        mPaint.setShader(mBitmapShader)
        canvas.drawRoundRect(
            RectF(0f, 0f, getWidth().toFloat(), getHeight().toFloat()),
            mBorderRadius.toFloat(),
            mBorderRadius.toFloat(),
            mPaint
        )
    }

    private fun drawableToBitamp(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        // 当设置不为图片，为颜色时，获取的drawable宽高会有问题，所有当为颜色时候获取控件的宽高
        val w = if (drawable.intrinsicWidth <= 0) getWidth() else drawable.intrinsicWidth
        val h = if (drawable.intrinsicHeight <= 0) getHeight() else drawable.intrinsicHeight
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        drawable.draw(canvas)
        return bitmap
    }
}