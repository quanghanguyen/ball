package com.example.matchball.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchRequest (
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("teamName")
    val teamName : String? = "",
    @SerializedName("date")
    val date : String? = "",
    @SerializedName("time")
    val time : String? = "",
    @SerializedName("pitch")
    val pitch : String? = "",
    @SerializedName("pitchLatitude")
    val pitchLatitude : String? = "",
    @SerializedName("pitchLongitude")
    val pitchLongitude : String? = "",
    @SerializedName("people")
    var people : String? = "",
    @SerializedName("note")
    val note : String? = "",
    @SerializedName("phone")
    val phone : String? = "",
        ) : Parcelable