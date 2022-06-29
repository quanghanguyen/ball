package com.example.matchball.notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matchball.R
import com.example.matchball.confirm.ConfirmActivity
import com.example.matchball.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private lateinit var notificationBinding : ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationBinding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(notificationBinding.root)

        initEvent()
    }

    private fun initEvent() {
        back()
        itemsClick()
    }

    private fun itemsClick() {
        notificationBinding.notification1.setOnClickListener {
            val content = notificationBinding.notificationContent.text.toString()
            val intent = Intent(this, ConfirmActivity::class.java)
            intent.putExtra("content", content)
            startActivity(intent)
        }
    }

    private fun back() {
        notificationBinding.back.setOnClickListener {
            finish()
        }
    }
}