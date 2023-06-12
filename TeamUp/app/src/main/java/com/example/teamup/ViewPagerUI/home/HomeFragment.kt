package com.example.teamup.ViewPagerUI.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamup.R
import com.example.teamup.DataClass.TeamInfo
import com.example.teamup.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var itemList = ArrayList<TeamInfo>()
    private lateinit var recView: RecyclerView
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        recView = view.findViewById(R.id.home_recy_view)
        val adapter = HomeAdapter(itemList)
        recView.adapter = adapter
        recView.layoutManager = LinearLayoutManager(activity) // 线性布局
    }

    override fun onResume() {
        super.onResume()
//        Toast.makeText(context,"1asas",Toast.LENGTH_SHORT).show()


    }

    private  fun initList(){
//        repeat(20)
//        { itemList.add(
//            TeamInfo(   1,
//                "标题",
//                "简介",
//                "日期",
//                "时间",
//                "",
//                0,
//                0)
//        ) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}