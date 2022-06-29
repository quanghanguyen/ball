package com.example.matchball.mymatches.upcoming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.matchball.databinding.FragmentWaitBinding

class WaitFragment : Fragment() {

    private lateinit var fragmentWaitBinding: FragmentWaitBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentWaitBinding = FragmentWaitBinding.inflate(inflater, container, false)
        return fragmentWaitBinding.root
    }

}