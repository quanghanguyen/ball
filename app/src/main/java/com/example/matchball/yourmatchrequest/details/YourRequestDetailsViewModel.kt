package com.example.matchball.yourmatchrequest.details

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.matchball.firebaseconnection.AuthConnection
import com.example.matchball.firebaseconnection.DatabaseConnection
import com.example.matchball.model.MatchRequest

class YourRequestDetailsViewModel : ViewModel() {

    private val uid = AuthConnection.auth.currentUser!!.uid
    val updateData = MutableLiveData<UpdateData>()
    val deleteData = MutableLiveData<DeleteData>()

    private var requestId: String? = null

//    fun setId(id : String) {
//        requestId = id
//    }

    sealed class UpdateData {
        class ResultOk(val message : String) : UpdateData()
        class ResultError(val errorMessage: String) : UpdateData()
    }

    sealed class DeleteData {
        class ResultOk(val message: String) : DeleteData()
        class ResultError(val errorMessage: String) : DeleteData()
    }

    fun handleUpdateData(id : String, teamNameReceived : String, matchTime : String, locationReceived: String?,
                         latitudeReceived: String?, longitudeReceived : String?, matchPeople: String?,
                         matchNote : String, teamPhoneReceived : String) {

            val user = mapOf (
                "id" to id,
                "teamName" to teamNameReceived,
                "time" to matchTime,
                "pitch" to locationReceived!!,
                "pitchLatitude" to latitudeReceived!!,
                "pitchLongitude" to longitudeReceived!!,
                "people" to matchPeople!!,
                "note" to matchNote,
                "phone" to teamPhoneReceived
            )

        DatabaseConnection.databaseReference.getReference("User_MatchRequest")
            .child(uid)
            .child(id)
            .updateChildren(user)
            .addOnSuccessListener {
                updateData.postValue(UpdateData.ResultOk("Success"))
        }.addOnFailureListener {
            updateData.postValue(UpdateData.ResultError("Fail"))
            }

        DatabaseConnection.databaseReference.getReference("MatchRequest")
            .child(id)
            .updateChildren(user)
    }

    fun handleDeleteData(id : String) {
        DatabaseConnection.databaseReference
            .getReference("MatchRequest")
            .child(id)
            .removeValue()

        DatabaseConnection.databaseReference
            .getReference("User_MatchRequest")
            .child(uid)
            .child(id)
            .removeValue().addOnSuccessListener {
                deleteData.postValue(DeleteData.ResultOk("Success"))
            }.addOnFailureListener {
                deleteData.postValue(DeleteData.ResultError("Failed"))
            }
    }
}