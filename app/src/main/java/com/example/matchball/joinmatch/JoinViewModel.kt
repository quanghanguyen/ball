package com.example.matchball.joinmatch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.matchball.firebaseconnection.AuthConnection
import com.example.matchball.firebaseconnection.DatabaseConnection
import com.example.matchball.model.MatchRequest

class JoinViewModel : ViewModel() {

    private val uid = AuthConnection.auth.currentUser!!.uid
    val saveRequest = MutableLiveData<SaveResultResult>()

    sealed class SaveResultResult {
        class ResultOk(val successMessage : String) : SaveResultResult()
        class ResultError(val errorMessage : String) : SaveResultResult()
    }

    fun handleSaveRequest(id: String, teamNameReceived : String, matchDate: String, matchTime : String, locationReceived: String?,
                          latitudeReceived: String?, longitudeReceived : String?, matchPeople: String?,
                          matchNote : String, teamPhoneReceived : String) {

        val data = MatchRequest(id, teamNameReceived, matchDate, matchTime, locationReceived, latitudeReceived, longitudeReceived,
            matchPeople, matchNote, teamPhoneReceived)

        DatabaseConnection.databaseReference.getReference("Your_Match").child(uid).child(id).setValue(data).addOnCompleteListener {
            if (it.isSuccessful) {
                saveRequest.postValue(SaveResultResult.ResultOk("Success"))
            } else {
                saveRequest.postValue(SaveResultResult.ResultError("Error"))
            }
        }
    }
}