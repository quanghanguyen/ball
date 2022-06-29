package com.example.matchball.homedashboard.matchlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.matchball.firebaseconnection.DatabaseConnection
import com.example.matchball.model.FilterModel
import com.example.matchball.model.MatchRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

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
                        requestSnapshot.getValue(MatchRequest::class.java)?.let {
                            listMatch.add(0, it)
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