package com.example.matchball.mymatches.myrequest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.matchball.R
import com.example.matchball.databinding.FragmentMyRequestBinding

class MyRequestFragment : Fragment() {

    private lateinit var myRequestFragment : FragmentMyRequestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myRequestFragment = FragmentMyRequestBinding.inflate(inflater, container, false)
        return myRequestFragment.root
    }
}