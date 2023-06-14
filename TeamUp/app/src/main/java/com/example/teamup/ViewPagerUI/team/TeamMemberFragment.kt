package com.example.teamup.ViewPagerUI.team

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import com.example.teamup.R
import com.example.teamup.DataClass.TeamInfo
import com.example.teamup.DataClass.User
import com.example.teamup.DialogUnLogin
import com.example.teamup.LoginActivity
import com.example.teamup.ViewPagerUI.home.HomeAdapter
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.CountDownLatch

class TeamMemberFragment(
    private val searchView: EditText,
    private val btnSearch: ImageButton
) : Fragment() {
    private lateinit var recView: RecyclerView
    private var teamIdList = listOf<String>()
    private var UserID = "-1"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_member, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recView = view.findViewById(R.id.team_member_recyview)
    }

    private fun query(searchText:String) {
        val query = BmobQuery<TeamInfo>()
        val queryTeam = mutableListOf<String>()
        query.findObjects(object : FindListener<TeamInfo>() {
            override fun done(teams: MutableList<TeamInfo>?, e: BmobException?) {
                if (e == null && teams != null) {
                    for(team in teams){
                        if(team.isMember(UserID)
                            &&team.competitionName?.contains(searchText) == true){
                            queryTeam.add(team.objectId)
                            Log.d("MyDebug_Query_team","$team")
                        }
                    }
                    // 查询成功，更新 RecyclerView 的适配器
                    val adapter = TeamMemberAdapter(queryTeam, activity!!, UserID)
                    recView.adapter = adapter
                    recView.layoutManager = LinearLayoutManager(activity!!) // 线性布局
                } else { // 查询失败，处理异常
                    Log.e("ErrorTAG", e.toString())
                }
            }
        })
    }

    //  每次显示页面 判断是否需要刷新
    override fun onResume() {
        super.onResume()
        // 读取内存
        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("LoginUserInfo", Context.MODE_PRIVATE)
        var itUserID = sharedPreferences.getString("id","-1")

        if (itUserID != null) {
            UserID = itUserID
            btnSearch.setOnClickListener{
//            如果没有登录则打开登录提示
                if(UserID == "-1") {
                    DialogUnLogin(requireActivity()).show()
                }else{
                    val searchText = searchView.text.toString()
                    query(searchText)
                }
            }
            refresh()
        }
//        if(itUserID == UserID) // 当前用户 没有变化
//            return
//        else if (itUserID != null && itUserID != "-1") {// 已登录
//            UserID = itUserID
//            refresh()
//        } else{// 当前没有登录，则
//        }
    }

    private  fun refresh(){
        val query = BmobQuery<User>() // 查询数据 获得当前登录人员的 joinTeam
        if(UserID != "-1") {
            query.getObject(UserID, object : QueryListener<User>() {
                override fun done(person: User?, e: BmobException?) {
                    if (e == null) { // 查询成功
                        if (person != null) { // 查询到了符合条件的数据
                            teamIdList = person.joinTeam?.toList()!!
                            // 绑定视图
                            val adapter = TeamMemberAdapter(teamIdList, activity!!, UserID)
                            recView.adapter = adapter
                            recView.layoutManager = LinearLayoutManager(requireActivity()) // 线性布局

                        }
                    } else { // 查询失败，处理异常
                        Log.d("TAG", e.toString())
                    }
                }
            })
        }

    }
}