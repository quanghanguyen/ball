package com.example.matchball.homedashboard.matchlist

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.matchball.firebaseconnection.DatabaseConnection
import com.example.matchball.model.FilterModel
import com.example.matchball.model.MatchRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class MatchListViewModel : ViewModel() {

    val matchListResult = MutableLiveData<MatchListResult>()

    sealed class MatchListResult {
        object Loading : MatchListResult()
        class ResultOk(val matchList : ArrayList<MatchRequest>) : MatchListResult()
        class ResultError(val errorMessage : String) : MatchListResult()
    }

    fun handleMatchList() {
        DatabaseConnection.databaseReference.getReference("MatchRequest").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val listMatch = ArrayList<MatchRequest>()
                    for (requestSnapshot in snapshot.children) {
                        requestSnapshot.getValue(MatchRequest::class.java)?.let {list ->
                            val current = LocalDate.now()
                            val matchTime = list.date

                            val formatter = DateTimeFormatter.ofPattern("d/M/yyyy", Locale.ENGLISH)

                            val date = LocalDate.parse(matchTime, formatter)

                            when {
                                date >= current -> {
                                    listMatch.add(0, list)
                                }
                                else -> {
                                    Log.e("Thong bao", "Failed")
                                }
                            }
                        }
                    }
                    matchListResult.postValue(MatchListResult.ResultOk(listMatch))
                }
            }
            override fun onCancelled(error: DatabaseError) {
                matchListResult.postValue(MatchListResult.ResultError("Failed to load Data"))
            }
        })
    }
}