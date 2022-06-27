package com.example.matchball.homedashboard.matchlist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matchball.chat.ChatListActivity
import com.example.matchball.joinmatch.JoinActivity
import com.example.matchball.databinding.FragmentListBinding
import com.example.matchball.homedashboard.ViewPagerAdapter
import com.example.matchball.model.MatchRequest
import com.google.android.material.tabs.TabLayoutMediator

class MatchListFragment : Fragment() {

    private lateinit var listFragmentBinding: FragmentListBinding
    private lateinit var matchRequestAdapter: RecyclerAdapter
//    private lateinit var filterAdapter : FilterAdapter
    private val matchListViewModel: MatchListViewModel by viewModels()
    private var matchList = ArrayList<MatchRequest>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
//        initList()
//        initFilterList()
//        initObserve()
        initEvent()
//        matchListViewModel.handleMatchList()

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
                    tab.text = "Near Me"
                }
                2 -> {
                    tab.text = "Newest"
                }
                3 -> {
                    tab.text = "Oldest"
                }
            }
        }.attach()
    }

//    private fun initObserve() {
//        matchListViewModel.matchListResult.observe(viewLifecycleOwner) { result ->
//            listFragmentBinding.swipe.isRefreshing = false
//            when (result) {
//                is MatchListViewModel.MatchListResult.Loading -> {
//                    listFragmentBinding.swipe.isRefreshing = true
//                }
//                is MatchListViewModel.MatchListResult.ResultOk -> {
//                    matchRequestAdapter.addNewData(result.matchList)
//                    matchList = result.matchList
//                }
//                is MatchListViewModel.MatchListResult.ResultError -> {
//                    Toast.makeText(context, result.errorMessage, Toast.LENGTH_SHORT).show()
//                }
//                is MatchListViewModel.MatchListResult.ResultFilterOk -> {
//                    filterAdapter.addFilterNewData(result.filterList)
//
//                    filterAdapter.setOnItemClickListerner(object :
//                        FilterAdapter.OnItemClickListerner {
//                        override fun onItemClick(position: Int) {
//                            if (position == 0) {
//                                matchRequestAdapter.addNewData(matchList)
//                            }
//                        }
//                    })
//                }
//            }
//        }
//    }

//    private fun initFilterList() {
//        listFragmentBinding.filter.apply {
//            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
//            filterAdapter = FilterAdapter(arrayListOf())
//            adapter = filterAdapter
//        }
//    }

//    private fun initList() {
//        listFragmentBinding.rcvMatchRequest.apply {
//            layoutManager = LinearLayoutManager(context)
//            matchRequestAdapter = RecyclerAdapter(arrayListOf())
//            adapter = matchRequestAdapter
//            matchRequestAdapter.setOnItemClickListerner(object :
//                RecyclerAdapter.OnItemClickListerner {
//                override fun onItemClick(requestData: MatchRequest) {
//                    JoinActivity.startDetails(requireContext(), requestData)
//                }
//            })
//        }
//    }

    private fun initEvent() {
        chat()
//        refresh()
    }

//    private fun refresh() {
//        listFragmentBinding.swipe.setOnRefreshListener {
//            matchListViewModel.handleMatchList()
//        }
//    }

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