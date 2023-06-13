package com.example.teamup.ViewPagerUI.team

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener
import com.example.teamup.DataClass.TeamInfo
import com.example.teamup.DataClass.User
import com.example.teamup.R

class DialogLeaderAdapter(
    private val userIdList: Array<String>,
    private val teamId: String,
    private val activity: Activity
    ) : RecyclerView.Adapter<DialogLeaderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dialog_leader, parent, false)
        return ViewHolder(view)
    }

    //    设置 RecyclerView 中每个位置的视图的数据。
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userId = userIdList[position]
        // 通过 String account 查询得到 User
        holder.bind(userId)
    }

    override fun getItemCount(): Int = userIdList.size


    @SuppressLint("InflateParams")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemUserName = itemView.findViewById<TextView>(R.id.MemberName_Leader)
        val itemUniversity = itemView.findViewById<TextView>(R.id.MemberUniversity_Leader)
        val itemMajor = itemView.findViewById<TextView>(R.id.MemberMajor_Leader)
        val btnKickOut = itemView.findViewById<Button>(R.id.btnKickOut)

        //        布局与数据的绑定，设置对应布局的文本
        @SuppressLint("SetTextI18n")
        fun bind(userId: String) {
            // 查询数据库 获得 UserName
            val LeaderDetailQuery = BmobQuery<User>()
            LeaderDetailQuery.getObject(userId, object : QueryListener<User>() {
                override fun done(UserItem: User?, e: BmobException?) {
                    if (e == null && UserItem != null) {
                        itemUserName.text = UserItem.userName// 设置 用户名
                        itemUniversity.text = UserItem.university// 设置 学校
                        itemMajor.text = UserItem.major// 设置 专业
                        // 请出按钮 监听事件
                        btnKickOut.setOnClickListener {
                            updateData(userId,teamId)

                        }

                    }
                }
            })

        }

        private fun updateData(userId: String, teamId: String) {
            Log.d("myDebug","点击请出队员团队按钮")
            // 1. 更新团队成员 teamInfo.members
            val teamQuery = BmobQuery<TeamInfo>()
            teamQuery.getObject(teamId,object : QueryListener<TeamInfo>(){
                override fun done(newTeam: TeamInfo, e: BmobException?) {
                    if (e == null && newTeam!=null) {
                        //  进行更新操作
                        newTeam.removeAll("members", listOf (userId))
                        newTeam.update(object : UpdateListener() {
                            override fun done(e: BmobException?) {
                                if (e == null) { // 更新成功
                                    // 2. 更新个人的团队 User.joinTeam
                                    val userQuery = BmobQuery<User>()
                                    userQuery.getObject(userId,object : QueryListener<User>(){
                                        override fun done(newUser: User?, e: BmobException?) {
                                            if(e==null&&newUser!=null) {
                                                newUser.removeAll("joinTeam", listOf(teamId))
                                                newUser.update(object : UpdateListener() {
                                                    override fun done(e: BmobException?) {
                                                        if (e == null) {
                                                            Log.d("MyDebug teamId userId","$teamId $userId")
                                                            Toast.makeText(
                                                                activity,
                                                                "请出队员成功",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    }
                                                })
                                            }
                                        }
                                    })

                                } else { // 更新失败
                                    Toast.makeText(
                                        activity,
                                        "请出队员失败。",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        })

                    } else {
                        // 处理查询异常
                    }
                }
            })

        }
    }
}