package com.example.teamup.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.teamup.databinding.FragmentTeamBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TeamFragment : Fragment() {

    private var _binding: FragmentTeamBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this).get(TeamViewModel::class.java)
    }

    private val viewPager by lazy {
        binding.viewPagerTeam
    }

    private val tabLayout by lazy {
        binding.tabLayoutTeam
    }

    private val pagerAdapter by lazy {
        PagerAdapter(childFragmentManager, lifecycle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = pagerAdapter

//        分页标题
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "我是队长"
                1 -> tab.text = "我是队员"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewPager.adapter = null
        tabLayout.removeAllTabs()
        _binding = null
    }

//    内部类：使用 PagerAdater 创建分页
    inner class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> TeamLeaderFragment()
                1 -> TeamMemberFragment()
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }
    }
}