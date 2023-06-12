package com.example.teamup.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamup.R
import com.example.teamup.dataClass.TeamInfo
import com.example.teamup.adapter.TeamAdapter

class TeamLeaderFragment : Fragment() {
    private var itemList = ArrayList<TeamInfo>()
    private lateinit var recView: RecyclerView
//   应用布局
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_leader, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Toast.makeText(activity, "11111", Toast.LENGTH_SHORT).show()

        initList()
        recView = view.findViewById<RecyclerView>(R.id.team_leader_recyview)
        val adapter = TeamAdapter(itemList)
        recView.adapter = adapter
        recView.layoutManager = LinearLayoutManager(activity) // 线性布局
    }

    private  fun initList(){
//        repeat(20)
//        { itemList.add(
//            Team(1,
//                "标题",
//                "简介",
//                "日期",
//                "时间",
//                "",
//                R.mipmap.teamup_logo,
//                0,
//                0)
//        ) }
    }
}