package com.example.matchball.mymatches.wait.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matchball.chat.ChatListActivity
import com.example.matchball.databinding.ActivityUpComingDetailBinding
import com.example.matchball.model.MatchRequest

class WaitDetailActivity : AppCompatActivity() {

    private lateinit var upComingDetailBinding: ActivityUpComingDetailBinding

    companion object
    {
        private const val KEY_DATA = "request_data"
        fun startDetails(context: Context, data : MatchRequest?)
        {
            context.startActivity(Intent(context, WaitDetailActivity::class.java).apply {
                putExtra(KEY_DATA, data)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        upComingDetailBinding = ActivityUpComingDetailBinding.inflate(layoutInflater)
        setContentView(upComingDetailBinding.root)

        initIntentData()
        initEvent()
    }

    private fun initEvent() {
        back()
        openChat()
    }

    private fun openChat() {
        upComingDetailBinding.openChat.setOnClickListener {
            startActivity(Intent(this, ChatListActivity::class.java))
        }
    }

    private fun back() {
        upComingDetailBinding.back.setOnClickListener {
            finish()
        }
    }

    private fun initIntentData() {
        intent?.let { bundle ->
            val requests = bundle.getParcelableExtra<MatchRequest>(KEY_DATA)

            with(upComingDetailBinding){
                tvJMTeamName.text = requests!!.teamName
                tvJMTime.text = requests.time
                tvJMPitch.text = requests.pitch
                tvJMNote.text = requests.note
                tvJMPhone.text = requests.phone
                tvJMPeople.text = requests.people.toString()
            }
        }
    }
}