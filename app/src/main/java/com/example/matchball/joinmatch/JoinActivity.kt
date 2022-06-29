package com.example.matchball.joinmatch

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.matchball.databinding.ActivityJoinBinding
import com.example.matchball.firebaseconnection.AuthConnection.authUser
import com.example.matchball.home.MainActivity
import com.example.matchball.model.MatchRequest

class JoinActivity : AppCompatActivity() {

    private lateinit var joinBinding: ActivityJoinBinding
    private val joinViewModel : JoinViewModel by viewModels()

    companion object
    {
        private const val KEY_DATA = "request_data"
        fun startDetails(context: Context, data : MatchRequest?)
        {
            context.startActivity(Intent(context, JoinActivity::class.java).apply {
                putExtra(KEY_DATA, data)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        joinBinding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(joinBinding.root)

        initIntentData()
        initEvent()
        initObserve()
    }

    private fun initIntentData() {
        intent?.let { bundle ->
            val requests = bundle.getParcelableExtra<MatchRequest>(KEY_DATA)

            with(joinBinding){
                tvJMTeamName.text = requests!!.teamName
                tvJMTime.text = requests.time
                tvJMPitch.text = requests.pitch
                tvJMNote.text = requests.note
                tvJMPhone.text = requests.phone
                tvJMPeople.text = requests.people
            }
        }
    }

    private fun initEvent() {
        checkRequest()
        sendJoinRequest()
        openPitchMap()
        makeCall()
        back()
    }

    private fun initObserve() {
        joinViewModel.saveRequest.observe(this) {result ->
            when (result) {
                is JoinViewModel.SaveResultResult.ResultOk -> {
                    Toast.makeText(this, result.successMessage, Toast.LENGTH_SHORT).show()
                    finish()
                }
                is JoinViewModel.SaveResultResult.ResultError -> {
                    Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkRequest() {
        if (joinBinding.tvJMPhone.text == authUser?.phoneNumber) {
            joinBinding.btnJoin.visibility = View.GONE
        }
    }

    private fun makeCall() {
        joinBinding.call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            val phoneNumber = "tel:" + joinBinding.tvJMPhone.toString()
            intent.data = Uri.parse(phoneNumber)
            startActivity(intent)
        }
    }

    private fun back() {
        joinBinding.back.setOnClickListener {
            finish()
        }
    }

    private fun sendJoinRequest() {
        joinBinding.btnJoin.setOnClickListener {
            intent?.let { bundle ->
                val requests = bundle.getParcelableExtra<MatchRequest>(KEY_DATA)
                val pitchLatitude = requests?.pitchLatitude
                val pitchLongitude = requests?.pitchLongitude
                val teamName = joinBinding.tvJMTeamName.text.toString()
                val time = joinBinding.tvJMTime.text.toString()
                val location = joinBinding.tvJMPitch.text.toString()
                val people = joinBinding.tvJMPeople.text.toString()
                val note = joinBinding.tvJMNote.text.toString()
                val phone = joinBinding.tvJMPhone.text.toString()

                joinViewModel.handleSaveRequest(teamName, time, location, pitchLatitude, pitchLongitude, people, note, phone)
            }
        }
    }

    private fun openPitchMap() {
        joinBinding.location.setOnClickListener {
            intent?.let { bundle ->
                val requests = bundle.getParcelableExtra<MatchRequest>(KEY_DATA)
                val pitchLatitude = requests?.pitchLatitude?.toDouble()
                val pitchLongitude = requests?.pitchLongitude?.toDouble()
                val pitchName = requests?.pitch.toString()

                if (isGoogleMapsInstalled()) {
                    val gmmIntentUri = Uri.parse("geo:$pitchLatitude, $pitchLongitude")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
                } else {
                    val intent = Intent(this, JoinMapsActivity::class.java)
                    intent.putExtra("pitchLatitude", pitchLatitude)
                    intent.putExtra("pitchLongitude", pitchLongitude)
                    intent.putExtra("pitchName", pitchName)
                    startActivity(intent)
                }
            }
        }
    }

    private fun isGoogleMapsInstalled(): Boolean {
        return try {
            packageManager.getPackageInfo("com.google.android.apps.maps", 0)
            true
        } catch (e : PackageManager.NameNotFoundException) {
            false
        }
    }
}