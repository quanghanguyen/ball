package com.example.matchball.homedashboard.all

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matchball.R
import com.example.matchball.databinding.FragmentAllBinding
import com.example.matchball.homedashboard.matchlist.MatchListViewModel
import com.example.matchball.homedashboard.matchlist.RecyclerAdapter
import com.example.matchball.joinmatch.JoinActivity
import com.example.matchball.model.MatchRequest

class AllFragment : Fragment() {

    private lateinit var fragmentAllBinding: FragmentAllBinding
    private lateinit var matchRequestAdapter: RecyclerAdapter
    private val matchListViewModel: MatchListViewModel by viewModels()
    private var matchList = ArrayList<MatchRequest>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initObserve()
        initEvents()
        matchListViewModel.handleMatchList()

    }

    private fun initEvents() {
        refresh()
    }

    private fun initList() {
        fragmentAllBinding.rcvMatchRequest.apply {
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
        matchListViewModel.matchListResult.observe(viewLifecycleOwner) { result ->
            fragmentAllBinding.swipe.isRefreshing = false
            when (result) {
                is MatchListViewModel.MatchListResult.Loading -> {
                    fragmentAllBinding.swipe.isRefreshing = true
                }
                is MatchListViewModel.MatchListResult.ResultOk -> {
                    matchRequestAdapter.addNewData(result.matchList)
                    matchList = result.matchList
                }
                is MatchListViewModel.MatchListResult.ResultError -> {
                    Toast.makeText(context, result.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun refresh() {
        fragmentAllBinding.swipe.setOnRefreshListener {
            matchListViewModel.handleMatchList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentAllBinding = FragmentAllBinding.inflate(inflater, container, false)
        return fragmentAllBinding.root
    }
}