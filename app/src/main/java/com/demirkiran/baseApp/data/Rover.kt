package com.demirkiran.baseApp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rover(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("landing_date") val landing_date: String,
    @SerializedName("launch_date") val launch_date: String,
    @SerializedName("status") val status: String
):Parcelable