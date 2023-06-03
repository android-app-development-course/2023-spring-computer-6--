package com.example.teamup.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teamup.R
import com.example.teamup.dataClass.Team

class TeamAdapter(private val itemList: List<Team>) : RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_team_listview, parent, false)
        return ViewHolder(view)
    }

//    设置 RecyclerView 中每个位置的视图的数据。
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        private val imgView: ImageView = itemView.findViewById(R.id.team_image)
        private val titleView: TextView = itemView.findViewById(R.id.team_title)
        private val sumView: TextView = itemView.findViewById(R.id.team_summary)
        private val dateView: TextView = itemView.findViewById(R.id.team_date)
        private val timeView: TextView = itemView.findViewById(R.id.team_time)
        private val attendeeView: TextView = itemView.findViewById(R.id.team_attendees)


//        布局与数据的绑定，设置对应布局的文本和图片
        fun bind(item: Team) {
            titleView.text = item.title
            sumView.text = item.summary
            dateView.text = item.date
            timeView.text = item.time
            attendeeView.text = item.numMember.toString()  + '/' + item.maxMember.toString()
//            imgView.setImageURI(item.imgSrc.toUri())  // 将字符串转为 Uri

        }
    }
}