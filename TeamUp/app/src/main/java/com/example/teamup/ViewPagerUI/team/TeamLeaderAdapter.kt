package com.example.teamup.ViewPagerUI.team

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import com.example.teamup.R
import com.example.teamup.DataClass.TeamInfo
import com.example.teamup.DataClass.User
import com.example.teamup.MainActivity

class TeamLeaderAdapter(private val itemList: List<TeamInfo>) : RecyclerView.Adapter<TeamLeaderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_team, parent, false)
        return ViewHolder(view)
    }

    //    设置 RecyclerView 中每个位置的视图的数据。
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size


    @SuppressLint("InflateParams")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.CompetitionName)
        private val dateView: TextView = itemView.findViewById(R.id.team_deadline)
        private val attendeeView: TextView = itemView.findViewById(R.id.team_attendees)
        private val leaderNameView: TextView = itemView.findViewById(R.id.LeaderName)
        private val leaderSchoolView: TextView = itemView.findViewById(R.id.LeaderUniversity)
        private val leaderMajorView: TextView = itemView.findViewById(R.id.LeaderMajor)

        init {
            // 点击 RecyclerView 中按钮后 显示对话框
            itemView.setOnClickListener {
                // 获取item的数据
                val item = itemList[adapterPosition]

                // 打开对话框
                val dialog = Dialog(itemView.context)
                val view = LayoutInflater.from(itemView.context).inflate(R.layout.dialog_team_leader, null)
                dialog.setContentView(view)

                // 设置对话框标题
                val dialogTitle = view.findViewById<TextView>(R.id.leader_team_title)
                dialogTitle.text = item.competitionName

                // 设置队员列表
                val dialogMembers = view.findViewById<RecyclerView>(R.id.lead_team_member_recyview)
                item.members?.let { dialogMembers.adapter = DialogLeaderAdapter(it) }

                dialogMembers.layoutManager = LinearLayoutManager(MainActivity()) // 线性布局
                dialog.show()
            }
        }

        //        布局与数据的绑定，设置对应布局的文本
        @SuppressLint("SetTextI18n")
        fun bind(item: TeamInfo) {

            // 查询数据库 获得 UserName
            val LeaderDetailQuery = BmobQuery<User>()
            LeaderDetailQuery.getObject(item.leader, object : QueryListener<User>() {
                override fun done(UserItem: User?, e: BmobException?) {
                    if (e == null && UserItem != null) {
                        titleView.text = item.competitionName
                        dateView.text = item.deadline
                        attendeeView.text = item.curNum.toString()  + '/' + item.expectedNum.toString()
                        leaderNameView.text = UserItem.userName// 设置 用户名
                        leaderSchoolView.text = UserItem.university// 设置 学校
                        leaderMajorView.text = UserItem.major// 设置 专业
                    }
                }
            })

        }
    }
}