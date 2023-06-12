package com.example.teamup

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog

class DialogUnLogin : DialogFragment() {

    private val REQUEST_CODE = 1

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

//        创建对话框
        val builder = AlertDialog.Builder(requireActivity())
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_un_login,null)

        builder.setView(view)
            .setTitle("点击确定按钮登录")
            .setMessage("目前处于游客状态")
            .setPositiveButton("确定") { dialog, id ->
//                跳转至 登录页面
                val intent = Intent(activity, LoginActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE)
            }
            .setNegativeButton("取消") { dialog, id -> dialog.cancel() }

        val dialog = builder.create()
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimationStyle

        return dialog
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val resultAccount = data?.getStringExtra("resultAccount")
            Toast.makeText(MainActivity(),resultAccount,Toast.LENGTH_LONG).show()
        }
    }
}