package com.example.teamup.ViewPagerUI.team


import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener
import com.example.teamup.R
import com.example.teamup.DataClass.TeamInfo
import com.example.teamup.DataClass.User
import com.example.teamup.MainActivity

class TeamMemberAdapter(
    private val itemIdList: List<String>,
    private val activity: Activity,
    private val userId: String
) : RecyclerView.Adapter<TeamMemberAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_team, parent, false)
        return ViewHolder(view)
    }

    //    设置 RecyclerView 中每个位置的视图的数据。
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemIdList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemIdList.size


    @SuppressLint("InflateParams")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.CompetitionName)
        private val dateView: TextView = itemView.findViewById(R.id.team_deadline)
        private val attendeeView: TextView = itemView.findViewById(R.id.team_attendees)
        private val leaderNameView: TextView = itemView.findViewById(R.id.LeaderName)
        private val leaderSchoolView: TextView = itemView.findViewById(R.id.LeaderUniversity)
        private val leaderMajorView: TextView = itemView.findViewById(R.id.LeaderMajor)
        init {
            // 点击 RecyclerView.item 中的按钮后 显示对话框
            itemView.setOnClickListener {
                // 获取item的数据
                val itemId = itemIdList[adapterPosition]

                // 根据teamInfo.objectID 查询获得团队信息
                val query = BmobQuery<TeamInfo>()
                query.getObject(itemId, object : QueryListener<TeamInfo>() {
                    override fun done(team: TeamInfo?, e: BmobException?) {
                        if (e == null && team != null) { // 查询到了符合条件的数据
                            showDialog(team)
                            Log.d("MyDebug_Team", team.toString())
                        } else { // 查询失败，处理异常
                            Log.d("TAG", e.toString())
                        }
                    }
                })
            }
        }

        private fun showDialog(team: TeamInfo) {
            // 打开对话框
            val dialog = Dialog(itemView.context)
            val view = LayoutInflater.from(itemView.context).inflate(R.layout.dialog_team_member, null)
            dialog.setContentView(view)

            // 设置对话框标题
            val dialogTitle = view.findViewById<TextView>(R.id.team_member_title)
            dialogTitle.text = team.competitionName

            // 设置简介内容
            val dialogMembers = view.findViewById<RecyclerView>(R.id.team_member_recyview)
            team.members?.let { dialogMembers.adapter = DialogMemberAdapter(it) }
            dialogMembers.layoutManager = LinearLayoutManager(MainActivity()) // 线性布局

            // 退出团队 按钮监听事件
            val btnExitTeam = view.findViewById<Button>(R.id.btnExitTeam)
            btnExitTeam.setOnClickListener {
                Log.d("myDebug","点击退出团队按钮")
                updateData(team)
            }

            dialog.show()
        }

        private fun updateData(team: TeamInfo) {
            // 1. 更新团队成员 teamInfo.members
            val teamQuery = BmobQuery<TeamInfo>()
            teamQuery.getObject(team.objectId,object : QueryListener<TeamInfo>(){
                override fun done(newTeam: TeamInfo, e: BmobException?) {
                    newTeam.removeAll("members", listOf(userId))
                    newTeam.update(object : UpdateListener() {
                        override fun done(e: BmobException?) {
                            if (e == null) { // 更新成功
                                // 2. 更新个人的团队 User.joinTeam
                                val userQuery = BmobQuery<User>()
                                userQuery.getObject(userId,object : QueryListener<User>(){
                                    override fun done(newUser: User?, e: BmobException?) {
                                        if(e==null&&newUser!=null) {
                                            newUser.objectId = userId
                                            newUser.removeAll("joinTeam", listOf(team.objectId))
                                            newUser.update(object : UpdateListener(){
                                                override fun done(e: BmobException?) {
                                                    if(e == null){
                                                        Toast.makeText(
                                                            activity,
                                                            "退出团队成功",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    } else { // 更新失败
                                                        Toast.makeText(
                                                            activity,
                                                            "退出团队失败。",
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
                                    "退出团队失败。",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })

                }
            })
        }


        //        布局与数据的绑定，设置对应布局的文本
        @SuppressLint("SetTextI18n")
        fun bind(itemId: String) {
            var item: TeamInfo
            // 根据teamInfo.objectID 查询获得团队信息
            val query = BmobQuery<TeamInfo>()
            query.getObject(itemId, object : QueryListener<TeamInfo>() {
                override fun done(team: TeamInfo?, e: BmobException?) {
                    if (e == null && team != null) { // 查询到了符合条件的数据
                        item = team
                        Log.d("MyDebug_Team", team.toString())

                        // 查询数据库 获得 UserName
                        val LeaderDetailQuery = BmobQuery<User>()
                        LeaderDetailQuery.getObject(item.leader, object : QueryListener<User>() {
                            override fun done(UserItem: User?, e: BmobException?) {
                                if (e == null && UserItem != null) {
                                    titleView.text = item.competitionName
                                    dateView.text = item.deadline
                                    attendeeView.text = "${item.curNum()}/${item.expectedNum}"
                                    leaderNameView.text = UserItem.userName// 设置 用户名
                                    leaderSchoolView.text = UserItem.university// 设置 学校
                                    leaderMajorView.text = UserItem.major// 设置 专业
                                }
                            }
                        })
                    } else { // 查询失败，处理异常
                        Log.d("TAG", e.toString())
                    }
                }
            })
        }
    }
}