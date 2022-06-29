package com.example.matchball.chat

import android.app.Application
import android.util.Log
import com.example.matchball.firebaseconnection.AuthConnection.authUser
import com.example.matchball.firebaseconnection.DatabaseConnection
import com.example.matchball.firebaseconnection.StorageConnection
import com.sendbird.uikit.SendBirdUIKit
import com.sendbird.uikit.adapter.SendBirdUIKitAdapter
import com.sendbird.uikit.interfaces.UserInfo

class BaseApplication : Application() {

    private var teamName : String? = null
    private var url : String? = null

    override fun onCreate() {
        super.onCreate()

        authUser?.let {
            DatabaseConnection.databaseReference.getReference("Users").child(it.uid).get().addOnSuccessListener {
                teamName =  it.child("teamName").value.toString()
            }
        }

        authUser?.let {
            StorageConnection.storageReference.getReference("Users").child(it.uid).downloadUrl.addOnSuccessListener {
                url = it.toString()
            }
        }

        SendBirdUIKit.init(object : SendBirdUIKitAdapter {
            override fun getAppId(): String {
                return "A113D5E8-B653-4412-8476-0AF5B8EB01F9"
            }

            override fun getAccessToken(): String {
                return ""
            }

            override fun getUserInfo(): UserInfo {
                return object : UserInfo {
                    override fun getUserId(): String {
                        return authUser!!.uid
                    }

                    override fun getNickname(): String? {
                        return teamName
                        }

                    override fun getProfileUrl(): String? {
                        return url
                    }
                }
            }
        }, this)
    }
}