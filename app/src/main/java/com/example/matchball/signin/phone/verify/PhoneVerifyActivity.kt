package com.example.matchball.signin.phone.verify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.matchball.R
import com.example.matchball.databinding.ActivityPhoneVerifyBinding
import com.example.matchball.firebaseconnection.AuthConnection.auth
import com.example.matchball.home.MainActivity
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class PhoneVerifyActivity : AppCompatActivity() {

    private lateinit var phoneVerifyBinding: ActivityPhoneVerifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        phoneVerifyBinding = ActivityPhoneVerifyBinding.inflate(layoutInflater)
        setContentView(phoneVerifyBinding.root)

        initEvents()
    }

    private fun initEvents() {
        verify()
    }

    private fun verify() {
        val storedVerificationId = intent.getStringExtra("storedVerificationId")
        phoneVerifyBinding.verify.setOnClickListener {
            val otp = phoneVerifyBinding.verifyCode.text.toString().trim()
            if(otp.isNotEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else{
                Toast.makeText(this,"Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
        }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}