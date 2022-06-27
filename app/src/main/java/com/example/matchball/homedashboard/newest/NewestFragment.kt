package com.example.matchball.homedashboard.newest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.matchball.R
import com.example.matchball.databinding.FragmentNewestBinding

class NewestFragment : Fragment() {

    private lateinit var fragmentNewestBinding: FragmentNewestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentNewestBinding = FragmentNewestBinding.inflate(inflater, container, false)
        return fragmentNewestBinding.root
    }
}