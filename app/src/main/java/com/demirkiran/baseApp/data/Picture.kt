package com.demirkiran.baseApp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Picture(
    @SerializedName("id") val id: Int,
    @SerializedName("sol") val sol: Int,
    @SerializedName("camera") val camera: Camera,
    @SerializedName("img_src") val img_src: String,
    @SerializedName("earth_date") val earth_date: String,
    @SerializedName("rover") val rover: Rover
): Parcelable
data class  Pictures(
    val photos: List<Picture>,
)