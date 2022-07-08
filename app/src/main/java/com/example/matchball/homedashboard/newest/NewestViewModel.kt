package com.example.matchball.homedashboard.newest

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.matchball.firebaseconnection.DatabaseConnection
import com.example.matchball.homedashboard.today.TodayViewModel
import com.example.matchball.model.MatchRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class NewestViewModel : ViewModel() {

    val newestListResult = MutableLiveData<NewestListResult>()
    sealed class NewestListResult {
        object Loading : NewestListResult()
        class ResultOk(val newestList : ArrayList<MatchRequest>) : NewestListResult()
        class ResultError(val errorMessage : String) : NewestListResult()
    }

    fun handleNewestList() {
        DatabaseConnection.databaseReference.getReference("MatchRequest").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val listNewest = ArrayList<MatchRequest>()
                    for (requestSnapshot in snapshot.children) {

                        requestSnapshot.getValue(MatchRequest::class.java)?.let {list ->
                            val current = LocalDate.now()
                            val matchTime = list.date

                            val formatter = DateTimeFormatter.ofPattern("d/M/yyyy", Locale.ENGLISH)

                            val date = LocalDate.parse(matchTime, formatter)

                            when {
                                date >= current -> {
                                    listNewest.add(0, list)
                                }
                                else -> {
                                    Log.e("Thong bao", "Failed")
                                }
                            }
                        }
                    }
                    newestListResult.postValue(NewestListResult.ResultOk(listNewest))
                }
            }
            override fun onCancelled(error: DatabaseError) {
                newestListResult.postValue(NewestListResult.ResultError("Failed to load Data"))
            }
        })
    }
}