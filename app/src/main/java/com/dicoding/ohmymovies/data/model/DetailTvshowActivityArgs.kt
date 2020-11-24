package com.dicoding.ohmymovies.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailTvshowActivityArgs(
    val id : Int? = 0,
    val title : String? = ""
) : Parcelable