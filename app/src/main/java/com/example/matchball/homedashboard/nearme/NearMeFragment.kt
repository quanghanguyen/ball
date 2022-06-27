package com.example.matchball.homedashboard.nearme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.matchball.R
import com.example.matchball.databinding.FragmentListBinding
import com.example.matchball.databinding.FragmentNearMeBinding

class NearMeFragment : Fragment() {

    private lateinit var fragmentNearMeBinding : FragmentNearMeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentNearMeBinding = FragmentNearMeBinding.inflate(inflater, container, false)
        return fragmentNearMeBinding.root
    }
}