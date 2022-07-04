package com.example.matchball.mymatches.myrequest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matchball.databinding.FragmentMyRequestBinding
import com.example.matchball.model.MatchRequest
import com.example.matchball.mymatches.myrequest.details.YourRequestDetailsActivity

class MyRequestFragment : Fragment() {

    private lateinit var myRequestBinding : FragmentMyRequestBinding
    private val myRequestViewModel : MyRequestViewModel by viewModels()
    private lateinit var myRequestAdapter: MyRequestAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initObserve()
        myRequestViewModel.handleLoadData()
    }

    private fun initObserve() {
        myRequestViewModel.loadData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is MyRequestViewModel.LoadData.ResultOk -> {
                    myRequestAdapter.addNewData(result.matchList)
                }
                is MyRequestViewModel.LoadData.ResultError -> {
                    Toast.makeText(context, result.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initList() {
        myRequestBinding.myRequestList.apply {
            layoutManager = LinearLayoutManager(context)
            myRequestAdapter = MyRequestAdapter(arrayListOf())
            adapter = myRequestAdapter

            myRequestAdapter.setOnItemClickListerner(object :
                MyRequestAdapter.OnItemClickListerner{
                override fun onItemClick(data: MatchRequest) {
                    YourRequestDetailsActivity.startDetails(context, data)
                }
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myRequestBinding = FragmentMyRequestBinding.inflate(inflater, container, false)
        return myRequestBinding.root
    }
}