package com.example.matchball.homedashboard.matchlist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.matchball.chat.ChatListActivity
import com.example.matchball.databinding.FragmentListBinding
import com.example.matchball.homedashboard.ViewPagerAdapter
import com.example.matchball.notification.NotificationActivity
import com.google.android.material.tabs.TabLayoutMediator

class MatchListFragment : Fragment() {

    private lateinit var listFragmentBinding: FragmentListBinding
    private lateinit var matchRequestAdapter: RecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
        initEvent()

        //search
        listFragmentBinding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                matchRequestAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun initViewPager() {
        val viewPagerAdapter = fragmentManager?.let { ViewPagerAdapter(it, lifecycle) }
        listFragmentBinding.homeViewpager.adapter = viewPagerAdapter

        TabLayoutMediator(listFragmentBinding.homeTablayout, listFragmentBinding.homeViewpager) {tab, position ->
            when (position) {
                0 -> {
                    tab.text = "All"
                }
                1 -> {
                    tab.text = "Today"
                }
                2 -> {
                    tab.text = "Near Me"
                }
                3 -> {
                    tab.text = "Newest"
                }
            }
        }.attach()
    }

    private fun initEvent() {
        chat()
        notification()
    }

    private fun notification() {
        listFragmentBinding.notifications.setOnClickListener {
            startActivity(Intent(context, NotificationActivity::class.java))
        }
    }

    private fun chat() {
        listFragmentBinding.chat.setOnClickListener {
            startActivity(Intent(context, ChatListActivity::class.java))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        listFragmentBinding = FragmentListBinding.inflate(inflater, container, false)
        return listFragmentBinding.root
    }

    companion object {
        fun newInstance() = MatchListFragment()
    }
}