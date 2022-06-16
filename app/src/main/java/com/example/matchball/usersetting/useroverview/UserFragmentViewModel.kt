package com.example.matchball.usersetting.useroverview

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.matchball.firebaseconnection.AuthConnection.authUser
import com.example.matchball.firebaseconnection.DatabaseConnection
import com.example.matchball.firebaseconnection.StorageConnection
import java.io.File

class UserFragmentViewModel : ViewModel() {

    private val uid = authUser?.uid
    val readUser = MutableLiveData<UserData>()

    sealed class UserData {
        class LoadUserInfo(val phone: String) : UserData()
        object Loading : UserData()
        class ReadAvatarSuccess(val image : Bitmap) : UserData()
        object ReadAvatarError : UserData()
        class ReadInfoSuccess(val teamName: String, val teamBio : String, val birthday : String, val email : String) : UserData()
        object ReadInfoError : UserData()
    }

    fun handleReadUser() {
        readUser.postValue(UserData.Loading)

        val userPhone = authUser?.phoneNumber
        userPhone?.let {
            readUser.postValue(UserData.LoadUserInfo(userPhone))
        }

        uid?.let {
            DatabaseConnection.databaseReference.getReference("Users").child(it).get().addOnSuccessListener {
                if (it.exists()) {
                    val teamName = it.child("teamName").value.toString()
                    val teamBio = it.child("teamBio").value.toString()
                    val birthday = it.child("birthday").value.toString()
                    val email = it.child("email").value.toString()

                    readUser.postValue(UserData.ReadInfoSuccess(teamName, teamBio, birthday, email))
                } else {
                    readUser.postValue(UserData.ReadInfoError)
                }
            }.addOnFailureListener {
                it.printStackTrace()
            }
        }

        val localFile = File.createTempFile("tempImage", "jpg")
        uid?.let {
            StorageConnection.handleAvatar(uid = it, localFile = localFile, onSuccess = {
                readUser.postValue(UserData.ReadAvatarSuccess(it))
            }, onFail = {
                readUser.postValue(UserData.ReadAvatarError)
            })
        }
    }
}