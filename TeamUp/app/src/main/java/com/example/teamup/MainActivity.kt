package com.example.teamup

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.teamup.ui.DialogFragment
import com.example.teamup.ui.home.HomeFragment
import com.example.teamup.ui.me.MeFragment
import com.example.teamup.ui.team.TeamFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: MyPagerAdapter

    private var islogin = true   // 是否已经登录
    private var isNavView: Boolean = true   // 是否为Nav底边栏

    override fun onCreate(savedInstanceState: Bundle?) {
        // 开始登录
//        if(!islogin){
//            val intent = Intent(this, OpeningActivity::class.java)
//            startActivity(intent)
//        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide() // 隐藏顶部栏
        overridePendingTransition(0,0) // （暂时）取消系统自带动画


//       btnNewActivity 新建 添加监听事件
        val btnCreateTeam = findViewById<FloatingActionButton>(R.id.btnCreateTeam)
        btnCreateTeam.setOnClickListener{view ->
//            如果没有登录则打开登录提示
            if(!islogin)
                DialogFragment().show(supportFragmentManager,"DialogFragment")
            else {
                val intent = Intent(this, CreateTeamActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fab_expand,R.anim.slide_up)
            }
        }

        viewPager = findViewById<ViewPager2>(R.id.viewPager)
        adapter = MyPagerAdapter(this)
        viewPager.adapter = adapter

//      底部导航栏 nav
            var bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_team -> {
                        viewPager.currentItem = 0
                        return@setOnItemSelectedListener true
                    }
                    R.id.navigation_home -> {
                        viewPager.currentItem = 1
                        return@setOnItemSelectedListener true
                    }
                    R.id.navigation_me -> {
                        viewPager.currentItem = 2
                        return@setOnItemSelectedListener true
                    }
                    else -> return@setOnItemSelectedListener false
                }
            }
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val menuItem = bottomNavigationView.menu.getItem(position)
                    menuItem.isChecked = true
                }
            })
//      设置初始页面为 首页
        viewPager.currentItem = 1
    }

    private class MyPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        private val fragments = listOf(TeamFragment(), HomeFragment(), MeFragment())

        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position]
    }
}