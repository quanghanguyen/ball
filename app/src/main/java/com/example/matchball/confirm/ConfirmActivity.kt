package com.example.matchball.confirm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matchball.R
import com.example.matchball.databinding.ActivityConfirmBinding

class ConfirmActivity : AppCompatActivity() {

    private lateinit var confirmBinding : ActivityConfirmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        confirmBinding = ActivityConfirmBinding.inflate(layoutInflater)
        setContentView(confirmBinding.root)
    }
}