package com.example.teamup.ViewPagerUI.home

import android.content.Context.MODE_APPEND
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import com.example.teamup.DataClass.TeamInfo
import com.example.teamup.DataClass.User
import com.example.teamup.R
import com.example.teamup.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private var UserID = "-1"

    private lateinit var recView: RecyclerView
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recView = view.findViewById(R.id.home_recy_view)
    }
//  每次显示页面 刷新
    override fun onResume() {
        super.onResume()
    // 读取内存
        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("LoginUserInfo", MODE_PRIVATE)
        var itUserID = sharedPreferences.getString("id","-1")

    if (itUserID != null) {
        UserID = itUserID
        if(itUserID != "-1")
            refresh()
        else
            showAll()
    }
//
//        if(itUserID == UserID)
//            return // 当前用户 没有变化
//        else if (itUserID != null && itUserID != "-1") {// 已登录
//            UserID = itUserID
//            refresh()
//        } else{// 当前没有登录，则 显示所有队伍
//            showAll()
//        }
    }

    private fun showAll() {
        val query = BmobQuery<TeamInfo>()
        query.findObjects(object : FindListener<TeamInfo>() {
            override fun done(teams: MutableList<TeamInfo>?, e: BmobException?) {
                if (e == null && teams != null && teams.isNotEmpty()) { // 查询到了符合条件的数据
                    // 绑定视图
                    val adapter = HomeAdapter(teams, UserID, activity!!, this@HomeFragment)
                    recView.adapter = adapter
                    recView.layoutManager = LinearLayoutManager(activity) // 线性布局

                } else { // 查询失败，处理异常
                    Log.e("ErrorTAG", e.toString())
                }
            }
        })
    }

    fun refresh(){
        if(UserID != "-1") {
            val query = BmobQuery<TeamInfo>()
            query.findObjects(object : FindListener<TeamInfo>() {
                override fun done(teams: MutableList<TeamInfo>?, e: BmobException?) {
                    if (e == null && teams != null && teams.isNotEmpty()) { // 查询到了符合条件的数据
                        var itemList = mutableListOf<TeamInfo>()
                        for (team in teams){
//                            判断条件：如果 members不包含UserID 且 leader 不是 UserID 且 没有满足人数
                            if(!team.isMember(UserID) && !team.isLeader(UserID) && !team.isFull()){
                                itemList.add(team)
                            }
                        }
                        // 绑定视图
                        val adapter = HomeAdapter(itemList, UserID, activity!!, this@HomeFragment)
                        recView.adapter = adapter
                        recView.layoutManager = LinearLayoutManager(activity) // 线性布局

                    } else { // 查询失败，处理异常
                        Log.e("ErrorTAG", e.toString())
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}