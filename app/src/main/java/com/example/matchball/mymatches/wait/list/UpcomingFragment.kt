package com.example.matchball.mymatches.wait.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matchball.databinding.FragmentUpcomingBinding
import com.example.matchball.model.MatchRequest
import com.example.matchball.mymatches.MyMatchListAdapter
import com.example.matchball.mymatches.wait.details.UpComingDetailActivity

class UpcomingFragment : Fragment() {

    private lateinit var upcomingBinding: FragmentUpcomingBinding
    private val upComingViewModel : WaitViewModel by viewModels()
    private lateinit var myMatchesAdapter: MyMatchListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initObserve()
        upComingViewModel.handleLoadData()
    }

    private fun initObserve() {
        upComingViewModel.loadUpComingMatch.observe(viewLifecycleOwner) { result ->
            when (result) {
                is WaitViewModel.LoadResult.ResultOk -> {
                    myMatchesAdapter.addNewData(result.data)
                }
                is WaitViewModel.LoadResult.ResultError -> {
                    Toast.makeText(context, result.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initList() {
        upcomingBinding.upcomingList.apply {
            layoutManager = LinearLayoutManager(context)
            myMatchesAdapter = MyMatchListAdapter(arrayListOf())
            adapter = myMatchesAdapter

            myMatchesAdapter.setOnItemClickListerner(object :
                MyMatchListAdapter.OnItemClickListerner{
                override fun onItemClick(data: MatchRequest) {
                    UpComingDetailActivity.startDetails(context, data)
                }
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        upcomingBinding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return upcomingBinding.root
    }
}