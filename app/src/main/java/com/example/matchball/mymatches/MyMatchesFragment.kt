package com.example.matchball.mymatches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.matchball.R
import com.example.matchball.databinding.FragmentMyMatchesBinding
import com.google.android.material.tabs.TabLayoutMediator

class MyMatchesFragment : Fragment() {

    private lateinit var myMatchesBinding: FragmentMyMatchesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()

    }

    private fun initViewPager() {
        val myMatchPagerAdapter = fragmentManager?.let { MyMatchPagerAdapter(it, lifecycle) }
        myMatchesBinding.myMatchViewpager.adapter = myMatchPagerAdapter

        TabLayoutMediator(myMatchesBinding.myMatchTablayout, myMatchesBinding.myMatchViewpager) {tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Wait"
                }
                1 -> {
                    tab.text = "UpComing"
                }
                2 -> {
                    tab.text = "Done"
                }
            }
        }.attach()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myMatchesBinding = FragmentMyMatchesBinding.inflate(inflater, container, false)
        return myMatchesBinding.root
    }
}