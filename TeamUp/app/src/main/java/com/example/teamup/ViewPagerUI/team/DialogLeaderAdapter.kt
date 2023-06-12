package com.example.teamup.ViewPagerUI.team

import android.annotation.SuppressLint
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
import com.example.teamup.DataClass.User
import com.example.teamup.MainActivity
import com.example.teamup.R

class DialogLeaderAdapter(private val itemList: Array<String>) : RecyclerView.Adapter<DialogLeaderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dialog_leader, parent, false)
        return ViewHolder(view)
    }

    //    设置 RecyclerView 中每个位置的视图的数据。
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        // 通过 String account 查询得到 User
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size


    @SuppressLint("InflateParams")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemUserName = itemView.findViewById<TextView>(R.id.MemberName_Leader)
        val itemUniversity = itemView.findViewById<TextView>(R.id.MemberUniversity_Leader)
        val itemMajor = itemView.findViewById<TextView>(R.id.MemberMajor_Leader)
        val btnKickOut = itemView.findViewById<Button>(R.id.btnKickOut)

        //        布局与数据的绑定，设置对应布局的文本
        @SuppressLint("SetTextI18n")
        fun bind(item: String) {

            // 查询数据库 获得 UserName
            val LeaderDetailQuery = BmobQuery<User>()
            LeaderDetailQuery.getObject(item, object : QueryListener<User>() {
                override fun done(UserItem: User?, e: BmobException?) {
                    if (e == null && UserItem != null) {
                        itemUserName.text = UserItem.userName// 设置 用户名
                        itemUniversity.text = UserItem.university// 设置 学校
                        itemMajor.text = UserItem.major// 设置 专业
                    }
                }
            })

            // 请出按钮 监听事件
            btnKickOut.setOnClickListener {
                Log.d("myDebug","点击团员请出团队按钮")
            }
        }
    }
}