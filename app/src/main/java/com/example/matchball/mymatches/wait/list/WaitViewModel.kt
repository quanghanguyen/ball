package com.example.matchball.mymatches.wait.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.matchball.firebaseconnection.AuthConnection
import com.example.matchball.firebaseconnection.DatabaseConnection
import com.example.matchball.model.MatchRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class WaitViewModel : ViewModel() {

    private val uid = AuthConnection.auth.currentUser!!.uid
    val loadUpComingMatch = MutableLiveData<LoadResult>()

    sealed class LoadResult {
        class ResultOk(val data : ArrayList<MatchRequest>) : LoadResult()
        class ResultError(val errorMessage : String) : LoadResult()
    }

    fun handleLoadData() {
        DatabaseConnection.databaseReference.getReference("Your_Match").child(uid).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val list = ArrayList<MatchRequest>()
                    for (requestSnapshot in snapshot.children) {
                        requestSnapshot.getValue(MatchRequest::class.java)?.let {
                            list.add(0, it)
                        }
                    }
                    loadUpComingMatch.postValue(LoadResult.ResultOk(list))
                }
            }
            override fun onCancelled(error: DatabaseError) {
                loadUpComingMatch.postValue(LoadResult.ResultError("Error"))
            }
        })
    }
}