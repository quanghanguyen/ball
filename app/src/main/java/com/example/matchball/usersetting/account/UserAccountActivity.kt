package com.example.matchball.usersetting.account

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.matchball.databinding.ActivityUserAccountBinding
import com.example.matchball.usersetting.changepassword.ChangePasswordActivity

class UserAccountActivity : AppCompatActivity() {

    private lateinit var userAccountBinding: ActivityUserAccountBinding
    private val userAccountViewModel : UserAccountViewModel by viewModels()
    private lateinit var imgUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userAccountBinding = ActivityUserAccountBinding.inflate(layoutInflater)
        setContentView(userAccountBinding.root)

        initEvents()
        initObserve()
        initAvatarObserve()
//        initEmailVerifyObserve()
        userAccountViewModel.handleLoadAvatar()
    }

    private fun initEvents() {
//        emailVerifyCheck()
        back()
        changeAvatar()
        saveAvatar()
        changePassword()
//        verifyEmail()
    }

    private fun initAvatarObserve() {
        userAccountViewModel.saveUserData.observe(this) { avatarResult ->
            when (avatarResult) {
                is UserAccountViewModel.SaveUserData.SaveOk -> {
                    Toast.makeText(this, avatarResult.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
                is UserAccountViewModel.SaveUserData.SaveFail -> {
                    Toast.makeText(this, avatarResult.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

//    private fun initEmailVerifyObserve() {
//        userAccountViewModel.verifyEmail.observe(this) { sendResult ->
//            when (sendResult) {
//                is UserAccountViewModel.VerifyEmail.EmailVerifySuccess -> {
//                    Toast.makeText(this, sendResult.successMessage, Toast.LENGTH_SHORT).show()
//                }
//                is UserAccountViewModel.VerifyEmail.EmailVerifyFail -> {
//                    Toast.makeText(this, sendResult.errorMessage, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }

    private fun initObserve() {
        userAccountViewModel.loadData.observe(this) { result ->
            when (result) {
                is UserAccountViewModel.UserData.LoadPhoneSuccess -> {
                    userAccountBinding.phone.text = result.phone
                }
                is UserAccountViewModel.UserData.LoadAvatarSuccess -> {
                    userAccountBinding.avatar.setImageBitmap(result.image)
                }
                is UserAccountViewModel.UserData.LoadDataFail -> {
                }
            }
        }
    }

//    private fun emailVerifyCheck() {
//        if (authUser!!.isEmailVerified) {
//            userAccountBinding.emailStatus.visibility = View.GONE
//            userAccountBinding.verifyEmail.visibility = View.GONE
//        }
//    }

//    private fun verifyEmail() {
//        userAccountBinding.verifyEmail.setOnClickListener {
//            userAccountViewModel.handleVerifyEmail()
//        }
//    }

    private fun saveAvatar() {
        userAccountBinding.saveAvatar.setOnClickListener {
            userAccountViewModel.handleSaveAvatar()
        }
    }

    private fun changeAvatar() {
        userAccountBinding.avatar.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == RESULT_OK) {
            imgUri = data?.data!!
            userAccountViewModel.setUri(imgUri)
            userAccountBinding.avatar.setImageURI(imgUri)
            userAccountBinding.saveAvatar.visibility = View.VISIBLE
        }
    }

    private fun changePassword() {
        userAccountBinding.changePassword.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun back() {
        userAccountBinding.back.setOnClickListener {
            finish()
        }
    }
}