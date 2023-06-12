package com.example.teamup.ViewPagerUI.team

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import com.example.teamup.R
import com.example.teamup.DataClass.TeamInfo
import com.example.teamup.DataClass.User
import com.example.teamup.LoginActivity

class TeamMemberFragment : Fragment() {
    private lateinit var recView: RecyclerView
    private var itemList = mutableListOf<TeamInfo>()
    private var UserID = "-1"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_member, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recView = view.findViewById(R.id.team_member_recyview)
    }

    //  每次显示页面 判断是否需要刷新
    override fun onResume() {
        super.onResume()
        requireActivity().intent.getStringExtra("id") ?. let {
            if(it == UserID && UserID != "-1")
                return // 假设没有变化，则不会进行操作
            else {
                UserID = it
                refresh()
            }
        }
    }

    private  fun refresh(){
        var tmpList = mutableListOf<String>()
        val query = BmobQuery<User>() // 查询数据 获得当前登录人员的 joinTeam
        if(UserID != "-1") {
            query.getObject(UserID, object : QueryListener<User>() {
                override fun done(person: User?, e: BmobException?) {
                    if (e == null) { // 查询成功
                        if (person != null) { // 查询到了符合条件的数据
                            tmpList += person.joinTeam?.toList()!!
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

    private  fun initList(){
//        val user1 = User("Alice", "alice123", "password", "Harvard University", "Computer Science", "female", "I am a software engineer.", listOf(), listOf())
//        val user2 = User("Bob", "bob456", "password", "MIT", "Electrical Engineering", "male", "I am a robotics enthusiast.", listOf(), listOf())
//        val user3 = User("Charlie", "charlie789", "password", "Stanford University", "Mathematics", "male", "I am a data analyst.", listOf(), listOf())
//
//        val team1 = TeamInfo(user1, "Hackathon 2022", "We are a team of passionate coders.", "2022-08-31", 5, 3, listOf(user1, user2, user3))
//        val team2 = TeamInfo(user2, "Robotics Competition 2022", "We are building a robot for the competition.", "2022-09-30", 4, 2, listOf(user2, user3))
//        itemList.add(team2)
//        itemList.add(team1)
    }

}