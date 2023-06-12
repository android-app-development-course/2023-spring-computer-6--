package com.example.teamup.ViewPagerUI.team

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
import com.example.teamup.LoginActivity
import com.example.teamup.ViewPagerUI.home.HomeAdapter
import java.io.FileDescriptor
import java.io.PrintWriter

class TeamLeaderFragment : Fragment() {
    private lateinit var recView: RecyclerView
    private var itemList = mutableListOf<TeamInfo>()
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
        requireActivity().intent.getStringExtra("id") ?. let {
            if(it == UserID && UserID != "")
                return // 假设没有变化，则不会进行操作
            else {
                UserID = it
                refresh()
            }
        }
    }

    private  fun refresh(){
        var tmpList = mutableListOf<String>()
        // 查询数据 获得用户名 userName
        val query = BmobQuery<User>()
        // 执行查询  当前登录人员的 leadTeam 的 ID
        if(UserID != "-1") {
            query.getObject(UserID, object : QueryListener<User>() {
                override fun done(person: User?, e: BmobException?) {
                    if (e == null) { // 查询成功
                        if (person != null) { // 查询到了符合条件的数据
                            tmpList += person.leadTeam?.toList()!!
                        }
                    } else { // 查询失败，处理异常
                        Log.d("TAG", e.toString())
                    }
                }
            })
            // 作为队员的团队 查询信息
            for(JoinTeamID in tmpList){
                val Joinquery = BmobQuery<TeamInfo>()
                Joinquery.getObject(JoinTeamID, object : QueryListener<TeamInfo>() {
                    override fun done(team: TeamInfo?, e: BmobException?) {
                        if (e == null) { // 查询成功
                            if (team != null) { // 查询到了符合条件的数据
                                itemList.add(team)
                            }
                        } else { // 查询失败，处理异常
                            Log.d("TAG", e.toString())
                        }
                    }
                })
            }
        }

        if(itemList.isNotEmpty()) {
            // 绑定视图
            val adapter = TeamLeaderAdapter(itemList)
            recView.adapter = adapter
            recView.layoutManager = LinearLayoutManager(requireActivity()) // 线性布局
        }
    }
}