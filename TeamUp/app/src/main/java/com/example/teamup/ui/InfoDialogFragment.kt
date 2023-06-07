package com.example.teamup.ui

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.example.teamup.MainActivity
import com.example.teamup.R



import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobACL
import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import com.example.teamup.User


class InfoDialogFragment : DialogFragment() {

    private val REQUEST_CODE = 1

    @SuppressLint("ResourceAsColor", "SetTextI18n", "MissingInflatedId", "CutPasteId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

//        创建对话框
        val builder = AlertDialog.Builder(requireActivity(),R.style.MyDialogStyle)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.fragment_info_dialog, null)

        builder.setView(view)


        var face= Typeface.createFromAsset(activity?.assets  ,"MaoKenShiJinHei-2.ttf")
        val title = TextView(context)
        title.text = "\n『修改信息』"
        title.setPadding(10,10,10,10)
        title.gravity = Gravity.CENTER
        title.setTextColor(Color.parseColor("#4284f0"))
        title.textSize = 30F
        title.typeface=face
        builder.setCustomTitle(title)

        //先读取数据库信息

        val userId = requireActivity().intent.getStringExtra("id")
//        Toast.makeText(context,"Id为："+userId,Toast.LENGTH_LONG).show()

        val query = BmobQuery<User>()

        // 添加查询条件
        query.addWhereEqualTo("objectId", userId)

        // 执行查询，并在回调函数中处理查询结果
        query.findObjects(object : FindListener<User>() {
            override fun done(persons: MutableList<User>?, e: BmobException?) {
                if (e == null) {
                    // 查询成功，处理查询结果
                    if (persons != null && persons.size == 1) {
                        view?.findViewById<EditText>(R.id.info_dialog_username_e)?.setText(persons[0].userName)
                        view?.findViewById<EditText>(R.id.info_dialog_university_e)?.setText(persons[0].university)
                        view?.findViewById<EditText>(R.id.info_dialog_major_e)?.setText(persons[0].major)
                        view?.findViewById<EditText>(R.id.info_dialog_gender_e)?.setText(persons[0].gender)
                        view?.findViewById<EditText>(R.id.info_dialog_resume_e)?.setText(persons[0].resume)

                    } else {
                        // 没有查询到符合条件的数据(不可达)

                    }
                } else {
                    Toast.makeText(context,"用户信息获取失败，请确定是否联网：" + e.message,Toast.LENGTH_SHORT).show()

                }
            }
        })



        //保存
        var click_num=0
        val info_dialog_save=view?.findViewById<Button>(R.id.info_dialog_save)
        info_dialog_save?.setTypeface(face)
        info_dialog_save?.setOnClickListener {

            if(click_num==0){
                click_num+=1
                Toast.makeText(context,"请再次点击保存确认修改",Toast.LENGTH_SHORT).show()
            }
            else{
                click_num=0

                //修改数据库
                val userId1 = requireActivity().intent.getStringExtra("id")

                val temp_user = User()
                temp_user.userName= view.findViewById<EditText>(R.id.info_dialog_username_e)?.text.toString()
                temp_user.university= view.findViewById<EditText>(R.id.info_dialog_university_e)?.text.toString()
                temp_user.major= view.findViewById<EditText>(R.id.info_dialog_major_e)?.text.toString()
                temp_user.gender= view.findViewById<EditText>(R.id.info_dialog_gender_e)?.text.toString()
                temp_user.resume= view.findViewById<EditText>(R.id.info_dialog_resume_e)?.text.toString()


                temp_user.update(userId1, object : UpdateListener() {
                    override fun done(e: BmobException?) {
                        if (e == null) {
                            Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show()
                            dialog?.cancel()
                        } else {
                            Toast.makeText(context,"修改失败，请确认是否联网"+e.message,Toast.LENGTH_SHORT).show()
                        }
                    }
                })

            }
        }
        //取消
        val info_dialog_cancle=view?.findViewById<Button>(R.id.info_dialog_cancle)
        info_dialog_cancle?.setTypeface(face)
        info_dialog_cancle?.setOnClickListener {
//            Toast.makeText(context,"取消",Toast.LENGTH_SHORT).show()
            dialog?.cancel()

        }


//        builder.setPositiveButton("确定") { dialog, id ->
//
//
//            info_dialog_username_e?.text.toString()
//
//
//
//
//            }
//            .setNegativeButton("取消") { dialog, id ->
//                dialog.cancel()
//            }

        val dialog = builder.create()
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimationStyle
        dialog.setCanceledOnTouchOutside(false) //点空白处不取消会话

        var window1=dialog.window
        if (window1 != null) {
            window1.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }


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