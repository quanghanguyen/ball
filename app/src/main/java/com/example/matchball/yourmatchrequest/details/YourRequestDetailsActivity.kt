package com.example.matchball.yourmatchrequest.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.matchball.databinding.ActivityYourRequestDetailsBinding
import com.example.matchball.model.MatchRequest
import com.example.matchball.yourmatchrequest.list.YourRequestActivity

class YourRequestDetailsActivity : AppCompatActivity() {

    private var id : String? = null

    private lateinit var requestDetailsBinding : ActivityYourRequestDetailsBinding
    private val yourRequestDetailsViewModel : YourRequestDetailsViewModel by viewModels()
    companion object {
        private const val KEY_DATA = "match_data"
        fun startDetails(context : Context, data : MatchRequest) {
            context.startActivity(Intent(context, YourRequestDetailsActivity::class.java).apply {
                putExtra(KEY_DATA, data)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestDetailsBinding = ActivityYourRequestDetailsBinding.inflate(layoutInflater)
        setContentView(requestDetailsBinding.root)

        initEvents()
        initObserve()
        initDeleteDataObserve()
    }

    private fun initDeleteDataObserve() {
        yourRequestDetailsViewModel.deleteData.observe(this) {result ->
            when (result) {
                is YourRequestDetailsViewModel.DeleteData.ResultOk -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
                is YourRequestDetailsViewModel.DeleteData.ResultError -> {
                    Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initObserve() {
        yourRequestDetailsViewModel.updateData.observe(this) {result ->
            when (result) {
                is YourRequestDetailsViewModel.UpdateData.ResultOk -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
                is YourRequestDetailsViewModel.UpdateData.ResultError -> {
                    Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initEvents() {
        back()
        getIntentData()
        updateRequest()
        deleteRequest()
    }

    private fun deleteRequest() {
        requestDetailsBinding.delete.setOnClickListener {
            val deleteDialog = AlertDialog.Builder(this)
            deleteDialog.apply {
                setTitle("Delete")
                setMessage("Do you want to delete this request?")
                setPositiveButton("Yes") {_, _ ->
                    id?.let { it1 -> yourRequestDetailsViewModel.handleDeleteData(it1) }
                }
                setNegativeButton("No") {_, _ ->
                    //
                }
            }.create().show()
        }
    }

    private fun updateRequest() {
        requestDetailsBinding.update.setOnClickListener {
            val teamName = "X"
            val matchTime = requestDetailsBinding.timeEt.text.toString()
            val location = requestDetailsBinding.pitchEt.text.toString()
            val latitude = "1.25014"
            val longitude = "2.15652"
            val people = requestDetailsBinding.peopleSelect.text.toString()
            val note = requestDetailsBinding.noteEt.text.toString()
            val phone = "45612137855"
            id?.let { it1 -> yourRequestDetailsViewModel.handleUpdateData(it1, teamName, matchTime, location, latitude,
                longitude, people, note, phone) }
        }
    }

    private fun getIntentData() {
        intent?.let { bundle ->
            val matchData = bundle.getParcelableExtra<MatchRequest>(KEY_DATA)
            with(requestDetailsBinding) {
                timeEt.setText(matchData?.time)
                pitchEt.setText(matchData?.pitch)
                noteEt.setText(matchData?.note)
                peopleSelect.setText(matchData?.people)

                if (matchData != null) {
                    id = matchData.id
//                    id?.let { yourRequestDetailsViewModel.setId(it) }
                }
            }
        }
    }

    private fun back() {
        requestDetailsBinding.back.setOnClickListener {
            finish()
        }
    }

//    private fun selectMap() {
//        requestDetailsBinding.btnPitchSelect.setOnClickListener {
//            val intent = Intent(this, ShowRequestMapActivity::class.java)
//            val locationReceived = intent.getStringExtra("location")
//            intent.let {
//                requestDetailsBinding.tvPitch.text = locationReceived
//            }
//            startActivity(intent)
//        }
//    }
//
//    private fun spnPeopleClick() {
//        requestDetailsBinding.spnPeople.adapter = ArrayAdapter<Int>(this, R.layout.simple_list_item_1, peopleOptions)
//        requestDetailsBinding.spnPeople.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//
//            }
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//        }
//    }
//
//    private fun tvDoneClick() {
//
//    }
//
//    private fun ivBackClick() {
//        requestDetailsBinding.ivBack.setOnClickListener {
//            val intent = Intent(this, YourRequestActivity::class.java)
//            startActivity(intent)
//        }
//    }
//
//    private fun openPitchMap() {
//        requestDetailsBinding.tvPitch.setOnClickListener {
//            intent?.let { bundle ->
//                val requests = bundle.getParcelableExtra<MatchRequest>(KEY_DATA)
//
//                val pitchLatitude = requests?.pitchLatitude?.toDouble()
//                val pitchLongitude = requests?.pitchLongitude?.toDouble()
//                val pitchName = requests?.pitch.toString()
//
//                val intent = Intent(this, JoinMapsActivity::class.java)
//                intent.putExtra("pitchLatitude", pitchLatitude)
//                intent.putExtra("pitchLongitude", pitchLongitude)
//                intent.putExtra("pitchName", pitchName)
//                startActivity(intent)
//            }
//        }
//    }

}