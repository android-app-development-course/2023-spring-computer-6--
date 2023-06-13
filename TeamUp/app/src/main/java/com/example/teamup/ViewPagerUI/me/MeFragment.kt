package com.example.teamup.ViewPagerUI.me

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.example.teamup.LoginActivity
import com.example.teamup.R
import com.example.teamup.DataClass.User
import com.example.teamup.databinding.FragmentMeBinding
import com.example.teamup.InfoDialogFragment
import com.example.teamup.PasswordDialogFragment


class MeFragment : Fragment() {


    private var _binding: FragmentMeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(MeViewModel::class.java)

        _binding = FragmentMeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var menuJudge=false //是否点击菜单






        //波浪升起
        binding.userinfoWave.startAnimation(
            AnimationUtils.loadAnimation(
                context,R.anim.wave_up
            ))

        binding.userinfoWave2.startAnimation(
            AnimationUtils.loadAnimation(
                context,R.anim.wave_up2
            ))

        //字体
        var face= Typeface.createFromAsset(activity?.assets  ,"MaoKenShiJinHei-2.ttf")
        binding.userinfoUsername.setTypeface(face)
        binding.userinfoAccountT.setTypeface(face)
        binding.userinfoAccountC.setTypeface(face)
        binding.userinfoGenderT.setTypeface(face)
        binding.userinfoGenderC.setTypeface(face)
        binding.userinfoMajorC.setTypeface(face)
        binding.userinfoMajorT.setTypeface(face)
        binding.userinfoResumeC.setTypeface(face)
        binding.userinfoResumeT.setTypeface(face)
        binding.userinfoSchoolC.setTypeface(face)
        binding.userinfoSchoolT.setTypeface(face)
        binding.userinfoMenuCodeC.setTypeface(face)
        binding.userinfoMenuInfoC.setTypeface(face)
        binding.userinfoMenuLogoutC.setTypeface(face)



        binding.userinfoUsername.setOnClickListener {

            //如果未登录
            val userId = requireActivity().intent.getStringExtra("id")
            if (userId=="-1"){
                val intent=  Intent(activity,LoginActivity::class.java) //用于跳转
                startActivity(intent)

                activity?.overridePendingTransition(R.anim.slide_f_b,R.anim.slide_t_t)
                activity?.finish()
            }

        }


        binding.userinfoMenu.setOnClickListener {

            if(menuJudge==false){
                binding.userinfoMenuBox.startAnimation(AnimationUtils.loadAnimation(context,R.anim.userinfo_menu_f_b))
                menuJudge=true
                binding.userinfoMenuBox.visibility= View.VISIBLE

//                binding.userinfoMenuInfoC.visibility=View.VISIBLE
                binding.userinfoMenuInfoC.isClickable=true
                binding.userinfoMenuCodeC.isClickable=true

            }
            else {
                binding.userinfoMenuBox.startAnimation(AnimationUtils.loadAnimation(context,R.anim.userinfo_menu_t_b))
                menuJudge=false
                binding.userinfoMenuBox.visibility= View.INVISIBLE

                binding.userinfoMenuInfoC.isClickable=false
                binding.userinfoMenuCodeC.isClickable=false

            }

        }

        //点击其他地方菜单关闭
        binding.userinfoLayoutBox.setOnClickListener {
            if(menuJudge){
                binding.userinfoMenuBox.startAnimation(AnimationUtils.loadAnimation(context,R.anim.userinfo_menu_t_b))
                menuJudge=false
                binding.userinfoMenuBox.visibility= View.INVISIBLE

                binding.userinfoMenuInfoC.isClickable=false
                binding.userinfoMenuCodeC.isClickable=false
            }

        }




        //修改信息
        binding.userinfoMenuInfoC.setOnClickListener {

            val userId = requireActivity().intent.getStringExtra("id")
            if(userId=="-1"){
                Toast.makeText(context,"请先登录",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"修改信息",Toast.LENGTH_SHORT).show()
                InfoDialogFragment().show(parentFragmentManager,"InfoDialogFragment")

            }



        }
        //更改密码
        binding.userinfoMenuCodeC.setOnClickListener {
            val userId = requireActivity().intent.getStringExtra("id")

            if(userId=="-1"){
                Toast.makeText(context,"请先登录",Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "更改密码", Toast.LENGTH_SHORT).show()
                PasswordDialogFragment().show(parentFragmentManager, "PasswordDialogFragment")
            }
        }

        //退出登录
        binding.userinfoMenuLogoutC.setOnClickListener {
            val userId = requireActivity().intent.getStringExtra("id")

            if(userId=="-1"){
                Toast.makeText(context,"请先登录",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"退出登录",Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)

                val activity: Activity? = activity
                activity?.finish()
            }



        }




        //设置简介里的滚动条
        binding.userinfoResumeC.movementMethod=ScrollingMovementMethod.getInstance()



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()

        val userId = requireActivity().intent.getStringExtra("id")
//        Toast.makeText(context,"Id为："+userId,Toast.LENGTH_LONG).show()

        if(userId!="-1"){
            val query = BmobQuery<User>()

            // 添加查询条件
            query.addWhereEqualTo("objectId", userId)

            // 执行查询，并在回调函数中处理查询结果
            query.findObjects(object : FindListener<User>() {
                override fun done(persons: MutableList<User>?, e: BmobException?) {
                    if (e == null) {
                        // 查询成功，处理查询结果
                        if (persons != null && persons.size == 1) {
                            binding.userinfoUsername.setText(persons[0].userName)
                            binding.userinfoAccountC.setText(persons[0].account)
                            binding.userinfoSchoolC.setText(persons[0].university)
                            binding.userinfoMajorC.setText(persons[0].major)
                            binding.userinfoGenderC.setText(persons[0].gender)
                            binding.userinfoResumeC.setText(persons[0].resume)



                        } else {
                            // 没有查询到符合条件的数据(不可达)

                        }
                    } else {
                        Toast.makeText(context,"用户信息获取失败，请确定是否联网：" + e.message,Toast.LENGTH_SHORT).show()

                    }
                }
            })
        }






    }
}