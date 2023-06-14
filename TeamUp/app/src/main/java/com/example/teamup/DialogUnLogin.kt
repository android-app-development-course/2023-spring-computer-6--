package com.example.teamup

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog

class DialogUnLogin(private val context: Context) : Dialog(context) {
    private var builder = AlertDialog.Builder(context)
    private lateinit var dialog: AlertDialog

    init {
//        创建对话框
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_un_login,null)
        builder.setView(view)
            .setTitle("点击确定按钮登录")
            .setMessage("目前处于游客状态")
            .setPositiveButton("确定") { dialog, id ->
//                跳转至 登录页面
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
                this.dismiss()
            }
            .setNegativeButton("取消") { dialog, id -> dialog.cancel() }
        onCreate(null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = builder.create()
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimationStyle
    }

    // 显示对话框
    override fun show() = dialog.show()
}