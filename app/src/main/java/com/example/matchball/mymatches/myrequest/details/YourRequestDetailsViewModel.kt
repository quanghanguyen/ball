package com.example.matchball.mymatches.myrequest.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.matchball.createrequest.RequestViewModel
import com.example.matchball.firebaseconnection.AuthConnection
import com.example.matchball.firebaseconnection.DatabaseConnection

class YourRequestDetailsViewModel : ViewModel() {

    private val uid = AuthConnection.auth.currentUser!!.uid

    val getNameAndPhone = MutableLiveData<GetNameAndPhone>()
    val updateData = MutableLiveData<UpdateData>()
    val deleteData = MutableLiveData<DeleteData>()

    var day: Int = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay: Int = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0

    sealed class GetNameAndPhone {
        class GetResultOk(val name : String, val phone : String) : GetNameAndPhone()
        class GetResultError(val errorMessage : String) : GetNameAndPhone()
    }

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

    fun handleNameAndPhone() {
        DatabaseConnection.databaseReference.getReference("Users").child(uid).get().addOnSuccessListener {
            val teamName =  it.child("teamName").value.toString()
            val teamPhone = AuthConnection.authUser?.phoneNumber
            getNameAndPhone.postValue(GetNameAndPhone.GetResultOk(teamName, teamPhone!!))
        }.addOnFailureListener {
            getNameAndPhone.postValue(GetNameAndPhone.GetResultError("Failed to get Name and Phone"))
        }
    }
}