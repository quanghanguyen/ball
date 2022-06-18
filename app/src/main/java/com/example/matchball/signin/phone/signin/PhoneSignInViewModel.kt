package com.example.matchball.signin.phone.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.matchball.firebaseconnection.AuthConnection
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneSignInViewModel : ViewModel() {

    val phoneSignInResult = MutableLiveData<PhoneSignInResult>()

    sealed class PhoneSignInResult{
        class SignInOk()
    }



}