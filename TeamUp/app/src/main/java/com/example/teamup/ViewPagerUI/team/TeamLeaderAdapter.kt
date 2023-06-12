package com.example.teamup.ViewPagerUI.team

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teamup.R
import com.example.teamup.DataClass.TeamInfo

class TeamAdapter(private val itemList: List<TeamInfo>) : RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

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
        private val teamInfoView: TextView = itemView.findViewById(R.id.create_team_info)
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
                val view = LayoutInflater.from(itemView.context).inflate(R.layout.dialog_join_team, null)
                dialog.setContentView(view)

                // 设置对话框标题
                val dialogTitle = view.findViewById<TextView>(R.id.join_team_title)
                dialogTitle.text = item.competitionName

                // 设置简介内容
                val dialogInfo = view.findViewById<TextView>(R.id.join_team_info)
                dialogInfo.text = item.information

                // 加入按钮 监听事件
                val btnJoinTeam = view.findViewById<Button>(R.id.btnJoinTeam)
                btnJoinTeam.setOnClickListener {

                }

                dialog.show()
            }
        }

        //        布局与数据的绑定，设置对应布局的文本
        @SuppressLint("SetTextI18n")
        fun bind(item: TeamInfo) {
            titleView.text = item.competitionName
            teamInfoView.text = item.information
            dateView.text = item.deadline
            attendeeView.text = item.curNum.toString()  + '/' + item.expectedNum.toString()
            leaderNameView.text = item.leader.userName
            leaderSchoolView.text = item.leader.university
            leaderMajorView.text = item.leader.major
        }
    }
}