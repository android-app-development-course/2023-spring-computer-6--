package com.example.teamup.ViewPagerUI.team

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import com.example.teamup.R
import com.example.teamup.DataClass.TeamInfo
import com.example.teamup.DataClass.User
import com.google.android.gms.tasks.Continuation
import kotlinx.coroutines.*
import kotlin.coroutines.suspendCoroutine

class TeamLeaderFragment : Fragment() {
    private lateinit var recView: RecyclerView
    private var itemIdList = listOf<String>()
    private var UserID = "-1"
//   应用布局
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_leader, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recView = view.findViewById(R.id.team_leader_recyview)
    }

    override fun onResume() {
        super.onResume()
        // 读取内存
        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("LoginUserInfo", Context.MODE_PRIVATE)
        var itUserID = sharedPreferences.getString("id","-1")

        if (itUserID != null) {
            UserID = itUserID
            refresh()
        }
//        if(itUserID == UserID)
//            return // 当前用户 没有变化
//        else if (itUserID != null && itUserID != "-1") {// 已登录
//        UserID = itUserID
//        refresh()
//        } else{// 当前没有登录，则
//        }
    }

    private fun refresh() {
//      1. 查询数据 UserID.leadTeam
        val query = BmobQuery<User>()
        query.getObject(UserID, object : QueryListener<User>() {
            override fun done(person: User?, e: BmobException?) {
                if (e == null && person != null) { // 查询到了符合条件的数据
                    itemIdList = person.leadTeam?.toList()!!
                    val adapter = TeamLeaderAdapter(itemIdList, activity!!)
                    recView.adapter = adapter
                    recView.layoutManager = LinearLayoutManager(requireActivity()) // 线性布局
                } else { // 查询失败，处理异常
                    Log.d("TAG", e.toString())
                }
            }
        })
    }
}
