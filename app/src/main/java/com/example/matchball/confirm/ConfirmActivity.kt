package com.example.matchball.confirm

import android.content.Intent
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

        initEvent()
    }

    private fun initEvent() {
        intentReceived()
        back()
    }

    private fun intentReceived() {
        val content = intent.getStringExtra("content")
        confirmBinding.text.text = content
    }

    private fun back() {
        confirmBinding.back.setOnClickListener {
            finish()
        }
    }
}