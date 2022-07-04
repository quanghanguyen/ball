package com.example.matchball.mymatches.myrequest.details

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.matchball.R
import com.example.matchball.createrequest.RequestMapsActivity
import com.example.matchball.createrequest.ShowRequestMapActivity
import com.example.matchball.databinding.ActivityYourRequestDetailsBinding
import com.example.matchball.firebaseconnection.DatabaseConnection
import com.example.matchball.model.MatchRequest
import java.util.*

class YourRequestDetailsActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var id : String? = null
    private var teamName : String? = null
    private var teamPhone : String? = null
    private var latitude : String? = null
    private var longitude : String? = null

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
        initPhoneAndNameObserve()
        initDeleteDataObserve()
        yourRequestDetailsViewModel.handleNameAndPhone()
    }

    private fun initEvents() {
        back()
        getIntentData()
        timeSelect()
        peopleSelect()
        locationSelect()
        deleteRequest()
    }

    private fun initPhoneAndNameObserve() {
        yourRequestDetailsViewModel.getNameAndPhone.observe(this) {result ->
            when (result) {
                is YourRequestDetailsViewModel.GetNameAndPhone.GetResultOk -> {
                    teamName = result.name
                    teamPhone = result.phone
                }
                is YourRequestDetailsViewModel.GetNameAndPhone.GetResultError -> {
                    Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
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

    private fun timeSelect() {
        requestDetailsBinding.timeSelect.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            yourRequestDetailsViewModel.day = calendar.get(Calendar.DATE)
            yourRequestDetailsViewModel.month = calendar.get(Calendar.MONTH)
            yourRequestDetailsViewModel.year = calendar.get(Calendar.YEAR)
            val datePickerDialog = DatePickerDialog(this, this, yourRequestDetailsViewModel.year,
                yourRequestDetailsViewModel.month, yourRequestDetailsViewModel.day)
            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        yourRequestDetailsViewModel.myDay = day
        yourRequestDetailsViewModel.myYear = year
        yourRequestDetailsViewModel.myMonth = month + 1
        val calendar : Calendar = Calendar.getInstance()
        yourRequestDetailsViewModel.hour = calendar.get(Calendar.HOUR)
        yourRequestDetailsViewModel.minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this, this, yourRequestDetailsViewModel.hour,
            yourRequestDetailsViewModel.minute,
            DateFormat.is24HourFormat(this))
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        yourRequestDetailsViewModel.myHour = hourOfDay
        yourRequestDetailsViewModel.myMinute = minute
        requestDetailsBinding.timeEt.setText("${yourRequestDetailsViewModel.myHour}:${yourRequestDetailsViewModel.myMinute} (${yourRequestDetailsViewModel.myDay}/${yourRequestDetailsViewModel.myMonth}/${yourRequestDetailsViewModel.myYear})")
    }

    private fun locationSelect() {
        requestDetailsBinding.pitchSelect.setOnClickListener {
            intent = Intent(this, ShowRequestMapActivity::class.java)
            startActivityForResult(intent, 2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                val locationReceived = data?.getStringExtra("location")
//                var latitudeReceived = data?.getStringExtra("latitude")
//                var longitudeReceived = data?.getStringExtra("longitude")
                var latitudeReceived : String? = null
                var longitudeReceived : String? = null

                latitudeReceived = if (latitudeReceived == null) {
                    latitude
                } else {
                    data?.getStringExtra("latitude")
                }
                longitudeReceived = if (longitudeReceived == null) {
                    longitude
                } else {
                    data?.getStringExtra("longitude")
                }

                requestDetailsBinding.pitchEt.setText(("$locationReceived"))
                requestDetailsBinding.update.setOnClickListener {
                    if (requestDetailsBinding.timeEt.text.isNullOrEmpty()
                        || requestDetailsBinding.pitchEt.text.isNullOrEmpty()
                        || requestDetailsBinding.peopleSelect.text.isNullOrEmpty()
                    ) {
                        Toast.makeText(this, "Please Check Your Request Again", Toast.LENGTH_SHORT).show()
                    } else {
                        val matchTime = requestDetailsBinding.timeEt.text.toString()
                        val matchPeople = requestDetailsBinding.peopleSelect.text.toString()
                        val matchNote = requestDetailsBinding.noteEt.text.toString()
                        val location = requestDetailsBinding.pitchEt.text.toString()

                        teamName?.let { it1 ->
                            teamPhone?.let { it2 ->
                                if (id != null) {
                                    yourRequestDetailsViewModel.handleUpdateData(
                                        id!!, it1, matchTime, location,
                                        latitudeReceived, longitudeReceived, matchPeople, matchNote, it2
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun peopleSelect() {
        val peopleOptions = resources.getStringArray(R.array.amount_people)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_items, peopleOptions)
        requestDetailsBinding.peopleSelect.setAdapter(arrayAdapter)
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

    private fun getIntentData() {
        intent?.let { bundle ->
            val matchData = bundle.getParcelableExtra<MatchRequest>(KEY_DATA)
            with(requestDetailsBinding) {
                timeEt.setText(matchData?.time)
                pitchEt.setText(matchData?.pitch)
                noteEt.setText(matchData?.note)
                matchData?.people?.let { peopleSelect.setText(it) }
                if (matchData != null) {
                    latitude = matchData.pitchLatitude
                }
                if (matchData != null) {
                    longitude = matchData.pitchLongitude
                }
                if (matchData != null) {
                    id = matchData.id
                }
            }
        }
    }

    private fun back() {
        requestDetailsBinding.back.setOnClickListener {
            finish()
        }
    }
}