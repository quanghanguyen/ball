package com.example.matchball.mymatches.myrequest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.matchball.firebaseconnection.AuthConnection
import com.example.matchball.firebaseconnection.DatabaseConnection
import com.example.matchball.model.MatchRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MyRequestViewModel : ViewModel() {

    val loadData = MutableLiveData<LoadData>()
    private val uid = AuthConnection.auth.currentUser!!.uid

    sealed class LoadData {
        class ResultOk(val matchList : ArrayList<MatchRequest>) : LoadData()
        class ResultError(val errorMessage : String) : LoadData()
    }

    fun handleLoadData() {
        DatabaseConnection.databaseReference.getReference("User_MatchRequest").child(uid).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val list = ArrayList<MatchRequest>()
                    for (requestSnapshot in snapshot.children) {
                        requestSnapshot.getValue(MatchRequest::class.java)?.let {
                            list.add(0, it)
                        }
                    }
                    loadData.postValue(LoadData.ResultOk(list))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                loadData.postValue(LoadData.ResultError("Error"))
            }
        })
    }
}