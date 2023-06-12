package com.example.teamup.ViewPagerUI.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import com.example.teamup.DataClass.TeamInfo
import com.example.teamup.DataClass.User
import com.example.teamup.R

class HomeAdapter(private val itemList: List<TeamInfo>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

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

        init {  // 点击 RecyclerView 中按钮后 显示对话框
            itemView.setOnClickListener {  // 获取item的数据
                val item = itemList[adapterPosition]

                // 打开对话框
                val dialog = Dialog(itemView.context)
                val view = LayoutInflater.from(itemView.context).inflate(R.layout.dialog_join_team, null)
                dialog.window?.setLayout(1200, 800) // 设置宽1200dp 高800dp
                dialog.setContentView(view)

                // 设置对话框标题
                view.findViewById<TextView>(R.id.join_team_title).text = item.competitionName
                // 设置简介内容
                view.findViewById<TextView>(R.id.join_team_info).text = item.information
                // 加入按钮 监听事件
                val btnJoinTeam = view.findViewById<Button>(R.id.btnJoinTeam)
                btnJoinTeam.setOnClickListener {
                    Log.d("myDebug","加入按钮 监听事件")
                }
                // 调整 Dialog 宽高
                val params = dialog.window?.attributes
                val width = params?.width ?: 0
                val height = params?.height ?: 0
                view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
                view.layout(0, 0, width , height)

                dialog.show()
            }
        }

//        布局与数据的绑定，设置对应布局的文本
        @SuppressLint("SetTextI18n")
        fun bind(item: TeamInfo) {
        // 查询数据库获得 队长的信息
            val LeaderDetailQuery = BmobQuery<User>()
            LeaderDetailQuery.getObject( item.leader, object : QueryListener<User>() {
                override fun done(UserItem: User?, e: BmobException?) {
                    if (e == null && UserItem != null) {
                        titleView.text = item.competitionName
                        dateView.text = item.deadline
                        attendeeView.text = item.curNum.toString() + '/' + item.expectedNum.toString()
                        leaderNameView.text = UserItem.userName
                        leaderSchoolView.text = UserItem.university
                        leaderMajorView.text = UserItem.major
                    }
                }
            })

        }
    }
}