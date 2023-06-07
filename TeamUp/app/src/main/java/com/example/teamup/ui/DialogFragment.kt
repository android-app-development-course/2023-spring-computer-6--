package com.example.teamup.ui

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import com.example.teamup.CreateTeamActivity
import com.example.teamup.LoginActivity
import com.example.teamup.MainActivity
import com.example.teamup.R

class DialogFragment : DialogFragment() {

    private val REQUEST_CODE = 1

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

//        创建对话框
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.fragment_dialog, null)

        builder.setView(view)

        builder.setTitle("点击确定按钮登录")
            .setMessage("目前处于游客状态")
            .setPositiveButton("确定") { dialog, id ->
//                跳转至 登录页面
                val intent = Intent(activity, LoginActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE)
            }
            .setNegativeButton("取消") { dialog, id -> dialog.cancel() }

        val dialog = builder.create()
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimationStyle
        dialog.setCanceledOnTouchOutside(false) //点空白处不取消会话




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