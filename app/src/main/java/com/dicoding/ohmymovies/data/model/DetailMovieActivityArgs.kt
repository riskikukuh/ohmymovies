package com.dicoding.ohmymovies.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailMovieActivityArgs(
    val title : String,
    val movie : MovieModel?
) : Parcelable