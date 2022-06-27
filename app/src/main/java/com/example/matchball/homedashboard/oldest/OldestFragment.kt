package com.example.matchball.homedashboard.oldest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.matchball.R
import com.example.matchball.databinding.FragmentNewestBinding
import com.example.matchball.databinding.FragmentOldestBinding

class OldestFragment : Fragment() {

    private lateinit var fragmentOldestBinding: FragmentOldestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentOldestBinding = FragmentOldestBinding.inflate(inflater, container, false)
        return fragmentOldestBinding.root
    }
}