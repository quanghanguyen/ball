package com.example.matchball.signin.phone.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.matchball.databinding.ActivityPhoneSignInBinding
import com.example.matchball.firebaseconnection.AuthConnection.auth
import com.example.matchball.home.MainActivity
import com.example.matchball.signin.phone.verify.PhoneVerifyActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneSignInActivity : AppCompatActivity() {

    private lateinit var phoneSignInBinding: ActivityPhoneSignInBinding
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        phoneSignInBinding = ActivityPhoneSignInBinding.inflate(layoutInflater)
        setContentView(phoneSignInBinding.root)

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG","onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token

                var intent = Intent(applicationContext, PhoneVerifyActivity::class.java)
                intent.putExtra("storedVerificationId", storedVerificationId)
                startActivity(intent)
            }
        }
        initEvents()
    }

    private fun initEvents() {
        signIn()
    }

    private fun signIn() {
        phoneSignInBinding.signIn.setOnClickListener {
            phoneCheck()
        }
    }

    private fun phoneCheck() {
        val areaCode = phoneSignInBinding.areaCode.text.toString().trim()
        var phoneNumber = phoneSignInBinding.phoneNumber.text.toString().trim()

        if (areaCode.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please try Again", Toast.LENGTH_SHORT).show()
        } else {
            phoneNumber = areaCode + phoneNumber
            sendVertification(phoneNumber)
        }
    }

    private fun sendVertification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}