package com.example.matchball.mymatches.done

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.matchball.R
import com.example.matchball.databinding.FragmentDoneBinding

class DoneFragment : Fragment() {

    private lateinit var fragmentDoneBinding: FragmentDoneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentDoneBinding = FragmentDoneBinding.inflate(inflater, container, false)
        return fragmentDoneBinding.root
    }
}