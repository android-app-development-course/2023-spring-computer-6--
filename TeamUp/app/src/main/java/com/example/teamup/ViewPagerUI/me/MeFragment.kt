package com.example.teamup.ViewPagerUI.me

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.teamup.LoginActivity
import com.example.teamup.R
import com.example.teamup.databinding.FragmentMeBinding

class MeFragment : Fragment() {


    private var _binding: FragmentMeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(MeViewModel::class.java)

        _binding = FragmentMeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var menuJudge=false

//        val textView: TextView = binding.textAbc
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text =it
//        }


        //波浪升起
        binding.userinfoWave.startAnimation(
            AnimationUtils.loadAnimation(
                context,R.anim.wave_up
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

        binding.userinfoUsername.setOnClickListener {

            val intent=  Intent(activity,LoginActivity::class.java) //用于跳转
            startActivity(intent)

            activity?.overridePendingTransition(R.anim.slide_f_b,R.anim.slide_t_t)
            activity?.finish()
        }

        binding.userinfoMenu.setOnClickListener {

            if(menuJudge==false){
                binding.userinfoMenuBox.startAnimation(AnimationUtils.loadAnimation(context,R.anim.userinfo_menu_f_b))
                menuJudge=true
                binding.userinfoMenuBox.visibility= View.VISIBLE

            }
            else{
                binding.userinfoMenuBox.startAnimation(AnimationUtils.loadAnimation(context,R.anim.userinfo_menu_t_b))
                menuJudge=false
                binding.userinfoMenuBox.visibility= View.INVISIBLE
            }

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}