package com.example.matchball.mymatches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.matchball.R
import com.example.matchball.databinding.FragmentMyMatchesBinding

class MyMatchesFragment : Fragment() {

    private lateinit var myMatchesBinding: FragmentMyMatchesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myMatchesBinding = FragmentMyMatchesBinding.inflate(inflater, container, false)
        return myMatchesBinding.root
    }
}