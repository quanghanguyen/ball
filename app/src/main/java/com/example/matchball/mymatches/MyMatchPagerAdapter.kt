package com.example.matchball.mymatches

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.matchball.mymatches.done.DoneFragment
import com.example.matchball.mymatches.wait.list.UpcomingFragment
import com.example.matchball.mymatches.upcoming.WaitFragment

class MyMatchPagerAdapter(fm : FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                UpcomingFragment()
            }
            1 -> {
                WaitFragment()
            }
            2 -> {
                DoneFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}