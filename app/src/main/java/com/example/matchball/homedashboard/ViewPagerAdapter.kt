package com.example.matchball.homedashboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.matchball.homedashboard.all.AllFragment
import com.example.matchball.homedashboard.nearme.NearMeFragment
import com.example.matchball.homedashboard.newest.NewestFragment
import com.example.matchball.homedashboard.oldest.OldestFragment

class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                AllFragment()
            }
            1 -> {
                NearMeFragment()
            }
            2 -> {
                NewestFragment()
            }
            3 -> {
                OldestFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}