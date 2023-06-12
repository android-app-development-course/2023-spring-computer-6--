package com.example.teamup.ViewPagerUI.home

import android.content.Context.MODE_APPEND
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import com.example.teamup.DataClass.TeamInfo
import com.example.teamup.DataClass.User
import com.example.teamup.R
import com.example.teamup.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private var UserID = "-1"

    private lateinit var recView: RecyclerView
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recView = view.findViewById(R.id.home_recy_view)
    }
//  每次显示页面 刷新
    override fun onResume() {
        super.onResume()
        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("LoginUserInfo", MODE_PRIVATE)
        var itUserID = sharedPreferences.getString("id","-1")
        if((itUserID == UserID && UserID != "-1"))
            return // 假设没有变化，则不会进行操作
        UserID = itUserID ?: "-1"
        var totalList = mutableListOf<String>()// 记录该用户已经加入和领导的 teamInfo.ObjectID
        var itemList = mutableListOf<TeamInfo>()// 存储该用户已经加入和领导的 teamInfo 内容

        // 查询数据 获得用户加入和领导 的队伍
        if(UserID != "-1") {
            val query = BmobQuery<User>()
            query.getObject( UserID, object : QueryListener<User>() {
                override fun done(person: User?, e: BmobException?) {
                    if (e == null) { // 查询成功
                        if (person != null) { // 查询到了符合条件的数据
                            totalList.addAll(person.joinTeam?.toList()!!)
                            totalList.addAll(person.leadTeam?.toList()!!)
                        }
                    } else { // 查询失败，处理异常
                        Log.e("ErrorTAG", e.toString())
                    }
                }
            })
            val notInTotalListQuery = BmobQuery<TeamInfo>() // 查找不在totalList 数据库的 队伍
            notInTotalListQuery.addWhereNotContainedIn("objectID", totalList)
            notInTotalListQuery.findObjects(object : FindListener<TeamInfo>() {
                override fun done(teams: MutableList<TeamInfo>?, e: BmobException?) {
                    if (e == null) { // 查询成功
                        if (teams != null && teams.isNotEmpty()) { // 查询到了符合条件的数据
                            itemList = teams
                        }
                    } else { // 查询失败，处理异常
                        Log.e("ErrorTAG", e.toString())
                    }
                }
            })
        }
    //        Log.d("totalListSize", totalList.size.toString())
        Log.d("itemListSize", itemList.size.toString())
//        Toast.makeText(activity, "itemList.size ${itemList.size}", Toast.LENGTH_SHORT).show()
        // 绑定视图
        if(itemList.isNotEmpty()) {
            val adapter = HomeAdapter(itemList)
            recView.adapter = adapter
            recView.layoutManager = LinearLayoutManager(activity) // 线性布局
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}