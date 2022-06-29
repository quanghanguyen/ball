package com.example.matchball.mymatches

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.matchball.mymatches.done.DoneFragment
import com.example.matchball.mymatches.myrequest.MyRequestFragment
import com.example.matchball.mymatches.wait.list.WaitFragment
import com.example.matchball.mymatches.upcoming.UpComingFragment

class MyMatchPagerAdapter(fm : FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle){
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                WaitFragment()
            }
            1 -> {
                MyRequestFragment()
            }
            2 -> {
                UpComingFragment()
            }
            3 -> {
                DoneFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}