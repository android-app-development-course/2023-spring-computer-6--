package com.example.teamup.ViewPagerUI.team

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teamup.DataClass.User
import com.example.teamup.R

class DialogLeaderAdapter(private val itemList: List<User>) : RecyclerView.Adapter<DialogLeaderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dialog_leader, parent, false)
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
        val itemUserName = itemView.findViewById<TextView>(R.id.MemberName_Leader)
        val itemUniversity = itemView.findViewById<TextView>(R.id.MemberUniversity_Leader)
        val itemMajor = itemView.findViewById<TextView>(R.id.MemberMajor_Leader)
        val btnJoinTeam = itemView.findViewById<Button>(R.id.btnKickOut)

        //        布局与数据的绑定，设置对应布局的文本
        @SuppressLint("SetTextI18n")
        fun bind(item: User) {

            // 设置 用户名
            itemUserName.text = item.userName
            // 设置 学校
            itemUniversity.text = item.university
            // 设置 专业
            itemMajor.text = item.major
            // 请出按钮 监听事件
            btnJoinTeam.setOnClickListener {

            }
        }
    }
}