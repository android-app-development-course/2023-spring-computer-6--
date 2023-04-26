package com.example.teamup

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        // 开始登录
//        if(!islogin){
//            val intent = Intent(this, OpeningActivity::class.java)
//            startActivity(intent)
//        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide() // 隐藏顶部栏


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

        //设置初始页面为 首页
        viewPager.setCurrentItem(1,false)



//      底部导航栏 nav
        var bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId=R.id.navigation_home //初始底部栏为首页
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




    }

    private class MyPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        private val fragments = listOf(TeamFragment(), HomeFragment(), MeFragment())

        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position]
    }
}