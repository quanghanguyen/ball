package com.example.matchball.homedashboard.today

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matchball.databinding.FragmentTodayBinding
import com.example.matchball.homedashboard.matchlist.MatchListViewModel
import com.example.matchball.homedashboard.matchlist.RecyclerAdapter
import com.example.matchball.joinmatch.JoinActivity
import com.example.matchball.model.MatchRequest

class TodayFragment : Fragment() {

    private lateinit var fragmentTodayBinding: FragmentTodayBinding
    private lateinit var matchRequestAdapter: RecyclerAdapter
    private val todayListViewModel: TodayViewModel by viewModels()
    private var todayList = ArrayList<MatchRequest>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initObserve()
        initEvents()
        todayListViewModel.handleTodayList()
    }

    private fun initList() {
        fragmentTodayBinding.rcvMatchRequest.apply {
            layoutManager = LinearLayoutManager(context)
            matchRequestAdapter = RecyclerAdapter(arrayListOf())
            adapter = matchRequestAdapter
            matchRequestAdapter.setOnItemClickListerner(object :
                RecyclerAdapter.OnItemClickListerner {
                override fun onItemClick(requestData: MatchRequest) {
                    JoinActivity.startDetails(requireContext(), requestData)
                }
            })
        }
    }

    private fun initObserve() {
        todayListViewModel.todayListResult.observe(viewLifecycleOwner) { result ->
            fragmentTodayBinding.swipe.isRefreshing = false
            when (result) {
                is TodayViewModel.TodayListResult.Loading -> {
                    fragmentTodayBinding.swipe.isRefreshing = true
                }
                is TodayViewModel.TodayListResult.ResultOk -> {
                    matchRequestAdapter.addNewData(result.todayList)
                    todayList = result.todayList
                }
                is TodayViewModel.TodayListResult.ResultError -> {
                    Toast.makeText(context, result.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initEvents() {
        refresh()
    }

    private fun refresh() {
        fragmentTodayBinding.swipe.setOnRefreshListener {
            todayListViewModel.handleTodayList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTodayBinding = FragmentTodayBinding.inflate(inflater, container, false)
        return fragmentTodayBinding.root
    }
}