package com.example.matchball.homedashboard.today

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.matchball.firebaseconnection.DatabaseConnection
import com.example.matchball.homedashboard.matchlist.MatchListViewModel
import com.example.matchball.model.MatchRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TodayViewModel : ViewModel() {

    val todayListResult = MutableLiveData<TodayListResult>()

    sealed class TodayListResult {
        object Loading : TodayListResult()
        class ResultOk(val todayList : ArrayList<MatchRequest>) : TodayListResult()
        class ResultError(val errorMessage : String) : TodayListResult()
    }

    fun handleTodayList() {
        DatabaseConnection.databaseReference.getReference("MatchRequest").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val listToday = ArrayList<MatchRequest>()
                    for (requestSnapshot in snapshot.children) {
                        requestSnapshot.getValue(MatchRequest::class.java)?.let {
                            it.people = "10"
                            listToday.add(0, it)
                        }
                    }
                    todayListResult.postValue(TodayListResult.ResultOk(listToday))
                }
            }
            override fun onCancelled(error: DatabaseError) {
                todayListResult.postValue(TodayListResult.ResultError("Failed to load Data"))
            }
        })
    }
}