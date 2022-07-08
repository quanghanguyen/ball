package com.example.matchball.homedashboard.newest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matchball.R
import com.example.matchball.databinding.FragmentNewestBinding
import com.example.matchball.homedashboard.matchlist.RecyclerAdapter
import com.example.matchball.homedashboard.today.TodayViewModel
import com.example.matchball.joinmatch.JoinActivity
import com.example.matchball.model.MatchRequest

class NewestFragment : Fragment() {

    private lateinit var fragmentNewestBinding: FragmentNewestBinding
    private lateinit var matchRequestAdapter: RecyclerAdapter
    private val newestViewModel : NewestViewModel by viewModels()
    private var newestList = ArrayList<MatchRequest>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initObserve()
        initEvents()
        newestViewModel.handleNewestList()
    }

    private fun initEvents() {
        refresh()
    }

    private fun refresh() {
        fragmentNewestBinding.swipe.setOnRefreshListener {
            newestViewModel.handleNewestList()
        }
    }

    private fun initObserve() {
        newestViewModel.newestListResult.observe(viewLifecycleOwner) { result ->
            fragmentNewestBinding.swipe.isRefreshing = false
            when (result) {
                is NewestViewModel.NewestListResult.Loading -> {
                    fragmentNewestBinding.swipe.isRefreshing = true
                }
                is NewestViewModel.NewestListResult.ResultOk -> {
                    matchRequestAdapter.addNewData(result.newestList)
                    newestList = result.newestList
                }
                is NewestViewModel.NewestListResult.ResultError -> {
                    Toast.makeText(context, result.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initList() {
        fragmentNewestBinding.rcvMatchRequest.apply {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentNewestBinding = FragmentNewestBinding.inflate(inflater, container, false)
        return fragmentNewestBinding.root
    }
}